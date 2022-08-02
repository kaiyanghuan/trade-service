package com.scb.trade.exceptions;

public class ProductMissingException extends BusinessException {
    public ProductMissingException(String errorMessage) {
        super(errorMessage);
    }
}
