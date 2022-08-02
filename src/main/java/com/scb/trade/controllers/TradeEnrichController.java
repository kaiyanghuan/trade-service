package com.scb.trade.controllers;

import com.scb.trade.entities.Trade;
import com.scb.trade.services.CsvService;
import com.scb.trade.services.TradeService;
import com.scb.trade.utilities.FileWriterHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.util.List;

@RestController
@RequestMapping("/api/v1/enrich")
public class TradeEnrichController {

    @Autowired
    private CsvService csvService;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private FileWriterHelper fileWriterHelper;

    @PostMapping
    public ResponseEntity<Resource> enrich(@RequestBody MultipartFile multipartFile) {
        BufferedReader bufferedReader = csvService.processCsv(multipartFile);
        List<Trade> tradeResult = tradeService.processTradeCsv(bufferedReader);
        InputStreamResource file = new InputStreamResource(fileWriterHelper.writeTrade(tradeResult));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFilename())
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }
}
