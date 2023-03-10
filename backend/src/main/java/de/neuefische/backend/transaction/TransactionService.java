package de.neuefische.backend.transaction;

import de.neuefische.backend.app_user.AppUser;
import de.neuefische.backend.app_user.AppUserRepository;
import de.neuefische.backend.exception.ItemNotFoundException;
import de.neuefische.backend.exception.NotEnoughBalanceException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AppUserRepository appUserRepository;


    public List<Transaction> getAllFromAuthUser() {
        Optional<AppUser> optionalSenderUser = appUserRepository.findByUsername
                (SecurityContextHolder.getContext().getAuthentication().getName());
        if (optionalSenderUser.isPresent()) {
            AppUser senderUser = optionalSenderUser.get();
            int authUserAccountNumber = senderUser.getAccountNumber();
            return transactionRepository.findBySenderAccountNumberOrReceiverAccountNumber(authUserAccountNumber, authUserAccountNumber);
        } else {
            throw new NoSuchElementException("No user found for given username");
        }
    }


    public Transaction sendTransaction(Transaction transaction) {
        Optional<AppUser> optionalSenderUser = appUserRepository.findByUsername
                (SecurityContextHolder.getContext().getAuthentication().getName());
        if (optionalSenderUser.isPresent()) {
            AppUser senderUser = optionalSenderUser.get();
            int senderAccountNumber = senderUser.getAccountNumber();
            transaction.setSenderAccountNumber(senderAccountNumber);

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
        } else {
            throw new NoSuchElementException("No user found for given username");
        }
        return transaction;
    }
}


