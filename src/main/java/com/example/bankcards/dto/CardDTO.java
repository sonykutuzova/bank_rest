package com.example.bankcards.dto;

import com.example.bankcards.entity.Card;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardDTO {
    private Long id;
    private String cardNumber;
    private String cardHolderName;
    private LocalDate expiryDate;
    private Card.CardType cardType;
    private Card.CardStatus status;
    private BigDecimal balance;
    private BigDecimal creditLimit;
    private LocalDateTime createdAt;
    private Long userId;

    public static CardDTO fromEntity(Card card) {
        return CardDTO.builder()
                .id(card.getId())
                .cardNumber(card.getCardNumber())
                .cardHolderName(card.getCardHolderName())
                .expiryDate(card.getExpiryDate())
                .cardType(card.getCardType())
                .status(card.getStatus())
                .balance(card.getBalance())
                .creditLimit(card.getCreditLimit())
                .createdAt(card.getCreatedAt())
                .userId(card.getUser().getId())
                .build();
    }
}