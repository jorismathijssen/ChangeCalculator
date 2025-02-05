package com.jorismathijssen.changecalculatorapi.unit.services;

import com.jorismathijssen.changecalculatorapi.dto.ChangeRequest;
import com.jorismathijssen.changecalculatorapi.dto.ChangeResponse;
import com.jorismathijssen.changecalculatorapi.services.ChangeCalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ChangeCalculatorServiceTest {

    private ChangeCalculatorService service;

    @BeforeEach
    void setUp() {
        service = new ChangeCalculatorService();
    }

    @ParameterizedTest
    @CsvSource({
            "37.65, 50.00, 12.35",
            "10.00, 20.00, 10.00",
            "99.99, 200.00, 100.01",
            "1.00, 2.00, 1.00",
            "0.05, 1.00, 0.95"
    })
    void shouldCalculateCorrectChange(BigDecimal totalAmount, BigDecimal cashGiven, BigDecimal expectedChange) {
        // Arrange
        ChangeRequest request = new ChangeRequest(totalAmount, cashGiven);

        // Act
        ChangeResponse response = service.calculateChange(request);

        // Assert
        assertEquals(expectedChange, response.changeAmount());
        assertNotNull(response.changeBreakdown());
        assertFalse(response.changeBreakdown().isEmpty());
    }

    @Test
    void shouldThrowExceptionForInsufficientPayment() {
        // Arrange
        ChangeRequest request = new ChangeRequest(new BigDecimal(50.00), new BigDecimal(20.00));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                service.calculateChange(request));
        assertEquals("Insufficient payment. Please provide more cash.", exception.getMessage());
    }

    @Test
    void shouldReturnZeroChangeWhenExactPaymentIsMade() {
        // Arrange
        ChangeRequest request = new ChangeRequest(new BigDecimal(10.00), new BigDecimal(10.00));

        // Act
        ChangeResponse response = service.calculateChange(request);

        // Assert
        assertEquals(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN), response.changeAmount());
        assertTrue(response.changeBreakdown().isEmpty());
    }

    @Test
    void shouldHandleLargeCashAmounts() {
        // Arrange
        ChangeRequest request = new ChangeRequest(new BigDecimal(120034.56), new BigDecimal(200000.00));

        // Act
        ChangeResponse response = service.calculateChange(request);

        // Assert
        assertEquals(new BigDecimal(79965.44).setScale(2, RoundingMode.HALF_EVEN), response.changeAmount());
        assertNotNull(response.changeBreakdown());
    }
}
