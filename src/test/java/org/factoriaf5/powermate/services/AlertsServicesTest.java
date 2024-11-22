package org.factoriaf5.powermate.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.factoriaf5.powermate.models.AlertsModel;
import org.factoriaf5.powermate.models.Device;
import org.factoriaf5.powermate.repositories.AlertRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


class AlertsServicesTest {

    @Mock
    private AlertRepository alertRepository;

    @InjectMocks
    private AlertsServices alertsServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByThresholdGreaterThan() {
        AlertsModel alert1 = new AlertsModel();
        alert1.setThreshold(50.0);

        AlertsModel alert2 = new AlertsModel();
        alert2.setThreshold(80.0);

        when(alertRepository.findAll()).thenReturn(List.of(alert1, alert2));

        List<AlertsModel> result = alertsServices.findByThresholdGreaterThan(60.0);

        assertEquals(1, result.size());
        assertEquals(80.0, result.get(0).getThreshold());
        verify(alertRepository, times(1)).findAll();
    }

    @Test
    void testDeleteAlert() {
        Long alertId = 1L;

        doNothing().when(alertRepository).deleteById(alertId);

        alertsServices.deleteAlert(alertId);

        verify(alertRepository, times(1)).deleteById(alertId);
    }

    @Test
    void testFindById() {
        Long alertId = 1L;

        AlertsModel alert = new AlertsModel();
        alert.setId(alertId);

        when(alertRepository.findById(alertId)).thenReturn(Optional.of(alert));

        AlertsModel result = alertsServices.findById(alertId);

        assertNotNull(result);
        assertEquals(alertId, result.getId());
        verify(alertRepository, times(1)).findById(alertId);
    }

    @Test
    void testCheckAlert() {
        Long deviceId = 1L;
        double currentConsumption = 80.0;

        Device device = new Device();
        device.setId(deviceId);

        when(alertRepository.findAll()).thenReturn(List.of());

        AlertsModel alert = new AlertsModel();
        alert.setDevice(device);
        alert.setThreshold(70.0);

        when(alertRepository.findAll()).thenReturn(List.of(alert));

        boolean isAlertTriggered = alertsServices.checkAlert(deviceId, currentConsumption);

        assertFalse(isAlertTriggered);
        verify(alertRepository, times(1)).findAll();
    }

    @Test
    void testCheckAlertNoMatch() {
        Long deviceId = 1L;
        double currentConsumption = 50.0;

        Device device = new Device();
        device.setId(deviceId);

        AlertsModel alert = new AlertsModel();
        alert.setDevice(device);
        alert.setThreshold(70.0);

        when(alertRepository.findAll()).thenReturn(List.of(alert));

        boolean isAlertTriggered = alertsServices.checkAlert(deviceId, currentConsumption);

        assertFalse(isAlertTriggered);
        verify(alertRepository, times(1)).findAll();
    }

    @Test
    void testUpdateAlert() {
        Long alertId = 1L;
        double newThreshold = 90.0;

        AlertsModel alert = new AlertsModel();
        alert.setId(alertId);
        alert.setThreshold(50.0);

        when(alertRepository.findById(alertId)).thenReturn(Optional.of(alert));
        when(alertRepository.save(alert)).thenReturn(alert);

        AlertsModel updatedAlert = alertsServices.updateAlert(alertId, newThreshold);

        assertNotNull(updatedAlert);
        assertEquals(newThreshold, updatedAlert.getThreshold());
        verify(alertRepository, times(1)).findById(alertId);
        verify(alertRepository, times(1)).save(alert);
    }

    @Test
    void testFindAllWithFilter() {
        // Mock data
        Device device = new Device();
        device.setId(5L); // assuming you want to set the id of the device

        Device device1 = new Device();
        device1.setId(3L);

        Device device2 = new Device();
        device2.setId(6L);

        AlertsModel alert1 = new AlertsModel();
        alert1.setDevice(device1);
        alert1.setThreshold(50.0);

        AlertsModel alert2 = new AlertsModel();
        alert2.setDevice(device2);
        alert2.setThreshold(80.0);

        List<AlertsModel> mockAlerts = List.of(alert1, alert2);

        
        when(alertRepository.findAll()).thenReturn(mockAlerts);


        List<AlertsModel> filteredAlerts = alertsServices.findByThresholdGreaterThan(60.0);
        
        assertEquals(1, filteredAlerts.size());
        assertEquals(6L, filteredAlerts.get(0).getDevice().getId());
    }
    @Test
    void testNoAlertsTriggered() {
        
        Long deviceId = 5L;

        Device device1 = new Device();
        device1.setId(3L);

        Device device2 = new Device();
        device2.setId(6L);

        AlertsModel alert1 = new AlertsModel();
        alert1.setDevice(device1);
        alert1.setThreshold(50.0);
        AlertsModel alert2 = new AlertsModel();
        alert2.setDevice(device2);
        alert2.setThreshold(80.0);

        List<AlertsModel> mockAlerts = List.of(alert1, alert2);

        
        when(alertRepository.findAll()).thenReturn(mockAlerts);

    
        boolean result = alertsServices.checkAlert(deviceId, 40.0);
        
        assertFalse(result);
    }

    @Test
    void testAlertTriggered() {
    
    Long deviceId = 5L;

    Device device1 = new Device();
    device1.setId(3L);

    Device device2 = new Device();
    device2.setId(6L);

    AlertsModel alert1 = new AlertsModel();
    alert1.setDevice(device1);
    alert1.setThreshold(50.0);
    AlertsModel alert2 = new AlertsModel();
    alert2.setDevice(device2);
    alert2.setThreshold(80.0); 

    List<AlertsModel> mockAlerts = List.of(alert1, alert2);


    when(alertRepository.findAll()).thenReturn(mockAlerts);


    boolean result = alertsServices.checkAlert(deviceId, 90.0); 

    
    assertTrue(result);
}


@Test
void testDeviceNotPresent() {
    
    Long deviceId = 10L;

    Device device1 = new Device();
    device1.setId(3L);

    Device device2 = new Device();
    device2.setId(6L);

    AlertsModel alert1 = new AlertsModel();
    alert1.setDevice(device1);
    alert1.setThreshold(50.0);
    AlertsModel alert2 = new AlertsModel();
    alert2.setDevice(device2);
    alert2.setThreshold(80.0);

    List<AlertsModel> mockAlerts = List.of(alert1, alert2);


    when(alertRepository.findAll()).thenReturn(mockAlerts);

    boolean result = alertsServices.checkAlert(deviceId, 70.0);

    assertFalse(result);
}
@Test
void testAlertNotTriggeredByConsumption() {
    // Datos simulados
    Long deviceId = 5L;

    Device device1 = new Device();
    device1.setId(3L);

    Device device2 = new Device();
    device2.setId(6L);

    AlertsModel alert1 = new AlertsModel();
    alert1.setDevice(device1);
    alert1.setThreshold(50.0);
    AlertsModel alert2 = new AlertsModel();
    alert2.setDevice(device2);
    alert2.setThreshold(80.0);

    List<AlertsModel> mockAlerts = List.of(alert1, alert2);

    
    when(alertRepository.findAll()).thenReturn(mockAlerts);

    boolean result = alertsServices.checkAlert(deviceId, 40.0);


    assertFalse(result);
}
}