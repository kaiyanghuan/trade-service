package com.scb.trade;

import com.scb.trade.entities.Product;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ObjectFactory {

    public static MultipartFile mockMultipartFile(String fileName) {
        Path path = Paths.get("src/main/resources/" + fileName);
        String contentType = "text/plain";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
        }
        return new MockMultipartFile(fileName, fileName, contentType, content);
    }

    public static BufferedReader mockBufferedReader(String fileName) {
        MultipartFile multipartFile = mockMultipartFile(fileName);
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = multipartFile.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            bufferedReader.readLine();
        } catch (IOException ioException) {
        }
        return bufferedReader;
    }

    public static Product mockProduct() {
        return new Product(BigInteger.ONE, "Treasury Bills Domestic");
    }

}
