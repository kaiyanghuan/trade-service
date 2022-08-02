package com.scb.trade.exceptions;

public class CsvFormatException extends BusinessException {
    public CsvFormatException(String errorMessage) {
        super(errorMessage);
    }
}