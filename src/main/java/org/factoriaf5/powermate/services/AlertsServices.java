package org.factoriaf5.powermate.services;

import java.util.List;

import org.factoriaf5.powermate.models.AlertsModel;
import org.factoriaf5.powermate.models.Device;
import org.factoriaf5.powermate.repositories.AlertRepository;
import org.springframework.stereotype.Service;




@Service
public class AlertsServices {


    private final AlertRepository alertRepository;



    public AlertsServices(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public List<AlertsModel> findByThresholdGreaterThan(double threshold) {
        return alertRepository.findByThresholdGreaterThan(threshold);
    }

    public AlertsModel createAlert(Device device, double threshold) {
        AlertsModel alert = new AlertsModel();
        alert.setDevice(device);
        alert.setThreshold(threshold);
        return alertRepository.save(alert);
    }


    public void deleteAlert(Long alertId) {
        alertRepository.deleteById(alertId);
    }

    public AlertsModel findById(Long alertId) {
        return alertRepository.findById(alertId).orElse(null);
    }

    /* public boolean checkAlert(Long device, double currentConsumption) {
        for (AlertsModel alert : alerts) {
            if (currentConsumption > alert.getThreshold()) {
                System.out.println("Alera activada para el dispositivo " + device + " del usuario " + alert.getUserid());
                return true;
            }
        }
        return false;
    }
  */
    public AlertsModel updateAlert(Long alertId, double threshold) {
    AlertsModel alert = alertRepository.findById(alertId).orElseThrow();
    alert.setThreshold(threshold);
    return alertRepository.save(alert);

    }
}