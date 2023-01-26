package de.neuefische.backend.appUser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


class AppUserServiceTest {
    @Test
    void create_whenAppUser_thenCreateAppUser() {
        // given
        AppUser appUser = new AppUser("1", "User", "", 0, "private", 0);

        AppUserRepository appUserRepository = Mockito.mock(AppUserRepository.class);
        Mockito.when(appUserRepository.save(appUser))
                .thenReturn(new AppUser("1", "User", "", 0, "private", 0));

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        AppUserService appUserService = new AppUserService(appUserRepository, bCryptPasswordEncoder);

        // when
        AppUser actual = appUserService.create(appUser);

        // then
        AppUser expected = new AppUser("1", "User", "", 1, "private", 0);
        Assertions.assertEquals(expected, actual);

        Mockito.verify(appUserRepository).save(appUser);
    }

    @Test
    void create_whenAppUserWithAccountNumber1_thenCreateAppUserWithAccountNumber2() {
        // given
        AppUser existingAppUser = new AppUser("1", "User", "", 1, "private", 0);
        Optional<AppUser> appUserWithMaxAccountNumber = Optional.of(existingAppUser);


        AppUserRepository appUserRepository = Mockito.mock(AppUserRepository.class);
        Mockito.when(appUserRepository.findTopByOrderByAccountNumberDesc())
                .thenReturn(appUserWithMaxAccountNumber);


        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        AppUserService appUserService = new AppUserService(appUserRepository, bCryptPasswordEncoder);

        // when
        AppUser actual = appUserService.create(existingAppUser);

        // then
        AppUser expected = new AppUser("1", "User", "", 2, "private", 0);
        Assertions.assertEquals(expected, actual);

        Mockito.verify(appUserRepository).save(existingAppUser);

    }

    @Test
    void create_whenExistingAppUser_thenReturn409Conflict() {
        // given
        AppUser appUser = new AppUser("1", "User", "password", 0, "private", 0);
        AppUser appUserExist = new AppUser("1", "User", "", 0, "private", 0);

        AppUserRepository appUserRepository = Mockito.mock(AppUserRepository.class);
        Mockito.when(appUserRepository.findByUsername(appUser.getUsername())).thenReturn(Optional.of(appUserExist));

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        AppUserService appUserService = new AppUserService(appUserRepository, bCryptPasswordEncoder);

        // when, then
        try{
            appUserService.create(appUser);
        }catch (ResponseStatusException e) {
            Assertions.assertEquals(e.getStatus(), HttpStatus.CONFLICT);
        }
        Mockito.verify(appUserRepository).findByUsername("User");
    }

}