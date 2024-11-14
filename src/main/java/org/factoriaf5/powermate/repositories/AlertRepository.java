package org.factoriaf5.powermate.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Alerts extends JpaRepository <Alert, Long> {

}
