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

    //private int newUserIban = 1;
    public AppUser create (AppUser appUser) {
        Optional<AppUser> existingAppUser = findByUsername(
                appUser.getUsername()
        );

        if (existingAppUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
       // String iban = "DE89 1001 1001 0000 0" + newUserIban++;
        // appUser.setIban(iban);

        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));

        appUserRepository.save(appUser);

        appUser.setPassword("");

        return appUser;
    }

    public Optional<AppUser> findByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

   // public Optional<AppUser> findByIban(String iban) {
   //     return appUserRepository.findByIban(iban);
  //  }

    public Optional<AppUser> findByUsernameWithoutPassword(String username) {
        Optional<AppUser> appUser = appUserRepository.findByUsername(username);
        appUser.ifPresent(user -> user.setPassword(""));
        return appUser;
    }
}