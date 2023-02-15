package de.neuefische.backend.transaction;

import de.neuefische.backend.app_user.AppUser;
import de.neuefische.backend.app_user.AppUserRepository;
import de.neuefische.backend.app_user.AppUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


class TransactionServiceTest {

    @Test
    void getAllFromAuthUser_whenNoAppUser_thenThrowNoSuchElementException() {
        // given
        Authentication auth = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);

        when(auth.getName()).thenReturn("user");
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        AppUserRepository appUserRepository = Mockito.mock(AppUserRepository.class);
        TransactionRepository transactionRepositoryRepository = Mockito.mock(TransactionRepository.class);
        TransactionService transactionService = new TransactionService(transactionRepositoryRepository, appUserRepository);

        when(appUserRepository.findByUsername("user")).thenReturn(Optional.empty());

        // when & then
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, transactionService::getAllFromAuthUser);

        assertEquals("No user found for given username", exception.getMessage());
    }

/*
    @Test
    void sendTransaction_whenAppUserNotPresent_thenThrowNoSuchElementException() {
        // given
        AppUserRepository appUserRepository = Mockito.mock(AppUserRepository.class);
        TransactionRepository transactionRepository = Mockito.mock(TransactionRepository.class);
        SecurityContextHolder.getContext().setAuthentication(null);

        TransactionService transactionService = new TransactionService(transactionRepository, appUserRepository);
        Transaction transaction = new Transaction("1", 1,2,500,"test",new Date());

        Mockito.when(appUserRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.empty());

        // when & then
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> transactionService.sendTransaction(transaction));
        assertEquals("No user found for given username", exception.getMessage());
    }

    
 */

}
