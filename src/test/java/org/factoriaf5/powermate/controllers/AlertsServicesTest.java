package org.factoriaf5.powermate.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.factoriaf5.powermate.models.AlertsModel;
import org.factoriaf5.powermate.services.AlertsServices;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;


class AlertsServicesTest {

    @Mock
    private AlertsServices.AlertRepository alertRepository;

    @InjectMocks
    private AlertsServices alertsServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByThresholdGreaterThan() {
        double threshold = 50.0;
        AlertsModel alert1 = new AlertsModel();
        AlertsModel alert2 = new AlertsModel();
        List<AlertsModel> expectedAlerts = Arrays.asList(alert1, alert2);

        when(alertRepository.findByThresholdGreaterThan(threshold)).thenReturn(expectedAlerts);

        List<AlertsModel> result = alertsServices.findByThresholdGreaterThan(threshold);

        assertEquals(expectedAlerts, result);
        verify(alertRepository, times(1)).findByThresholdGreaterThan(threshold);
    }

    @Test
    void testCreateAlert() {
        Long userId = 1L;
        Long deviceId = 1L;
        double threshold = 60.0;
        AlertsModel alert = new AlertsModel();
        alert.setUserid(userId);
        alert.setDeviceid(deviceId);
        alert.setThreshold(threshold);

        when(alertRepository.save(any(AlertsModel.class))).thenReturn(alert);

        AlertsModel createdAlert = alertsServices.createAlert(userId, deviceId, threshold);

        assertNotNull(createdAlert);
        assertEquals(userId, createdAlert.getUserid());
        assertEquals(deviceId, createdAlert.getDeviceid());
        assertEquals(threshold, createdAlert.getThreshold());
        verify(alertRepository, times(1)).save(any(AlertsModel.class));
    }

    @Test
    void testDeleteAlert() {
        Long alertId = 1L;

        alertsServices.deleteAlert(alertId);

        verify(alertRepository, times(1)).deleteById(alertId);
    }

    @Test
    void testCheckAlert_Activated() {
        Long deviceId = 1L;
        double currentConsumption = 70.0;
        AlertsModel alert = new AlertsModel();
        alert.setThreshold(50.0);
        alert.setUserid(1L);
        List<AlertsModel> alerts = Arrays.asList(alert);

        when(alertRepository.findByDeviceId(deviceId)).thenReturn(alerts);

        boolean result = alertsServices.checkAlert(deviceId, currentConsumption);

        assertTrue(result);
        verify(alertRepository, times(1)).findByDeviceId(deviceId);
    }

    @Test
    void testCheckAlert_NotActivated() {
        Long deviceId = 1L;
        double currentConsumption = 40.0;
        AlertsModel alert = new AlertsModel();
        alert.setThreshold(50.0);
        List<AlertsModel> alerts = Arrays.asList(alert);

        when(alertRepository.findByDeviceId(deviceId)).thenReturn(alerts);

        boolean result = alertsServices.checkAlert(deviceId, currentConsumption);

        assertFalse(result);
        verify(alertRepository, times(1)).findByDeviceId(deviceId);
    }

    @Test
    void testUpdateAlert() {
        Long alertId = 1L;
        double newThreshold = 80.0;
        AlertsModel alert = new AlertsModel();
        alert.setThreshold(50.0);

        when(alertRepository.findById(alertId)).thenReturn(Optional.of(alert));
        when(alertRepository.save(alert)).thenReturn(alert);

        AlertsModel updatedAlert = alertsServices.updateAlert(alertId, newThreshold);

        assertNotNull(updatedAlert);
        assertEquals(newThreshold, updatedAlert.getThreshold());
        verify(alertRepository, times(1)).findById(alertId);
        verify(alertRepository, times(1)).save(alert);
    }
}
