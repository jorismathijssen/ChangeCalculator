package com.jorismathijssen.changecalculatorapi.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jorismathijssen.changecalculatorapi.dto.ChangeRequest;
import com.jorismathijssen.changecalculatorapi.dto.ChangeResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ChangeCalculatorIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenValidInput_whenCalculateChange_thenReturnsCorrectChange() throws Exception {
        ChangeRequest request = new ChangeRequest(
                new BigDecimal("10.00"),
                new BigDecimal("20.00"),
                "EUR"
        );

        mockMvc.perform(post("/api/change")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.changeAmount").value(10.00))
                .andExpect(jsonPath("$.changeBreakdown").exists())
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    public void givenInsufficientPayment_whenCalculateChange_thenReturnsBadRequest() throws Exception {
        ChangeRequest request = new ChangeRequest(
                new BigDecimal("10.00"),
                new BigDecimal("5.00"),
                "EUR"
        );

        mockMvc.perform(post("/api/change")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenUnsupportedCurrency_whenCalculateChange_thenReturnsBadRequest() throws Exception {
        ChangeRequest request = new ChangeRequest(
                new BigDecimal("10.00"),
                new BigDecimal("20.00"),
                "XYZ"  // Unsupported currency
        );

        mockMvc.perform(post("/api/change")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
