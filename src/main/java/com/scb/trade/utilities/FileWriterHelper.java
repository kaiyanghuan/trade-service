package com.scb.trade.utilities;

import com.scb.trade.configurations.CsvConfig;
import com.scb.trade.entities.Trade;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Component
public class FileWriterHelper {

    @Autowired
    private CsvConfig csvConfig;

    public ByteArrayInputStream writeTrade(List<Trade> trades) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {
            csvPrinter.printRecord(csvConfig.getTradeResultHeaders());
            for (Trade trade : trades) {
                List<String> line = List.of(trade.getDate(), trade.getProductName(),
                        trade.getCurrency(), trade.getPrice().toString());
                csvPrinter.printRecord(line);
            }
            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }
}
