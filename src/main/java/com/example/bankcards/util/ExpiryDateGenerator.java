package com.example.bankcards.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class ExpiryDateGenerator {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");

    public String generateExpiryDate() {
        // Карта действует 5 лет с текущей даты
        LocalDate expiryDate = LocalDate.now().plusYears(5);
        return expiryDate.format(formatter);
    }

    public boolean isExpired(String expiryDate) {
        try {
            LocalDate expiry = LocalDate.parse("01/" + expiryDate, DateTimeFormatter.ofPattern("dd/MM/yy"));
            return expiry.isBefore(LocalDate.now());
        } catch (Exception e) {
            return true;
        }
    }
}