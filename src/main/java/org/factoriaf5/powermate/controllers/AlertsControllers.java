package org.factoriaf5.powermate.controllers;

//Import necessary classes

import org.factoriaf5.powermate.services.AlertsServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//Controller for handling alerts

@RestController
@RequestMapping("/alerts")

public class AlertsControllers {

public class Alerts {

    private AlertsServices alertsService;
    public Alerts(AlertsServices alertsService) {
        this.alertsService = alertsService;
    }
    @PostMapping("createAlert")
    public void createAlert() {
        alertsService.createAlert();
    }
    @PutMapping("updateAlert")
    public void updateAlert(@RequestParam("id") long id) {
        alertsService.updateAlert(id);
    }
    @DeleteMapping("deleteAlert")
    public void deleteAlert(@RequestParam("id") long id) {
        alertsService.deleteAlert(id);
    }
    @GetMapping("getAlert")
    public Alert getAlert(@RequestParam("id") long id) {
        return alertsService.getAlert(id);
    }
    @GetMapping("getAlerts")
    public List<Alert> getAlerts() {
        return alertsService.getAlerts();
    }
    // Other methods for handling alerts

    @GetMapping("FindByThresholdGreaterThan")
    public List<Alert> findByThresholdGreaterThan(@RequestParam("threshold") double threshold) {
        return alertsService.findByThresholdGreaterThan(threshold);
    }
    @GetMapping("FindByDeviceId")
    public List<Alert> findByDeviceId(@RequestParam("deviceId") long deviceId) {
        return alertsService.findByDeviceId(deviceId);
    }
    
    //UPDATE ALERT
    
    @PutMapping("updateAlert")
    public void updateAlert(@RequestParam("id") long id, @RequestBody Alert updatedAlert) {
        alertsService.updateAlert(id, updatedAlert);
    }

    }
}