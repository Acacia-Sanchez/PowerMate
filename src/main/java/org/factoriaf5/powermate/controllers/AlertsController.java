package org.factoriaf5.powermate.controllers;

import java.util.List;

import org.factoriaf5.powermate.dtos.AlertsDTO;
import org.factoriaf5.powermate.models.AlertsModel;
import org.factoriaf5.powermate.services.AlertsServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alerts")
public class AlertsController {

    private final AlertsServices alertsServices;

    public AlertsController(AlertsServices alertsServices) {
        this.alertsServices = alertsServices;
    }

    @PostMapping("/create")
    public ResponseEntity<AlertsModel> createAlert(@RequestBody AlertsDTO alertDTO) {
        AlertsModel createdAlert = alertsServices.createAlert(alertDTO);
        return new ResponseEntity<>(createdAlert, HttpStatus.CREATED);
    }

    @GetMapping("/threshold")
    public ResponseEntity<List<AlertsModel>> getAlertsByThreshold(
        @RequestParam double threshold
    ) {
        List<AlertsModel> alerts = alertsServices.findByThresholdGreaterThan(threshold);
        return ResponseEntity.ok(alerts);
    }

    @PutMapping("/update/{alertId}")
    public ResponseEntity<AlertsModel> updateAlert(
        @PathVariable Long alertId, 
        @RequestParam double threshold
    ) {
        AlertsModel updatedAlert = alertsServices.updateAlert(alertId, threshold);
        return ResponseEntity.ok(updatedAlert);
    }

    @DeleteMapping("/delete/{alertId}")
    public ResponseEntity<Void> deleteAlert(@PathVariable Long alertId) {
        alertsServices.deleteAlert(alertId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkAlert(
        @RequestParam Long deviceId, 
        @RequestParam double currentConsumption
    ) {
        boolean isAlertTriggered = alertsServices.checkAlert(deviceId, currentConsumption);
        return ResponseEntity.ok(isAlertTriggered);
    }

    @GetMapping("/{alertId}")
    public ResponseEntity<AlertsModel> getAlertById(@PathVariable Long alertId) {
        AlertsModel alert = alertsServices.findById(alertId);
        if (alert != null) {
            return ResponseEntity.ok(alert);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}