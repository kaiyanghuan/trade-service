package com.scb.trade.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trade {

    private String date;

    private BigInteger productId;

    private String productName;

    private String currency;

    private BigDecimal price;

    public String[] toStringArray() {
        return new String[]{this.date, this.productName, this.currency, this.price.toString()};
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s", this.date, this.productName, this.currency, this.price);
    }
}
