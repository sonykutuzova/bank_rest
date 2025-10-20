package com.example.bankcards.exception;

// Пользователь уже существует
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public UserAlreadyExistsException(String username, String email) {
        super("User with username '" + username + "' or email '" + email + "' already exists");
    }
}