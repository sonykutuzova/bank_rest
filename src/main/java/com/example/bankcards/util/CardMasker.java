package com.example.bankcards.util;

import org.springframework.stereotype.Component;

@Component
public class CardMasker {

    public String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 16) {
            return cardNumber;
        }

        // Убираем пробелы для обработки
        String cleanNumber = cardNumber.replaceAll("\\s+", "");

        if (cleanNumber.length() < 16) {
            return cardNumber;
        }

        // Оставляем первые 6 и последние 4 цифры, остальные заменяем на *
        String firstSix = cleanNumber.substring(0, 6);
        String lastFour = cleanNumber.substring(cleanNumber.length() - 4);
        String masked = firstSix + "******" + lastFour;

        // Возвращаем в форматированном виде
        return masked.replaceAll("(.{4})", "$1 ").trim();
    }

    public String maskCVV(String cvv) {
        if (cvv == null) {
            return null;
        }
        return "***";
    }

    public String maskCardForDisplay(String cardNumber) {
        // Более агрессивное маскирование для отображения в UI
        if (cardNumber == null || cardNumber.length() < 16) {
            return cardNumber;
        }

        String cleanNumber = cardNumber.replaceAll("\\s+", "");
        String lastFour = cleanNumber.substring(cleanNumber.length() - 4);

        return "**** **** **** " + lastFour;
    }
}