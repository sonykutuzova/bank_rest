package com.example.bankcards.exception;

// Базовое исключение для сущностей
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String entityName, Long id) {
        super(entityName + " with id " + id + " not found");
    }

    public EntityNotFoundException(String entityName, String identifier) {
        super(entityName + " with identifier '" + identifier + "' not found");
    }
}