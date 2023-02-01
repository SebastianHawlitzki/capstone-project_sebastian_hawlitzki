package de.neuefische.backend.appUser;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AppUserRepository extends MongoRepository<AppUser, String> {
    Optional<AppUser> findByUsername(String username);

    Optional<AppUser> findTopByOrderByAccountNumberDesc();

    Optional<AppUser> findAppUsersByAccountNumber( int accountNumber);
}