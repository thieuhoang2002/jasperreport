package com.example.demo.controller;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.productService;

import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("/api/products")
public class productController {

    @Autowired
    private productService productService;
    
    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<product>> getAllProduct() {
        List<product> products = productService.getAllProduct();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<List<product>> addProduct(@RequestBody List<product> products) {
        List<product> addedProducts = productService.addProduct(products);
        return new ResponseEntity<>(addedProducts, HttpStatus.CREATED);
    }

    @GetMapping("/generate-pdf")
    public ResponseEntity<byte[]> generatePdf() {
        try {
           
            List<product> products = productRepository.findAll();

            byte[] pdfContent = productService.generatePdfListProduct(products);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=report.pdf");
            headers.setContentType(MediaType.APPLICATION_PDF);

            return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new byte[0]);
        } catch (JRException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new byte[0]);
        }
    }
    
    
}