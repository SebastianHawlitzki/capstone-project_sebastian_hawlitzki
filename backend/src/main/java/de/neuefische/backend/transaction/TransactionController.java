package de.neuefische.backend.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public List<Transaction> getAllFromAuthUser() {
        return transactionService.getAllFromAuthUser();
    }


    @PostMapping
    public Transaction sendTransaction(@RequestBody Transaction transaction){
        return transactionService.sendTransaction(transaction);
    }
}

