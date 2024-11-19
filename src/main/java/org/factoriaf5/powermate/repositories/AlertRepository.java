package org.factoriaf5.powermate.repositories;

import org.factoriaf5.powermate.controllers.AlertsControllers;
import org.factoriaf5.powermate.models.AlertsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepository extends JpaRepository <AlertsModel, Long> {

    List<Alerts> findByDeviceId(Long deviceId);
    List<Alerts> findByThresholdGreaterThan(double threshold);
    
}
