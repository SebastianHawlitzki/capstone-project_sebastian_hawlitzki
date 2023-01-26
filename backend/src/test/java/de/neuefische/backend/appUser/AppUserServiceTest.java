package de.neuefische.backend.appUser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


class AppUserServiceTest {
    @Test
    void create_whenAppUser_thenCreateAppUser() {
        // given
        AppUser appUser = new AppUser("1", "User", "", 0, "private", 0);

        AppUserRepository appUserRepository = Mockito.mock(AppUserRepository.class);
        Mockito.when(appUserRepository.save(appUser))
                .thenReturn(new AppUser("1", "User", "", 0, "private", 0));

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        AppUserService appUserService = new AppUserService(appUserRepository,bCryptPasswordEncoder);

        // when
        AppUser actual = appUserService.create(appUser);

        // then
        AppUser expected = new AppUser("1", "User", "",   1, "private", 0);
        Assertions.assertEquals(expected, actual);

        Mockito.verify(appUserRepository).save(appUser);
    }

}