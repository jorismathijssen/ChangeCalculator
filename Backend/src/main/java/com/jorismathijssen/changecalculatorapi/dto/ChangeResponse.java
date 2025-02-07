package com.jorismathijssen.changecalculatorapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Represents the response containing the calculated change details.
 *
 * @param changeAmount    The total amount of change to be returned to the customer.
 * @param changeBreakdown A map detailing the breakdown of the change into denominations,
 *                        where the key is the denomination (formatted with the correct currency symbol),
 *                        and the value is the count of that denomination.
 * @param currency        The currency code in which the change is calculated.
 */
public record ChangeResponse(
        @Schema(
                description = "The total amount of change to be returned to the customer.",
                type = "number",
                format = "double",
                example = "12.35"
        )
        BigDecimal changeAmount,

        @Schema(
                description = "A breakdown of the change into denominations.",
                example = "{\"€10\": 1, \"€2\": 1, \"€0.20\": 1, \"€0.10\": 1, \"€0.05\": 1}"
        )
        Map<String, Integer> changeBreakdown,

        @Schema(
                description = "The currency in which the change is given.",
                type = "string",
                example = "EUR"
        )
        String currency
) {
}
