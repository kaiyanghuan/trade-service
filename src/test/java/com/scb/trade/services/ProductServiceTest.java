package com.scb.trade.services;

import com.scb.trade.ObjectFactory;
import com.scb.trade.entities.Product;
import com.scb.trade.exceptions.ProductMissingException;
import com.scb.trade.repositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedReader;
import java.math.BigInteger;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    public void itShouldSuccessfullyProcessProductCsv_whenValidBufferedReaderIsPassed() {
        String fileName = "product.csv";
        BufferedReader mockBufferedReader = ObjectFactory.mockBufferedReader(fileName);
        productService.processProductCsv(mockBufferedReader);
    }

    @Test
    public void itShouldRetrieveProduct_whenValidIdIsPassed() {
        BigInteger id = BigInteger.ONE;
        Mockito.when(productRepository.findById(id)).thenReturn(Optional.of(ObjectFactory.mockProduct()));
        Product result = productService.getProduct(id);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(BigInteger.ONE, result.getId());
        Assertions.assertEquals("Treasury Bills Domestic", result.getName());
    }

    @Test
    public void itShouldThrowCsvFormatException_whenInvalidMultipartFileIsPassed() {
        BigInteger id = BigInteger.TEN;
        Mockito.when(productRepository.findById(id)).thenReturn(Optional.empty());
        ProductMissingException productMissingException = Assertions.assertThrows(ProductMissingException.class,
                () -> productService.getProduct(id),
                "Unable to find product for product id: 1");
    }

}
