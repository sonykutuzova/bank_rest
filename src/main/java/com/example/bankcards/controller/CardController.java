package com.example.bankcards.controller;

import com.example.bankcards.dto.CardDTO;
import com.example.bankcards.dto.CreateCardRequest;
import com.example.bankcards.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
@Tag(name = "Card Management", description = "APIs for managing bank cards")
public class CardController {

    private final CardService cardService;

    @PostMapping
    @Operation(summary = "Create a new card", description = "Create a new debit or credit card for a user")
    public ResponseEntity<CardDTO> createCard(@Valid @RequestBody CreateCardRequest request) {
        CardDTO card = cardService.createCard(request.getUserId(), request.getCardType());
        return ResponseEntity.ok(card);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user cards", description = "Retrieve all cards for a specific user")
    public ResponseEntity<List<CardDTO>> getUserCards(@PathVariable Long userId) {
        List<CardDTO> cards = cardService.getUserCards(userId);
        return ResponseEntity.ok(cards);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get card by ID", description = "Retrieve card information by card ID")
    public ResponseEntity<CardDTO> getCardById(@PathVariable Long id) {
        CardDTO card = cardService.getCardById(id);
        return ResponseEntity.ok(card);
    }

    @GetMapping("/number/{cardNumber}")
    @Operation(summary = "Get card by number", description = "Retrieve card information by card number")
    public ResponseEntity<CardDTO> getCardByNumber(@PathVariable String cardNumber) {
        CardDTO card = cardService.getCardByNumber(cardNumber);
        return ResponseEntity.ok(card);
    }

    @PostMapping("/{id}/block")
    @Operation(summary = "Block card", description = "Block a card by ID")
    public ResponseEntity<CardDTO> blockCard(@PathVariable Long id) {
        CardDTO card = cardService.blockCard(id);
        return ResponseEntity.ok(card);
    }

    @PostMapping("/{id}/unblock")
    @Operation(summary = "Unblock card", description = "Unblock a card by ID")
    public ResponseEntity<CardDTO> unblockCard(@PathVariable Long id) {
        CardDTO card = cardService.unblockCard(id);
        return ResponseEntity.ok(card);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete card", description = "Delete a card by ID")
    public ResponseEntity<Void> deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
        return ResponseEntity.noContent().build();
    }
}