package com.example.bankcards.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class AccountNumberGenerator {

    private static final Random random = new Random();

    public String generateAccountNumber() {
        // Генерация 20-значного номера счета
        StringBuilder accountNumber = new StringBuilder();

        for (int i = 0; i < 20; i++) {
            accountNumber.append(random.nextInt(10));
        }

        return accountNumber.toString();
    }

    public String formatAccountNumber(String accountNumber) {
        // Форматирование: 1234 5678 9012 3456 7890
        return accountNumber.replaceAll("(.{4})", "$1 ").trim();
    }
}