package org.factoriaf5.powermate.repositories;

import org.factoriaf5.powermate.models.Alerts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepository extends JpaRepository <Alerts, Long> {
    
}
