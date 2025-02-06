package com.jorismathijssen.changecalculatorapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Represents the response containing the calculated change details.
 *
 * @param changeAmount    The total amount of change to be returned to the customer.
 * @param changeBreakdown A map detailing the breakdown of the change into denominations,
 *                        where the key is the denomination (e.g., "€10") and the value is
 *                        the count of that denomination.
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
        Map<String, Integer> changeBreakdown
) {}
