package com.example.demo.service;

import java.io.FileNotFoundException;
import java.util.List;

import com.example.demo.entity.product;

import net.sf.jasperreports.engine.JRException;

public interface productService {
	 	List<product> getAllProduct();
	 	
	 	List<product> addProduct (List<product> products);
	 	
	 	byte[] generatePdfListProduct(List<product> products) throws JRException, FileNotFoundException;
}

