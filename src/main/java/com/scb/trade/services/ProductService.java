package com.scb.trade.services;

import com.scb.trade.entities.Product;
import com.scb.trade.exceptions.CsvFormatException;
import com.scb.trade.exceptions.ProductMissingException;
import com.scb.trade.repositories.ProductRepository;
import com.scb.trade.utilities.NumberConversionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class ProductService {

    private final ConcurrentHashMap<BigInteger, Optional<Product>> productCache = new ConcurrentHashMap<>();

    @Autowired
    private ProductRepository productRepository;

    public Product getProduct(BigInteger id) {
        if (productCache.containsKey(id)) {
            return productCache.get(id).orElseThrow(() -> new ProductMissingException(String.format("Unable to find product for product id: %s", id)));
        }
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            productCache.put(product.getId(), Optional.of(product));
            return product;
        }

        productCache.put(id, Optional.empty());
        throw new ProductMissingException(String.format("Unable to find product for product id: %s", id));
    }

    public void processProductCsv(BufferedReader bufferedReader) {
        String line;
        try {
            while (((line = bufferedReader.readLine()) != null)) {
                processRow(line);
            }
        } catch (IOException e) {
            log.error("Unable to read line from csv");
            throw new CsvFormatException("Unable to read line from csv");
        }
    }

    public void clearProductCache() {
        productCache.clear();
    }

    private void processRow(String line) {
        String[] row = line.split(",");
        Product product = new Product();
        product.setId(NumberConversionUtil.parseBigIntegerFormat(row[0]));
        product.setName(row[1]);
        productRepository.save(product);
    }
}
