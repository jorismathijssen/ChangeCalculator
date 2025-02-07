package com.jorismathijssen.changecalculatorapi.unit.services;

import com.jorismathijssen.changecalculatorapi.configs.CurrencyConfig;
import com.jorismathijssen.changecalculatorapi.dto.ChangeRequest;
import com.jorismathijssen.changecalculatorapi.dto.ChangeResponse;
import com.jorismathijssen.changecalculatorapi.services.ChangeCalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChangeCalculatorServiceTest {

    private ChangeCalculatorService service;

    @BeforeEach
    void setUp() {
        CurrencyConfig currencyConfig = mock(CurrencyConfig.class);

        // Mock currency denominations
        Map<String, List<BigDecimal>> mockDenominations = Map.of(
                "EUR", List.of(new BigDecimal("200.00"), new BigDecimal("100.00"), new BigDecimal("50.00"),
                        new BigDecimal("20.00"), new BigDecimal("10.00"), new BigDecimal("5.00"),
                        new BigDecimal("2.00"), new BigDecimal("1.00"), new BigDecimal("0.50"),
                        new BigDecimal("0.20"), new BigDecimal("0.10"), new BigDecimal("0.05"),
                        new BigDecimal("0.02"), new BigDecimal("0.01")),
                "USD", List.of(new BigDecimal("100.00"), new BigDecimal("50.00"), new BigDecimal("20.00"),
                        new BigDecimal("10.00"), new BigDecimal("5.00"), new BigDecimal("1.00"),
                        new BigDecimal("0.25"), new BigDecimal("0.10"), new BigDecimal("0.05"),
                        new BigDecimal("0.01")),
                "GBP", List.of(new BigDecimal("50.00"), new BigDecimal("20.00"), new BigDecimal("10.00"),
                        new BigDecimal("5.00"), new BigDecimal("2.00"), new BigDecimal("1.00"),
                        new BigDecimal("0.50"), new BigDecimal("0.20"), new BigDecimal("0.10"),
                        new BigDecimal("0.05"), new BigDecimal("0.02"), new BigDecimal("0.01"))
        );

        // Mock currency symbols
        Map<String, String> mockSymbols = Map.of(
                "EUR", "€",
                "USD", "$",
                "GBP", "£"
        );

        when(currencyConfig.getDenominations()).thenReturn(mockDenominations);
        when(currencyConfig.getSymbols()).thenReturn(mockSymbols);
        when(currencyConfig.getSymbolForCurrency("EUR")).thenReturn("€");
        when(currencyConfig.getSymbolForCurrency("USD")).thenReturn("$");
        when(currencyConfig.getSymbolForCurrency("GBP")).thenReturn("£");
        when(currencyConfig.getSymbolForCurrency("AUD")).thenReturn("AUD "); // Fallback

        service = new ChangeCalculatorService(currencyConfig);
    }


    @ParameterizedTest
    @CsvSource({
            "37.65, 50.00, 12.35, EUR",
            "10.00, 20.00, 10.00, EUR",
            "99.99, 200.00, 100.01, EUR",
            "1.00, 2.00, 1.00, EUR",
            "0.05, 1.00, 0.95, EUR",
            "50.00, 100.00, 50.00, USD",
            "9.99, 20.00, 10.01, GBP"
    })
    void shouldCalculateCorrectChange(BigDecimal totalAmount, BigDecimal cashGiven, BigDecimal expectedChange, String currency) {
        // Arrange
        ChangeRequest request = new ChangeRequest(totalAmount, cashGiven, currency);

        // Act
        ChangeResponse response = service.calculateChange(request);

        // Assert
        assertEquals(expectedChange, response.changeAmount());
        assertNotNull(response.changeBreakdown());
        assertFalse(response.changeBreakdown().isEmpty());
        assertEquals(currency, response.currency()); // Ensures currency is correct
    }

    @Test
    void shouldThrowExceptionForInsufficientPayment() {
        // Arrange
        ChangeRequest request = new ChangeRequest(new BigDecimal("50.00"), new BigDecimal("20.00"), "EUR");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                service.calculateChange(request));
        assertEquals("Insufficient payment. Please provide more cash.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForUnsupportedCurrency() {
        // Arrange
        ChangeRequest request = new ChangeRequest(new BigDecimal("10.00"), new BigDecimal("20.00"), "GLD");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                service.calculateChange(request));
        assertEquals("Unsupported currency: GLD", exception.getMessage());
    }

    @Test
    void shouldReturnZeroChangeWhenExactPaymentIsMade() {
        // Arrange
        ChangeRequest request = new ChangeRequest(new BigDecimal("10.00"), new BigDecimal("10.00"), "EUR");

        // Act
        ChangeResponse response = service.calculateChange(request);

        // Assert
        assertEquals(new BigDecimal("0.00").setScale(2, RoundingMode.HALF_EVEN), response.changeAmount());
        assertTrue(response.changeBreakdown().isEmpty());
    }

    @Test
    void shouldHandleLargeCashAmounts() {
        // Arrange
        ChangeRequest request = new ChangeRequest(new BigDecimal("120034.56"), new BigDecimal("200000.00"), "EUR");

        // Act
        ChangeResponse response = service.calculateChange(request);

        // Assert
        assertEquals(new BigDecimal("79965.44").setScale(2, RoundingMode.HALF_EVEN), response.changeAmount());
        assertNotNull(response.changeBreakdown());
        assertFalse(response.changeBreakdown().isEmpty());
    }

    @Test
    void shouldCorrectlyBreakdownChangeForUSD() {
        // Arrange
        ChangeRequest request = new ChangeRequest(new BigDecimal("9.75"), new BigDecimal("20.00"), "USD");

        // Act
        ChangeResponse response = service.calculateChange(request);

        // Assert
        assertEquals(new BigDecimal("10.25"), response.changeAmount());
        assertNotNull(response.changeBreakdown());
        assertEquals(1, response.changeBreakdown().get("$10").intValue());
        assertEquals(1, response.changeBreakdown().get("$0.25").intValue());
    }

    @Test
    void shouldCorrectlyBreakdownChangeForGBP() {
        // Arrange
        ChangeRequest request = new ChangeRequest(new BigDecimal("4.95"), new BigDecimal("10.00"), "GBP");

        // Act
        ChangeResponse response = service.calculateChange(request);

        // Assert
        assertEquals(new BigDecimal("5.05"), response.changeAmount());
        assertNotNull(response.changeBreakdown());
        assertEquals(1, response.changeBreakdown().get("£5").intValue());
        assertEquals(1, response.changeBreakdown().get("£0.05").intValue());
    }
}
