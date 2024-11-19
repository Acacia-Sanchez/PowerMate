package org.factoriaf5.powermate.services;

import java.util.List;

import org.factoriaf5.powermate.models.AlertsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;




@Service
public class AlertsServices {


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

public AlertsModel findById(Long alertId) {
    return alertRepository.findById(alertId).orElse(null);
}

/* 

EN REVISION DE CODIGO

public boolean checkAlert(Long deviceId, double currentConsumption) {
    AlertsModel alerts =findById(deviceId);
        for (AlertsModel alert : alerts) {
            if (currentConsumption > alert.getThreshold()) {
                System.out.println("Alera activada para el dispositivo " + deviceId + " del usuario " + alert.getUserid());
                return true;
            }
        }
        return false;
    
    }  

    private List<AlertsModel> findByDeviceId(Long deviceId) {
        throw new UnsupportedOperationException("Unimplemented method 'findByDeviceId'");
    }

    */

    
    public AlertsModel updateAlert(Long alertId, double threshold) {
    AlertsModel alert = alertRepository.findById(alertId).orElseThrow();
    alert.setThreshold(threshold);
    return alertRepository.save(alert);

}
}