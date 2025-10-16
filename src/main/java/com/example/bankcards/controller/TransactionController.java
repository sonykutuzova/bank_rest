package com.example.bankcards.controller;

import com.example.bankcards.dto.CreateTransactionRequest;
import com.example.bankcards.dto.TransactionDTO;
import com.example.bankcards.dto.TransferRequest;
import com.example.bankcards.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
@Tag(name = "Transaction Management", description = "APIs for managing transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/payment")
    @Operation(summary = "Create payment", description = "Create a new payment transaction")
    public ResponseEntity<TransactionDTO> createPayment(@Valid @RequestBody CreateTransactionRequest request) {
        TransactionDTO transaction = transactionService.createPayment(
                request.getCardId(),
                request.getAmount(),
                request.getMerchantName(),
                request.getDescription()
        );
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/transfer")
    @Operation(summary = "Create transfer", description = "Transfer money between cards")
    public ResponseEntity<TransactionDTO> createTransfer(@Valid @RequestBody TransferRequest request) {
        TransactionDTO transaction = transactionService.createTransfer(
                request.getFromCardId(),
                request.getToCardNumber(),
                request.getAmount(),
                request.getDescription()
        );
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/card/{cardId}")
    @Operation(summary = "Get card transactions", description = "Retrieve all transactions for a specific card")
    public ResponseEntity<List<TransactionDTO>> getCardTransactions(@PathVariable Long cardId) {
        List<TransactionDTO> transactions = transactionService.getCardTransactions(cardId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping
    @Operation(summary = "Get all transactions", description = "Retrieve all transactions in the system")
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        List<TransactionDTO> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }
}