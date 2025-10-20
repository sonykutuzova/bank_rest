package com.example.bankcards.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CardNumberGenerator {

    private static final String BIN = "4"; // Visa
    private static final Random random = new Random();

    public String generateCardNumber() {
        StringBuilder cardNumber = new StringBuilder(BIN);

        // Генерация 15 цифр (1 уже есть - BIN)
        for (int i = 0; i < 15; i++) {
            cardNumber.append(random.nextInt(10));
        }

        // Добавляем контрольную цифру (алгоритм Луна)
        String numberWithoutCheckDigit = cardNumber.toString();
        String checkDigit = calculateLuhnCheckDigit(numberWithoutCheckDigit);
        cardNumber.append(checkDigit);

        return formatCardNumber(cardNumber.toString());
    }

    private String calculateLuhnCheckDigit(String number) {
        int sum = 0;
        boolean alternate = false;

        for (int i = number.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(number.charAt(i));

            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit = (digit % 10) + 1;
                }
            }

            sum += digit;
            alternate = !alternate;
        }

        int checkDigit = (10 - (sum % 10)) % 10;
        return String.valueOf(checkDigit);
    }

    private String formatCardNumber(String cardNumber) {
        // Форматирование: 1234 5678 9012 3456
        return cardNumber.replaceAll("(.{4})", "$1 ").trim();
    }
}