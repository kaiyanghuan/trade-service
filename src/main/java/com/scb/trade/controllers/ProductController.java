package com.scb.trade.controllers;

import com.scb.trade.services.CsvService;
import com.scb.trade.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private CsvService csvService;

    @Autowired
    private ProductService productService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestBody MultipartFile multipartFile) {
        BufferedReader bufferedReader = csvService.processCsv(multipartFile);
        productService.processProductCsv(bufferedReader);
        return ResponseEntity.ok("SUCCESS");
    }
}
