package org.factoriaf5.powermate.repositories;

import org.factoriaf5.powermate.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Puedes añadir métodos personalizados si los necesitas
}
