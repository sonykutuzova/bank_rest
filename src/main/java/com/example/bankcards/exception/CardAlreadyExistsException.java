package com.example.bankcards.exception;

// Карта уже существует
public class CardAlreadyExistsException extends RuntimeException {
    public CardAlreadyExistsException(String message) {
        super(message);
    }

    public CardAlreadyExistsException(String username, String email) {
        super("Card already exists");
    }
}