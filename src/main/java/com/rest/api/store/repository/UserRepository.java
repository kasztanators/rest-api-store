package com.rest.api.store.repository;

import com.rest.api.store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String string);

    Optional<User> findById(UUID id);

}
