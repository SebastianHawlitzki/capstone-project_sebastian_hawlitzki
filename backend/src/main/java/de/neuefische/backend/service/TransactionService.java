package de.neuefische.backend.service;

import de.neuefische.backend.model.Transaction;
import de.neuefische.backend.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class TransactionService {
    private final TransactionRepository transactionRepository;

    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }



    // Receiver Method
    // benötigt: Transaction-> POST endpunkt
    // 1. get receiver data
    // 2. get amount + save transaction in Transaction[]
    // 3. update receiver data

    //Sender Method
    // benötigt: Transaction -> POST endpunkt
    // 1. get sender data
    // 2. send amount + save transaction in Transaction[]
    // 3. update sender data

    //Transaction
    // führt receiver Method & sender Method aus
    // ruft POST endpunkt auf

}
