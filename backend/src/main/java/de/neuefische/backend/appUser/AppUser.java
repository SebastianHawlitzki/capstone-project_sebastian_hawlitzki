package de.neuefische.backend.appUser;

import de.neuefische.backend.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {
    private String id;
    private String username;
    private String password;
    private int accountNumber;
    private String accountType;
    private double accountBalance;
    private Transaction[] transactionList;
}
