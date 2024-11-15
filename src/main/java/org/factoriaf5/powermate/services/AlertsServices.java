package org.factoriaf5.powermate.services;

import java.util.List;

import org.factoriaf5.powermate.models.Alerts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;



@Service
public class AlertsServices {
// @Autowired


private final AlertRepository alertRepository;



public AlertsServices(AlertRepository alertRepository) {
    this.alertRepository = alertRepository;
}

public interface AlertRepository extends JpaRepository <Alerts, Long> {
    List<Alerts> findByDeviceId(long deviceId);
    List<Alerts> findByThresholdGreaterThan(double threshold);
}

public List<Alerts> findByThresholdGreaterThan(double threshold) {
    return alertRepository.findByThresholdGreaterThan(threshold);
}

public Alerts createAlert(Long userId, Long deviceId, double threshold) {
    Alerts alert = new Alerts();
    alert.setUserid(userId);
    alert.setDeviceid(deviceId);
    alert.setThreshold(threshold);
    return alertRepository.save(alert);
}


public void deleteAlert(Long alertId) {
    alertRepository.deleteById(alertId);
}

public boolean checkAlert(Long deviceId, double currentConsumption) {
    List<Alerts> alerts = alertRepository.findByDeviceId(deviceId);
    for (Alerts alert : alerts) {
        if (currentConsumption > alert.getThreshold()) {
            System.out.println("Alera activada para el dispositivo " + deviceId+ " del usuario " + alert.getUserid());
            return true;
        }
    }
    return false;

}

public Alerts updateAlert(Long alertId, double threshold) {
    Alerts alert = alertRepository.findById(alertId).orElseThrow();
    alert.setThreshold(threshold);
    return alertRepository.save(alert);

}
}