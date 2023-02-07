package de.neuefische.backend.transaction;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface TransactionRepository extends MongoRepository<Transaction, String> {

List<Transaction> findBySenderAccountNumberOrReceiverAccountNumber(int senderAccountNumber, int receiverAccountNumber);
}
