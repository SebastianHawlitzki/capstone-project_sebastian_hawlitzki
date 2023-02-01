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
        // Ich hole mir den eingeloggten Benutzer aus der Datenbank
        AppUser senderUser = appUserRepository.findByUsername
                (SecurityContextHolder.getContext().getAuthentication().getName()).get();

        // Ich setze die senderAccountNumber auf den eingeloggten Benutzer
        int senderAccountNumber = transaction.getSenderAccountNumber();
        senderUser.setAccountNumber(senderAccountNumber);

        double senderAccountBalance = senderUser.getAccountBalance();
        double transactionAmount = transaction.getAmount();

        // Ich gleiche ab ob der eingeloggte Benutzer genug Geld zur verfügung hat um die Überweisung zu machen falls nit status 400
        if (senderAccountBalance <= 0 || senderAccountBalance < transactionAmount) {
            throw new NotEnoughBalanceException(senderAccountBalance);
        }

        // Ich hole mir den Empfänger mit der Iban (receiverAccountNumber) den ich in der transaction bekommen habe
        int receiverAccountNumber = transaction.getReceiverAccountNumber();
        Optional<AppUser> receiverUser = appUserRepository.findAppUsersByAccountNumber(receiverAccountNumber);

        // Ich reduziere den Überweisungsbetrag von dem Kontostand des eingeloggten benutzer
        double updatedSenderAccountBalance = senderAccountBalance - transactionAmount;
        senderUser.setAccountBalance(updatedSenderAccountBalance);


        // Ich addiere den Überweisungsbetrag auf den Kontostand von dem Empfänger
        if (receiverUser.isPresent()) {
            double receiverAccountBalance = receiverUser.get().getAccountBalance();
            double updateReceiverAccountBalance = receiverAccountBalance + transactionAmount;
            receiverUser.get().setAccountBalance(updateReceiverAccountBalance);
        } else {
            throw new ItemNotFoundException(receiverAccountNumber);
        }

        // Ich speicher den eingeloggten Benutzer in die Datenbank zurück
        appUserRepository.save(senderUser);

        // Ich speicher die geänderten Daten vom Empfänger in die Datenbank zurück
        AppUser receiver = receiverUser.get();
        appUserRepository.save(receiver);

        // Ich speicher die ausgeführte Transaktion in der Datenbank ab
        transactionRepository.save(transaction);

        // Ich gebe die gespeicherte Transaktion zurück
        return transaction;


    }
}


