package com.example.bankcards.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CVVGenerator {

    private static final Random random = new Random();

    public String generateCVV() {
        // Генерация 3-значного CVV
        int cvv = random.nextInt(900) + 100; // от 100 до 999
        return String.valueOf(cvv);
    }
}