package com.scb.trade.services;

import com.scb.trade.ObjectFactory;
import com.scb.trade.configurations.CsvConfig;
import com.scb.trade.exceptions.CsvFormatException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;

@ExtendWith(MockitoExtension.class)
public class CsvServiceTest {

    @Mock
    private CsvConfig csvConfig;

    @InjectMocks
    private CsvService csvService;

    @Test
    public void itShouldProcessProductCsvFile_whenValidMultipartFileIsPassed() {
        String fileName = "product.csv";
        Mockito.when(csvConfig.retrieveHeaders(fileName)).thenReturn("product_id,product_name");
        MultipartFile mockFile = ObjectFactory.mockMultipartFile(fileName);
        BufferedReader bufferedReader = csvService.processCsv(mockFile);
        Assert.notNull(bufferedReader, "bufferedReader should not be null");
        Assert.isTrue(bufferedReader.lines().findAny().isPresent(), "bufferedReader should not be empty");
    }

    @Test
    public void itShoulThrowCsvFormatException_whenInvalidMultipartFileIsPassed() {
        String fileName = "invalid.csv";
        MultipartFile mockFile = ObjectFactory.mockMultipartFile(fileName);
        CsvFormatException csvFormatException = Assertions.assertThrows(CsvFormatException.class,
                () -> csvService.processCsv(mockFile),
                "Incorrect header format for file invalid.csv");
    }
}
