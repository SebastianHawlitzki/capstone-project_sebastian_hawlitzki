package de.neuefische.backend.transaction;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface TransactionRepository extends MongoRepository<Transaction, String> {


}
