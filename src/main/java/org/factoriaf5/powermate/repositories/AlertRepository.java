package org.factoriaf5.powermate.repositories;

import java.util.List;

import org.factoriaf5.powermate.models.AlertsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepository extends JpaRepository <AlertsModel, Long> {

    List<AlertsModel> findByDeviceId(long deviceId);
        List<AlertsModel> findByThresholdGreaterThan(double threshold);

}
