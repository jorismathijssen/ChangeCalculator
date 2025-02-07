package com.jorismathijssen.changecalculatorapi.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Configuration class for loading currency settings from application.yml.
 */
@Component
@ConfigurationProperties(prefix = "currency")
public class CurrencyConfig {

    private Map<String, String> symbols;
    private Map<String, List<BigDecimal>> denominations;

    public Map<String, String> getSymbols() {
        return symbols;
    }

    public void setSymbols(Map<String, String> symbols) {
        this.symbols = symbols;
    }

    public Map<String, List<BigDecimal>> getDenominations() {
        return denominations;
    }

    public void setDenominations(Map<String, List<BigDecimal>> denominations) {
        this.denominations = denominations;
    }

    /**
     * Returns the currency symbol for a given currency code.
     * Defaults to the currency code itself if no symbol is found.
     *
     * @param currency The currency code (e.g., EUR, USD, GBP).
     * @return The corresponding currency symbol.
     */
    public String getSymbolForCurrency(String currency) {
        return symbols.getOrDefault(currency, currency + " "); // Fallback to currency code if missing
    }
}
