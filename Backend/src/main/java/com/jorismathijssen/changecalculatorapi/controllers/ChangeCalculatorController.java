package com.jorismathijssen.changecalculatorapi.controllers;

import com.jorismathijssen.changecalculatorapi.dto.ChangeRequest;
import com.jorismathijssen.changecalculatorapi.dto.ChangeResponse;
import com.jorismathijssen.changecalculatorapi.services.ChangeCalculatorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/change")
public class ChangeCalculatorController {

    private final ChangeCalculatorService changeCalculatorService;

    public ChangeCalculatorController(ChangeCalculatorService changeCalculatorService) {
        this.changeCalculatorService = changeCalculatorService;
    }

    @PostMapping
    public ResponseEntity<ChangeResponse> calculateChange(@Valid @RequestBody ChangeRequest request) {
        return ResponseEntity.ok(changeCalculatorService.calculateChange(request));
    }
}
