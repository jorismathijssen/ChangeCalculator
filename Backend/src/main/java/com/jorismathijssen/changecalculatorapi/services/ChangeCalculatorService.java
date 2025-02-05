package com.jorismathijssen.changecalculatorapi.services;

import com.jorismathijssen.changecalculatorapi.dto.ChangeRequest;
import com.jorismathijssen.changecalculatorapi.dto.ChangeResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Service for calculating the optimal change breakdown when a customer overpays.
 */
@Service
public class ChangeCalculatorService {

    /**
     * Ordered array of available EURO denominations, from highest to lowest.
     */
    private static final BigDecimal[] DENOMINATIONS = {
            new BigDecimal("500.00"), new BigDecimal("200.00"), new BigDecimal("100.00"),  new BigDecimal("50.00"),
            new BigDecimal("20.00"), new BigDecimal("10.00"), new BigDecimal("5.00"), new BigDecimal("2.00"),
            new BigDecimal("1.00"), new BigDecimal("0.50"), new BigDecimal("0.20"), new BigDecimal("0.10"),
            new BigDecimal("0.05"), new BigDecimal("0.02"), new BigDecimal("0.01")
    };

    /**
     * Calculates the change amount and provides a structured breakdown of bills and coins.
     *
     * @param request The DTO containing the total amount due and the cash received.
     * @return A {@link ChangeResponse} containing the total change amount and its denomination breakdown.
     * @throws IllegalArgumentException if the provided cash is less than the total amount due.
     */
    public ChangeResponse calculateChange(ChangeRequest request) {
        if (request.cashGiven().compareTo(request.totalAmount()) < 0) {
            throw new IllegalArgumentException("Insufficient payment. Please provide more cash.");
        }

        BigDecimal changeAmount = request.cashGiven().subtract(request.totalAmount()).setScale(2, RoundingMode.HALF_EVEN);
        Map<String, Integer> changeBreakdown = calculateDenominations(changeAmount);

        return new ChangeResponse(changeAmount, changeBreakdown);
    }

    /**
     * Computes the breakdown of the given change amount into the smallest number of bills and coins.
     *
     * @param changeAmount The total amount of change to be returned.
     * @return A {@link Map} containing the denomination (as a formatted string) and its count.
     */
    private Map<String, Integer> calculateDenominations(BigDecimal changeAmount) {
        Map<String, Integer> breakdown = new LinkedHashMap<>();
        BigDecimal remaining = changeAmount;

        for (BigDecimal denomination : DENOMINATIONS) {
            int count = remaining.divide(denomination, RoundingMode.FLOOR).intValue();
            if (count > 0) {
                breakdown.put(formatDenomination(denomination), count);
                remaining = remaining.subtract(BigDecimal.valueOf(count).multiply(denomination));
                remaining = remaining.setScale(2, RoundingMode.HALF_EVEN);
            }
        }
        return breakdown;
    }

    /**
     * Formats a currency denomination for display.
     *
     * @param value The denomination value.
     * @return A formatted string representation of the denomination.
     */
    private String formatDenomination(BigDecimal value) {
        return value.compareTo(BigDecimal.ONE) >= 0 ? "€" + value.intValue() : "€" + value;
    }
}
