package org.factoriaf5.powermate;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.factoriaf5.powermate.repositories.AlertRepository;
import org.factoriaf5.powermate.services.AlertsServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AlertsServicesTest {

    @Mock
    private AlertRepository alertRepository;

    @InjectMocks
    private AlertsServices alertsServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Aquí se agregarán las pruebas individuales para cada método.

    @Test
void testFindByDeviceId() {
    long deviceId = 1L;
    Alert alert1 = new Alert();
    alert1.setDeviceId(deviceId);
    Alert alert2 = new Alert();
    alert2.setDeviceId(deviceId);

    when(alertRepository.findByDeviceId(deviceId)).thenReturn(Arrays.asList(alert1, alert2));

    List<AlertsServices> result = alertsServices.findByDeviceId(deviceId);

    assertNotNull(result);
    assertEquals(2, result.size());
    verify(alertRepository, times(1)).findByDeviceId(deviceId);

    @Test
void testFindByThresholdGreaterThan() {
    double threshold = 50.0;
    Alert alert1 = new Alert();
    alert1.setThreshold(60.0);
    Alert alert2 = new Alert();
    alert2.setThreshold(70.0);

    when(alertRepository.findByThresholdGreaterThan(threshold)).thenReturn(Arrays.asList(alert1, alert2));

    List<AlertsServices> result = alertsServices.findByThresholdGreaterThan(threshold);

    assertNotNull(result);
    assertEquals(2, result.size());
    verify(alertRepository, times(1)).findByThresholdGreaterThan(threshold);
}

@Test
void testCreateAlert() {
    Long userId = 1L;
    Long deviceId = 2L;
    double threshold = 100.0;

    Alert alert = new Alert();
    alert.setUserId(userId);
    alert.setDeviceId(deviceId);
    alert.setThreshold(threshold);

    when(alertRepository.save(any(Alert.class))).thenReturn(alert);

    AlertsServices result = alertsServices.createAlert(userId, deviceId, threshold);

    assertNotNull(result);
    assertEquals(userId, alert.getUserId());
    assertEquals(deviceId, alert.getDeviceId());
    assertEquals(threshold, alert.getThreshold());
    verify(alertRepository, times(1)).save(any(Alert.class));
}


@Test
void testDeleteAlert() {
    Long alertId = 1L;

    doNothing().when(alertRepository).deleteById(alertId);

    alertsServices.deleteAlert(alertId);

    verify(alertRepository, times(1)).deleteById(alertId);
}


@Test
void testCheckAlert() {
    Long deviceId = 1L;
    double currentConsumption = 150.0;

    Alert alert = new Alert();
    alert.setDeviceId(deviceId);
    alert.setThreshold(100.0);

    when(alertRepository.findByDeviceId(deviceId)).thenReturn(Arrays.asList(alert));

    boolean result = alertsServices.checkAlert(deviceId, currentConsumption);

    assertTrue(result);
    verify(alertRepository, times(1)).findByDeviceId(deviceId);
}


@Test
void testUpdateAlert() {
    Long alertId = 1L;
    double newThreshold = 200.0;

    Alert alert = new Alert();
    alert.setId(alertId);
    alert.setThreshold(100.0);

    when(alertRepository.findById(alertId)).thenReturn(Optional.of(alert));
    when(alertRepository.save(any(Alert.class))).thenReturn(alert);

    AlertsServices result = alertsServices.updateAlert(alertId, newThreshold);

    assertNotNull(result);
    assertEquals(newThreshold, alert.getThreshold());
    verify(alertRepository, times(1)).findById(alertId);
    verify(alertRepository, times(1)).save(any(Alert.class));
}



}

}

