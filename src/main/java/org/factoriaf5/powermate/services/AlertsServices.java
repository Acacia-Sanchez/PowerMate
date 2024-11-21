package org.factoriaf5.powermate.services;

import java.util.List;

import org.factoriaf5.powermate.dtos.AlertsDTO;
import org.factoriaf5.powermate.models.AlertsModel;
import org.factoriaf5.powermate.repositories.AlertRepository;
import org.factoriaf5.powermate.repositories.DeviceRepository;
import org.springframework.stereotype.Service;




@Service
public class AlertsServices {


    private final AlertRepository alertRepository;
    private final DeviceRepository deviceRepository;



    public AlertsServices(AlertRepository alertRepository, DeviceRepository deviceRepository) {
        this.alertRepository = alertRepository;
        this.deviceRepository = deviceRepository;
    }

    public List<AlertsModel> findByThresholdGreaterThan(double threshold) {
        return alertRepository.findAll().stream().filter(x -> x.getThreshold()>threshold).toList();
    }

    public AlertsModel createAlert(AlertsDTO alertDto) {
        AlertsModel alert = new AlertsModel();
        alert.setDevice(deviceRepository.findById(alertDto.getDeviceid()).orElse(null));
        alert.setThreshold(alertDto.getThreshold());
        return alertRepository.save(alert);
    }


    public void deleteAlert(Long alertId) {
        alertRepository.deleteById(alertId);
    }

    public AlertsModel findById(Long alertId) {
        return alertRepository.findById(alertId).orElse(null);
    }

    public boolean checkAlert(Long device, double currentConsumption) {
        List<AlertsModel> alerts = alertRepository.findAll().stream().filter(x -> x.getDevice().getId()>device).toList();
        for (AlertsModel alert : alerts) {
            if (currentConsumption > alert.getThreshold()) {
                System.out.println("Alerta activada para el dispositivo " + device + " del usuario " + alert.getDevice().getUser());
                return true ;
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