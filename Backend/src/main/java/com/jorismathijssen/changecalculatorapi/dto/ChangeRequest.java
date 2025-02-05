package com.jorismathijssen.changecalculatorapi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ChangeRequest(
        @NotNull @Min(0) BigDecimal totalAmount,
        @NotNull @Min(0) BigDecimal cashGiven
) {}
