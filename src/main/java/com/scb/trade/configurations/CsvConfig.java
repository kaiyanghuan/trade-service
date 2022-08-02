package com.scb.trade.configurations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "com.scb.trade-service")
@ConfigurationPropertiesScan
public class CsvConfig {

    private String tradeFileName;
    private String tradeHeaders;
    private List<String> tradeResultHeaders;
    private String productFileName;
    private String productHeaders;

    public String retrieveHeaders(String fileName) {
        if (fileName.equals(tradeFileName)) return tradeHeaders;
        if (fileName.equals(productFileName)) return productHeaders;
        return "";
    }
}