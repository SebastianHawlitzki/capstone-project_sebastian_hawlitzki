package de.neuefische.backend.service;



import de.neuefische.backend.appUser.AppUserRepository;
import de.neuefische.backend.transaction.Transaction;
import de.neuefische.backend.transaction.TransactionRepository;
import de.neuefische.backend.transaction.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;


class TransactionServiceTest {
    /*
    @Test
    void getAll_whenEmpty_thenReturnEmpty() {
        // given
        TransactionRepository transactionRepository = Mockito.mock(TransactionRepository.class);
        AppUserRepository appUserRepository = Mockito.mock(AppUserRepository.class);
        TransactionService transactionService = new TransactionService(transactionRepository, appUserRepository);

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

        AppUserRepository appUserRepository = Mockito.mock(AppUserRepository.class);

        TransactionRepository transactionRepository = Mockito.mock(TransactionRepository.class);
        Mockito.when(transactionRepository.findAll())
                .thenReturn(List.of((new Transaction("1", 1, 2, 500, "Überweisung an ", date))));

        TransactionService transactionService = new TransactionService(transactionRepository, appUserRepository);

        // when
        List<Transaction> actual = transactionService.getAll();

        // then
        List<Transaction> expected = new ArrayList<>(
                List.of((new Transaction("1", 1, 2, 500, "Überweisung an ", date))));

        Assertions.assertEquals(expected, actual);

        Mockito.verify(transactionRepository).findAll();
    }


 */
}