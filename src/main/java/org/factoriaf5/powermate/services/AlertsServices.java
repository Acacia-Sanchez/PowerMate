package org.factoriaf5.powermate.services;

import java.util.List;
//import org.factoriaf5.powermate.repositories.AlertRepository;

//import org.factoriaf5.powermate.controllers.AlertsControllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.factoriaf5.powermate.models.Alerts;



@Service
public class AlertsServices {
@Autowired

private final AlertRepository alertRepository;

public AlertsServices(AlertRepository alertRepository) {
    this.alertRepository = alertRepository;
}

public interface AlertRepository extends JpaRepository <AlertsServices, Long> {
    List<AlertsServices> findByDeviceId(long deviceId);
    List<AlertsServices> findByThresholdGreaterThan(double threshold);
}


public List<AlertsServices> findByThresholdGreaterThan( double threshold) {
    return alertRepository.findByThresholdGreaterThan(threshold);

}

public Alerts createAlert(Long userId, Long deviceId, double threshold) {
    Alerts alert = new Alerts();
    alert.setUserId(userId);
    alert.setDeviceId(deviceId);
    alert.setThreshold(threshold);
    return alertRepository.save(alert);
}

public void deleteAlert(Long alertId) {
    alertRepository.deleteById(alertId);
}

public boolean checkAlert(Long deviceId, double currentConsumption) {
    List<Alert> alerts = alertRepository.findByDeviceId(deviceId);
    for (Alert alert : alerts) {
        if (currentConsumption > alert.getThreshold()) {
            System.out.println("Alera activada para el dispositivo " + deviceId+ " del usuario " + alert.getUserId());
            return true;
        }
    }
    return false;

}

public AlertsServices updateAlert(Long alertId, double threshold) {
    AlertsServices alert = alertRepository.findById(alertId).orElseThrow();
    alert.setThreshold(threshold);
    return alertRepository.save(alert);

}
}