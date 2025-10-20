package com.example.bankcards.util;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ValidationUtils {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^\\+?[1-9]\\d{1,14}$");

    public boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }

    public boolean isValidCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 16) {
            return false;
        }

        String cleanNumber = cardNumber.replaceAll("\\s+", "");
        return cleanNumber.matches("\\d{16}");
    }

    public boolean isValidAmount(Double amount) {
        return amount != null && amount > 0;
    }

    public boolean isValidCVV(String cvv) {
        return cvv != null && cvv.matches("\\d{3}");
    }

    public boolean isValidExpiryDate(String expiryDate) {
        return expiryDate != null && expiryDate.matches("(0[1-9]|1[0-2])/[0-9]{2}");
    }
}