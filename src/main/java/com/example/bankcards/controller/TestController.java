package com.example.bankcards.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@Tag(name = "Тестовый API", description = "API для проверки работы приложения")
public class TestController {

    @GetMapping("/hello")
    @Operation(summary = "Приветствие", description = "Простой эндпоинт для проверки работы")
    public String hello() {
        return "Hello from Bank Card System! Application is working!";
    }

    @GetMapping("/status")
    @Operation(summary = "Статус приложения", description = "Проверка статуса работы приложения")
    public String status() {
        return "Application is running successfully - " + System.currentTimeMillis();
    }

    @GetMapping("/db-check")
    @Operation(summary = "Проверка БД", description = "Проверка подключения к базе данных")
    public String dbCheck() {
        return "Database connection should be working";
    }
}