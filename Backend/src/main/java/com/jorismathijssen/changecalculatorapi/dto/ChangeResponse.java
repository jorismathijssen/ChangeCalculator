package com.jorismathijssen.changecalculatorapi.dto;

import java.math.BigDecimal;
import java.util.Map;

public record ChangeResponse(BigDecimal changeAmount, Map<String, Integer> changeBreakdown) {}
