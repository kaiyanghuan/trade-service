package com.scb.trade.utilities;

import com.scb.trade.exceptions.CsvFormatException;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.BigInteger;

@Slf4j
public class NumberConversionUtil {

    public static BigInteger parseBigIntegerFormat(String value) {
        try {
            return BigInteger.valueOf(Long.parseLong(value));
        } catch (NumberFormatException e) {
            log.error("Incorrect integer format");
            throw new CsvFormatException("Incorrect integer format");

        }
    }

    public static BigDecimal parseBigDecimalFormat(String value) {
        try {
            return BigDecimal.valueOf(Double.parseDouble(value));
        } catch (NumberFormatException e) {
            log.error("Incorrect double format");
            throw new CsvFormatException("Incorrect double format");
        }
    }
}
