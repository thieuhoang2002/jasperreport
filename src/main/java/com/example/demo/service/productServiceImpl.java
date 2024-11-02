package com.example.demo.service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.example.demo.DTO.reportProductDTO;
import com.example.demo.entity.product;
import com.example.demo.repository.ProductRepository;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class productServiceImpl implements productService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public List<product> addProduct(List<product> products) {
        return productRepository.saveAll(products);
    }

    @Override
    public byte[] generatePdfListProduct(List<product> products) throws JRException, FileNotFoundException {
    	List<reportProductDTO> listProduct = products.stream()
                .map(product -> new reportProductDTO(
                    product.getProductId(),
                    product.getName(),
                    product.getQuantity()
                ))
                .collect(Collectors.toList());
            
            String path = ResourceUtils.getFile("classpath:reports/productsReport.jrxml").getAbsolutePath();
            JasperReport jasperReport = JasperCompileManager.compileReport(path);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listProduct);
            Map<String, Object> parameters = new HashMap<>();
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            
            return outputStream.toByteArray();
    }
}