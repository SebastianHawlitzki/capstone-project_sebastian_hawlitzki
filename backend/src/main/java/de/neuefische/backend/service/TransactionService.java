package de.neuefische.backend.service;

import de.neuefische.backend.appUser.AppUser;
import de.neuefische.backend.appUser.AppUserRepository;
import de.neuefische.backend.exception.ItemNotFoundException;
import de.neuefische.backend.exception.NotEnoughBalanceException;
import de.neuefische.backend.model.Transaction;
import de.neuefische.backend.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AppUserRepository appUserRepository;


    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }


    public Transaction sendTransaction(Transaction transaction) {
        AppUser senderUser = appUserRepository.findByUsername
                (SecurityContextHolder.getContext().getAuthentication().getName()).get();

        int senderAccountNumber = transaction.getSenderAccountNumber();
        senderUser.setAccountNumber(senderAccountNumber);

        double senderAccountBalance = senderUser.getAccountBalance();
        double transactionAmount = transaction.getAmount();

        if (senderAccountBalance < transactionAmount) {
            throw new NotEnoughBalanceException(senderAccountBalance);
            }

        int receiverAccountNumber = transaction.getReceiverAccountNumber();
        Optional<AppUser> receiverUser = appUserRepository.findAppUsersByAccountNumber(receiverAccountNumber);

        double updatedSenderAccountBalance = senderAccountBalance - transactionAmount;
        senderUser.setAccountBalance(updatedSenderAccountBalance);

        if (receiverUser.isPresent()) {
            double receiverAccountBalance = receiverUser.get().getAccountBalance();
            double updateReceiverAccountBalance = receiverAccountBalance + transactionAmount;
            receiverUser.get().setAccountBalance(updateReceiverAccountBalance);
            } else {
            throw new ItemNotFoundException(receiverAccountNumber);
            }

        Date transactionDate = new Date();
        transaction.setTransactionDate(transactionDate);

        appUserRepository.save(senderUser);

        AppUser receiver = receiverUser.get();
        appUserRepository.save(receiver);

        transactionRepository.save(transaction);

        return transaction;

    }
}


