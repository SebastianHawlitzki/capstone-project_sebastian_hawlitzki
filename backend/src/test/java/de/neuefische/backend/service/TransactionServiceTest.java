package de.neuefische.backend.service;


import de.neuefische.backend.model.Transaction;
import de.neuefische.backend.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

class TransactionServiceTest {
    @Test
    void getAll_whenEmpty_thenReturnEmpty() {
        // given
        TransactionRepository transactionRepository = Mockito.mock(TransactionRepository.class);

        TransactionService transactionService = new TransactionService(transactionRepository);

        // when
        List<Transaction> actual = transactionService.getAll();

        // then
        List<Transaction> expected = new ArrayList<>();

        Assertions.assertEquals(expected, actual);

        Mockito.verify(transactionRepository).findAll();

    }


    @Test
    void getAll_whenOneTransaction_thenReturnOneTransaction() {
        // given
        java.util.Date date = new java.util.Date();

        TransactionRepository transactionRepository = Mockito.mock(TransactionRepository.class);
        Mockito.when(transactionRepository.findAll())
                .thenReturn(List.of((new Transaction("1", 1, 2, 500, "Überweisung an ", date))));


        TransactionService transactionService = new TransactionService(transactionRepository);

        // when
        List<Transaction> actual = transactionService.getAll();

        // then
        List<Transaction> expected = new ArrayList<>(List.of((new Transaction("1", 1, 2, 500, "Überweisung an ", date))));

        Assertions.assertEquals(expected, actual);

        Mockito.verify(transactionRepository).findAll();
    }

}