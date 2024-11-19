package org.factoriaf5.powermate.controllers;

import java.util.List;

import org.factoriaf5.powermate.models.AlertsModel;

//Import necessary classes

import org.factoriaf5.powermate.services.AlertsServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//Controller for handling alerts

@RestController
@RequestMapping("/alerts")

public class AlertsController {

public class Alerts {

    private AlertsServices alertsService;
    public Alerts(AlertsServices alertsService) {
        this.alertsService = alertsService;
    }
    @PostMapping("createAlert")
    public void createAlert() {
        alertsService.createAlert(null, null, 0);
    }
    @PutMapping("updateAlert")
public void updateAlert(@RequestParam("id") long id, @RequestBody AlertsModel updatedAlert) {
    alertsService.updateAlert(id, updatedAlert.getThreshold());
}
    @DeleteMapping("deleteAlert")
    public void deleteAlert(@RequestParam("id") long id) {
        alertsService.deleteAlert(id);
    }

    /* 


    EN REVISION 


    @GetMapping("checkAlert")
    public boolean checkAlert(@RequestParam("id") long id) {
        return alertsService.checkAlert(id, id);

    @GetMapping("FindByDeviceId")
    public List<AlertsModel> findByDeviceId(@RequestParam("deviceId") long deviceId) {
        return alertsService.findByDeviceId(deviceId);
    }
    
        
}
        */
    
    // Other methods for handling alerts

    @GetMapping("FindByThresholdGreaterThan")
    public List<AlertsModel> findByThresholdGreaterThan(@RequestParam("threshold") double threshold) {
        return alertsService.findByThresholdGreaterThan(threshold);
    }

    }
}