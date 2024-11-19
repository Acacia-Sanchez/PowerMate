package org.factoriaf5.powermate.services;

import java.util.List;

import org.factoriaf5.powermate.models.AlertsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;




@Service
public class AlertsServices {
// @Autowired


private final AlertRepository alertRepository;



public AlertsServices(AlertRepository alertRepository) {
    this.alertRepository = alertRepository;
}


public interface AlertRepository extends JpaRepository <AlertsModel, Long> {
    List<AlertsModel> findByDeviceId(long deviceId);
    List<AlertsModel> findByThresholdGreaterThan(double threshold);
}

public List<AlertsModel> findByThresholdGreaterThan(double threshold) {
    return alertRepository.findByThresholdGreaterThan(threshold);
}

public AlertsModel createAlert(Long userId, Long deviceId, double threshold) {
    AlertsModel alert = new AlertsModel();
    alert.setUserid(userId);
    alert.setDeviceid(deviceId);
    alert.setThreshold(threshold);
    return alertRepository.save(alert);
}


public void deleteAlert(Long alertId) {
    alertRepository.deleteById(alertId);
}

public boolean checkAlert(Long deviceId, double currentConsumption) {
    List<AlertsModel> alerts = alertRepository.findByDeviceId(deviceId);
    for (AlertsModel alert : alerts) {
        if (currentConsumption > alert.getThreshold()) {
            System.out.println("Alera activada para el dispositivo " + deviceId+ " del usuario " + alert.getUserid());
            return true;
        }
    }
    return false;

}

public AlertsModel updateAlert(Long alertId, double threshold) {
    AlertsModel alert = alertRepository.findById(alertId).orElseThrow();
    alert.setThreshold(threshold);
    return alertRepository.save(alert);

}
}