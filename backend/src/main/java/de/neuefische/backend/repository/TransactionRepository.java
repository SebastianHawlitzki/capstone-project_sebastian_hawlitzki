package de.neuefische.backend.repository;

import de.neuefische.backend.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<Transaction, String> {


}
