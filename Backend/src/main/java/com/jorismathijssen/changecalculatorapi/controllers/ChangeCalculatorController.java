package com.jorismathijssen.changecalculatorapi.controllers;

import com.jorismathijssen.changecalculatorapi.dto.ChangeRequest;
import com.jorismathijssen.changecalculatorapi.dto.ChangeResponse;
import com.jorismathijssen.changecalculatorapi.services.ChangeCalculatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/change")
@Tag(name = "Change Calculator", description = "API for calculating change based on the total amount and cash provided.")
public class ChangeCalculatorController {

    private final ChangeCalculatorService changeCalculatorService;

    public ChangeCalculatorController(ChangeCalculatorService changeCalculatorService) {
        this.changeCalculatorService = changeCalculatorService;
    }

    @PostMapping
    @Operation(
            summary = "Calculate Change",
            description = "Calculates the change to be returned to a customer based on the total amount due and the cash provided."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Change calculated successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid input data.")
    })
    public ResponseEntity<ChangeResponse> calculateChange(@Valid @RequestBody ChangeRequest request) {
        return ResponseEntity.ok(changeCalculatorService.calculateChange(request));
    }
}
