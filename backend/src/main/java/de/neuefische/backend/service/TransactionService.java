package de.neuefische.backend.service;

import de.neuefische.backend.appUser.AppUser;
import de.neuefische.backend.appUser.AppUserRepository;
import de.neuefische.backend.exception.ItemNotFoundException;
import de.neuefische.backend.model.Transaction;
import de.neuefische.backend.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
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


    public Transaction sendTransaction(Transaction transaction){
      String receiverId = transaction.getReceiverId();

      // Ich hole mir den eingeloggten Benutzer aus der Datenbank
        // Ich setze die transactionSenderID auf den eingeloggten Benutzer
        // Ich gleiche ab ob der eingeloggte Benutzer genug Geld zur verfügung hat um die Überweisung zu machen falls nit status 400
        // Ich hole mir den Empfänger mit der Iban den ich in der transaction bekommen habe
        // Ich reduziere den Überweisungsbetrag von dem eingeloggten benutzer
        // Ich addiere den Überweisungsbetrag auf den Kontostand von dem Empfänger
        // Ich setze das Datum zum zeitpunkt der Überweisung auf der Transaktion
        // Ich speicher den eingeloggten Benutzer in die Datenbank zurück
        // Ich speicher die geänderten Daten vom Empfänger in die Datenbank zurück
        // Ich speicher die ausgeführte Transaktion in der Datenbank ab
        // Ich gebe die gespeicherte Transaktion zurück


        // Ich hole mir aus der DatenBank den Benutzer mit der receiverIBAN aus dem transaction json
        // Ich addiere den Betrag aus dem transaction json mit dem Kontostand vom Empfänger aus der Datenbank
        // Ich hole mir aus der Datenbank den authentizierten Benutzer
      Optional<AppUser> optionalReceiver = appUserRepository.findById(receiverId);
      if (optionalReceiver.isEmpty()) {
          throw new ItemNotFoundException(receiverId);
      }
      AppUser receiver = optionalReceiver.get();

      receiver.setAccountBalance(transaction.getAmount() + receiver.getAccountBalance());

      List<Transaction> transactionList = receiver.getTransactionList();
      transactionList.add(transaction);
      receiver.setTransactionList(transactionList);

      appUserRepository.save(receiver);

      updateSender(transaction);

      return transaction;

    }

    private AppUser updateSender(Transaction transaction) {
        String senderId = transaction.getSenderId();

        Optional<AppUser> optionalSender = appUserRepository.findById(senderId);
        if (optionalSender.isEmpty()) {
            throw new ItemNotFoundException(senderId);
        }
        AppUser sender = optionalSender.get();

        sender.setAccountBalance(sender.getAccountBalance() - transaction.getAmount() );

        List<Transaction> transactionList = sender.getTransactionList();
        transactionList.add(transaction);
        sender.setTransactionList(transactionList);

        appUserRepository.save(sender);

        return sender;

    }


}
