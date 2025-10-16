package com.example.bankcards.service;

import com.example.bankcards.dto.TransactionDTO;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.Transaction;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CardRepository cardRepository;

    public TransactionDTO createPayment(Long cardId, BigDecimal amount, String merchant, String description) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found with id: " + cardId));

        validateCardForTransaction(card, amount);

        // Списание средств
        card.setBalance(card.getBalance().subtract(amount));
        cardRepository.save(card);

        Transaction transaction = Transaction.builder()
                .amount(amount)
                .type(Transaction.TransactionType.PAYMENT)
                .status(Transaction.TransactionStatus.COMPLETED)
                .description(description)
                .merchantName(merchant)
                .card(card)
                .build();

        Transaction savedTransaction = transactionRepository.save(transaction);
        return TransactionDTO.fromEntity(savedTransaction);
    }

    public TransactionDTO createTransfer(Long fromCardId, String toCardNumber, BigDecimal amount, String description) {
        Card fromCard = cardRepository.findById(fromCardId)
                .orElseThrow(() -> new RuntimeException("Source card not found"));

        Card toCard = cardRepository.findByCardNumber(toCardNumber)
                .orElseThrow(() -> new RuntimeException("Destination card not found with number: " + toCardNumber));

        validateCardForTransaction(fromCard, amount);
        validateCardForTransaction(toCard, BigDecimal.ZERO); // Just check if active

        // Списание с отправителя
        fromCard.setBalance(fromCard.getBalance().subtract(amount));
        cardRepository.save(fromCard);

        // Зачисление получателю
        toCard.setBalance(toCard.getBalance().add(amount));
        cardRepository.save(toCard);

        Transaction transaction = Transaction.builder()
                .amount(amount)
                .type(Transaction.TransactionType.TRANSFER)
                .status(Transaction.TransactionStatus.COMPLETED)
                .description(description)
                .card(fromCard)
                .build();

        Transaction savedTransaction = transactionRepository.save(transaction);
        return TransactionDTO.fromEntity(savedTransaction);
    }

    @Transactional(readOnly = true)
    public List<TransactionDTO> getCardTransactions(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found with id: " + cardId));
        return transactionRepository.findByCardOrderByCreatedAtDesc(card).stream()
                .map(TransactionDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TransactionDTO> getAllTransactions() {
        return transactionRepository.findAll().stream()
                .map(TransactionDTO::fromEntity)
                .collect(Collectors.toList());
    }

    private void validateCardForTransaction(Card card, BigDecimal amount) {
        if (card.getStatus() != Card.CardStatus.ACTIVE) {
            throw new RuntimeException("Card is not active");
        }

        if (card.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }
    }
}