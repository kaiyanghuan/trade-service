package com.scb.trade.services;

import com.scb.trade.configurations.CsvConfig;
import com.scb.trade.exceptions.CsvFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
@Slf4j
public class CsvService {

    @Autowired
    private CsvConfig csvConfig;

    public BufferedReader processCsv(MultipartFile multipartFile) {
        try {
            InputStream inputStream = multipartFile.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String header = bufferedReader.readLine();
            validateHeader(header, multipartFile.getOriginalFilename());
            return bufferedReader;
        } catch (IOException ioException) {
            log.error("Unable to read csv file {}", multipartFile.getOriginalFilename());
            throw new CsvFormatException("Unable to read Csv file");
        }
    }

    private void validateHeader(String header, String fileName) {
        if (header == null || !header.replaceAll(" ", "").equals(csvConfig.retrieveHeaders(fileName))) {
            log.error("Incorrect header format for file {}", fileName);
            throw new CsvFormatException(String.format("Incorrect header format for file %s", fileName));
        }
    }

}
