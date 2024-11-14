package org.factoriaf5.powermate.repositories;

import java.util.Optional;

import org.factoriaf5.powermate.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByUsername(String username);
    public boolean existsByUsername(String username);
}
