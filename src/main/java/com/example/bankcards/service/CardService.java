package com.example.bankcards.service;

import com.example.bankcards.dto.CardDTO;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CardService {

    private final CardRepository cardRepository;
    private final UserService userService;
    private final Random random = new Random();

    public CardDTO createCard(Long userId, Card.CardType cardType) {
        // ИСПРАВЛЯЕМ ЗДЕСЬ: getUserEntityById вместо getEntityById
        User user = userService.getUserEntityById(userId);

        String cardNumber = generateCardNumber();
        while (cardRepository.existsByCardNumber(cardNumber)) {
            cardNumber = generateCardNumber();
        }

        String cvv = generateCVV();

        Card card = Card.builder()
                .cardNumber(cardNumber)
                .expiryDate(LocalDate.now().plusYears(3))
                .cvv(cvv)
                .cardType(cardType)
                .status(Card.CardStatus.ACTIVE)
                .balance(BigDecimal.ZERO)
                .creditLimit(cardType == Card.CardType.CREDIT ? new BigDecimal("50000") : BigDecimal.ZERO)
                .user(user)
                .build();

        Card savedCard = cardRepository.save(card);
        return CardDTO.fromEntity(savedCard);
    }

    @Transactional(readOnly = true)
    public List<CardDTO> getUserCards(Long userId) {
        // ИСПРАВЛЯЕМ ЗДЕСЬ ТОЖЕ
        User user = userService.getUserEntityById(userId);
        return cardRepository.findByUser(user).stream()
                .map(CardDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CardDTO getCardById(Long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Card not found with id: " + id));
        return CardDTO.fromEntity(card);
    }

    @Transactional(readOnly = true)
    public CardDTO getCardByNumber(String cardNumber) {
        Card card = cardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new RuntimeException("Card not found with number: " + cardNumber));
        return CardDTO.fromEntity(card);
    }

    public CardDTO blockCard(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found with id: " + cardId));
        card.setStatus(Card.CardStatus.BLOCKED);
        Card updatedCard = cardRepository.save(card);
        return CardDTO.fromEntity(updatedCard);
    }

    public CardDTO unblockCard(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found with id: " + cardId));
        card.setStatus(Card.CardStatus.ACTIVE);
        Card updatedCard = cardRepository.save(card);
        return CardDTO.fromEntity(updatedCard);
    }

    public void deleteCard(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found with id: " + cardId));
        cardRepository.delete(card);
    }

    private String generateCardNumber() {
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            cardNumber.append(random.nextInt(10));
        }
        return cardNumber.toString();
    }

    private String generateCVV() {
        StringBuilder cvv = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            cvv.append(random.nextInt(10));
        }
        return cvv.toString();
    }
}