package de.neuefische.backend.appUser;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    public AppUser create(AppUser appUser) {
        Optional<AppUser> existingAppUser = findByUsername(
                appUser.getUsername()
        );

        if (existingAppUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        Optional <AppUser> maxAccountNumber =
                appUserRepository.findTopByOrderByAccountNumberDesc();
        int accountNumber = maxAccountNumber.map(AppUser::getAccountNumber).orElse(0) + 1;
        appUser.setAccountNumber(accountNumber);

        appUser.setAccountBalance(3500);

        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));

        appUserRepository.save(appUser);

        appUser.setPassword("");

        return appUser;
    }

    public Optional<AppUser> findByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }


    public Optional<AppUser> findByUsernameWithoutPassword(String username) {
        Optional<AppUser> appUser = appUserRepository.findByUsername(username);
        appUser.ifPresent(user -> user.setPassword(""));
        return appUser;
    }

}