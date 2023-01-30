package de.neuefische.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private String id;
    private int senderAccountNumber;
    private int receiverAccountNumber;
    private double amount;
    private String purpose;
    @CreatedDate
    private Date transactionDate;
}
