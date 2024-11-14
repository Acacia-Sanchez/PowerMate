package org.factoriaf5.powermate.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class AlertsServices {

@Autowired
private AlertRepository alertRepository;

public List<AlertsServices> findByDeviceId( long deviceId) {
    return alertRepository.findByDeviceId(deviceId);

}


public List<AlertsServices> findByThresholdGreaterThan( double threshold) {
    return alertRepository.findByThresholdGreaterThan(threshold);

}


public AlertsServices createAlert(Long userId, Long deviceId, double threshold) {
    Alert alert = new Alert();
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
}