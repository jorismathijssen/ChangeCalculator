package com.jorismathijssen.changecalculatorapi.services;

import com.jorismathijssen.changecalculatorapi.configs.CurrencyConfig;
import com.jorismathijssen.changecalculatorapi.dto.ChangeRequest;
import com.jorismathijssen.changecalculatorapi.dto.ChangeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Service for calculating the optimal change breakdown when a customer overpays.
 * Supports multiple currencies by using predefined denominations from an external configuration.
 */
@Service
public class ChangeCalculatorService {

    private final Map<String, List<BigDecimal>> currencyDenominations;
    private final CurrencyConfig currencyConfig;
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");


    /**
     * Constructor to inject currency denominations from application configuration.
     *
     * @param currencyConfig Configuration class that loads denominations from application.yml.
     */
    @Autowired
    public ChangeCalculatorService(CurrencyConfig currencyConfig) {
        this.currencyConfig = currencyConfig;
        this.currencyDenominations = currencyConfig.getDenominations();
    }

    /**
     * Calculates the change amount and provides a structured breakdown of bills and coins for the specified currency.
     *
     * @param request The DTO containing the total amount due, cash received, and currency type.
     * @return A {@link ChangeResponse} containing the total change amount and its denomination breakdown.
     * @throws IllegalArgumentException if the provided cash is less than the total amount due or if the currency is unsupported.
     */
    public ChangeResponse calculateChange(ChangeRequest request) {
        if (request.cashGiven().compareTo(request.totalAmount()) < 0) {
            throw new IllegalArgumentException("Insufficient payment. Please provide more cash.");
        }
        if (!currencyDenominations.containsKey(request.currency())) {
            throw new IllegalArgumentException("Unsupported currency: " + request.currency());
        }

        List<BigDecimal> denominations = currencyDenominations.get(request.currency());
        return computeChange(request, denominations);
    }

    /**
     * Computes the breakdown of the given change amount into the smallest number of bills and coins.
     *
     * @param request       The DTO containing total amount, cash received, and currency.
     * @param denominations The available denominations for the selected currency.
     * @return A {@link ChangeResponse} containing the total change amount and its breakdown.
     */
    private ChangeResponse computeChange(ChangeRequest request, List<BigDecimal> denominations) {
        BigDecimal changeAmount = request.cashGiven().subtract(request.totalAmount()).setScale(2, RoundingMode.HALF_EVEN);
        Map<String, Integer> changeBreakdown = calculateDenominations(changeAmount, denominations, request.currency());
        return new ChangeResponse(changeAmount, changeBreakdown, request.currency());
    }

    /**
     * Computes the optimal breakdown of change using the available denominations.
     *
     * @param changeAmount  The total amount of change to be returned.
     * @param denominations The available denominations for the given currency.
     * @param currency      The currency code (e.g., "EUR", "USD").
     * @return A {@link Map} containing the denomination (as a formatted string) and its count.
     */
    private Map<String, Integer> calculateDenominations(BigDecimal changeAmount, List<BigDecimal> denominations, String currency) {
        Map<String, Integer> breakdown = new LinkedHashMap<>();
        BigDecimal remaining = changeAmount;

        for (BigDecimal denomination : denominations) {
            int count = remaining.divide(denomination, RoundingMode.FLOOR).intValue();
            if (count > 0) {
                breakdown.put(formatDenomination(denomination, currency), count);
                remaining = remaining.subtract(BigDecimal.valueOf(count).multiply(denomination));
                remaining = remaining.setScale(2, RoundingMode.HALF_EVEN);
            }
        }
        return breakdown;
    }

    private String formatDenomination(BigDecimal value, String currency) {
        String symbol = currencyConfig.getSymbolForCurrency(currency);
        BigDecimal scaled = value.setScale(2, RoundingMode.HALF_EVEN);

        return symbol + (scaled.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0
                ? scaled.setScale(0, RoundingMode.UNNECESSARY).toPlainString()
                : scaled.toPlainString());
    }
}
