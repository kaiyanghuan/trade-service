package com.scb.trade.services;

import com.scb.trade.ObjectFactory;
import com.scb.trade.entities.Trade;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.BufferedReader;
import java.math.BigInteger;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class TradeServiceTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private TradeService tradeService;

    @BeforeEach
    private void init() {
        ReflectionTestUtils.setField(tradeService, "missingProductText", "Missing Product Name");
        ReflectionTestUtils.setField(tradeService, "dateFormat", "YYYYmmDD");
    }

    @Test
    public void itShouldSuccessfullyProcessTradeCsvWith4RowsInsteadOf5_when4RowsOfDataAreInvalid() {
        String fileName = "sample_trade.csv";
        BufferedReader mockBufferedReader = ObjectFactory.mockBufferedReader(fileName);
        Mockito.when(productService.getProduct(any(BigInteger.class))).thenReturn(ObjectFactory.mockProduct());
        List<Trade> result = tradeService.processTradeCsv(mockBufferedReader);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(4, result.size()); //Last row has date error
    }
}
