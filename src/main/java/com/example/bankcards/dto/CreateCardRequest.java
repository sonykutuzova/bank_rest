package com.example.bankcards.dto;

import com.example.bankcards.entity.Card;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCardRequest {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Card type is required")
    private Card.CardType cardType;
}