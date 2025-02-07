package com.jorismathijssen.changecalculatorapi;

import com.jorismathijssen.changecalculatorapi.configs.CurrencyConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(CurrencyConfig.class)
public class ChangeCalculatorApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChangeCalculatorApiApplication.class, args);
    }

}
