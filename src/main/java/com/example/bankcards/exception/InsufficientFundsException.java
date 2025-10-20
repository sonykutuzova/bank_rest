package com.example.bankcards.exception;

// Недостаточно средств
public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
        super(message);
    }

    public InsufficientFundsException(Double currentBalance, Double requiredAmount) {
        super("Insufficient funds. Current balance: " + currentBalance + ", required: " + requiredAmount);
    }
}
