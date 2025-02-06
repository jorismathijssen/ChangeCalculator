package com.jorismathijssen.changecalculatorapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Data Transfer Object representing a request to calculate change.
 * <p>
 * This DTO contains the total amount due and the cash provided by the customer.
 * Both fields are mandatory and must be non-negative values.
 * </p>
 */
public record ChangeRequest(
        @NotNull(message = "Total amount is required.")
        @DecimalMin(value = "0.0", message = "Total amount must be non-negative.")
        @Schema(
                description = "The total amount due for payment.",
                type = "number",
                format = "double",
                example = "10.00"
        )
        BigDecimal totalAmount,

        @NotNull(message = "Cash given is required.")
        @DecimalMin(value = "0.0", message = "Cash given must be non-negative.")
        @Schema(
                description = "The amount of cash provided by the customer.",
                type = "number",
                format = "double",
                example = "20.00"
        )
        BigDecimal cashGiven
) {}
