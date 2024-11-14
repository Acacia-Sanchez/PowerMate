package org.factoriaf5.powermate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class Alerts {

    @Autowired

private AlertRepository alertRepository;

public List<Alert> findByDeviceId( long deviceId) {
    return alertRepository.findByDeviceId(deviceId);

}


public List<Alert> findByThresholdGreaterThan( double threshold) {
    return alertRepository.findByThresholdGreaterThan(threshold);

}


public Alert createAlert(Long userId, Long deviceId, double threshold) {
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
