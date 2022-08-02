package com.scb.trade.services;

import com.scb.trade.entities.Product;
import com.scb.trade.entities.Trade;
import com.scb.trade.exceptions.CsvFormatException;
import com.scb.trade.exceptions.ProductMissingException;
import com.scb.trade.utilities.NumberConversionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TradeService {

    @Autowired
    private ProductService productService;

    @Value("${com.scb.trade-service.missing-product-text}")
    private String missingProductText;

    @Value("${com.scb.trade-service.date-format}")
    private String dateFormat;

    public List<Trade> processTradeCsv(BufferedReader bufferedReader) {
        List<Trade> tradeResult = new ArrayList<>();
        try {
            String line;
            while (((line = bufferedReader.readLine()) != null)) {
                try {
                    tradeResult.add(processRow(line));
                } catch (CsvFormatException e) {
                    log.warn("Skipping line {} due to format exception", line);
                }
            }
            productService.clearProductCache();
        } catch (IOException e) {
            log.error("Unable to read from csv");
            throw new CsvFormatException("Unable to read from csv");
        }
        return tradeResult;
    }

    private Trade processRow(String line) {
        String[] row = line.split(",");
        Trade trade = new Trade();
        trade.setDate(validateDateFormat(row[0]));
        trade.setProductId(NumberConversionUtil.parseBigIntegerFormat(row[1]));
        trade.setCurrency(row[2]);
        trade.setPrice(NumberConversionUtil.parseBigDecimalFormat(row[3]));
        processTrade(trade);
        return trade;
    }


    private void processTrade(Trade trade) {
        try {
            Product product = productService.getProduct(trade.getProductId());
            trade.setProductName(product.getName());
        } catch (ProductMissingException e) {
            log.error("Missing Product Mapping for trade date: {}, product id: {}", trade.getDate(), trade.getProductId());
            trade.setProductName(missingProductText);
        }
    }

    private String validateDateFormat(String date) {
        try {
            new SimpleDateFormat(dateFormat).parse(date);
            return date;
        } catch (ParseException e) {
            log.error("Trade date has incorrect format. Please follow {}", dateFormat);
            throw new CsvFormatException("Date has incorrect format");
        }
    }
}
