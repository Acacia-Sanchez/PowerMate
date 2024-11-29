 package org.factoriaf5.powermate.controllers;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.factoriaf5.powermate.models.AlertsModel;
import org.factoriaf5.powermate.models.Device;
import org.factoriaf5.powermate.services.AlertsServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

class AlertsControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private AlertsServices alertsServices;

    @InjectMocks
    private AlertsController alertsController;

    private Device testDevice;
    private AlertsModel testAlert;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(alertsController).build();
        objectMapper = new ObjectMapper();

        testDevice = new Device();
        testDevice.setId(1L);

        testAlert = new AlertsModel();
        testAlert.setId(1L);
        testAlert.setDevice(testDevice);
        testAlert.setThreshold(100.0);
    }

    @Test
    void getAlertsByThreshold_shouldReturnFilteredAlerts() throws Exception {
        
        List<AlertsModel> alerts = Arrays.asList(testAlert);
        when(alertsServices.findByThresholdGreaterThan(anyDouble()))
            .thenReturn(alerts);

        
        mockMvc.perform(get("/api/alerts/threshold")
                .param("threshold", "50.0")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].threshold", is(100.0)));

        
        verify(alertsServices).findByThresholdGreaterThan(50.0);
    }

    @Test
    void updateAlert_shouldReturnUpdatedAlert() throws Exception {
        
        AlertsModel updatedAlert = new AlertsModel();
        updatedAlert.setId(1L);
        updatedAlert.setDevice(testDevice);
        updatedAlert.setThreshold(200.0);

        when(alertsServices.updateAlert(anyLong(), anyDouble()))
            .thenReturn(updatedAlert);

        
        mockMvc.perform(put("/api/alerts/update/1")
                .param("threshold", "200.0")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.threshold", is(200.0)));

        
        verify(alertsServices).updateAlert(1L, 200.0);
    }

    @Test
    void deleteAlert_shouldReturnNoContent() throws Exception {
        
        doNothing().when(alertsServices).deleteAlert(anyLong());

        
        mockMvc.perform(delete("/api/alerts/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        
        verify(alertsServices).deleteAlert(1L);
    }

    @Test
    void checkAlert_shouldReturnAlertStatus() throws Exception {
        
        when(alertsServices.checkAlert(anyLong(), anyDouble()))
            .thenReturn(true);

        
        mockMvc.perform(get("/api/alerts/check")
                .param("deviceId", "1")
                .param("currentConsumption", "150.0")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("true"));

        
        verify(alertsServices).checkAlert(1L, 150.0);
    }

    @Test
    void getAlertById_shouldReturnAlert_WhenAlertExists() throws Exception {
        
        when(alertsServices.findById(anyLong()))
            .thenReturn(testAlert);

        
        mockMvc.perform(get("/api/alerts/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.threshold", is(100.0)));

        
        verify(alertsServices).findById(1L);
    }

    @Test
    void getAlertById_shouldReturnNotFound_WhenAlertDoesNotExist() throws Exception {
        
        when(alertsServices.findById(anyLong()))
            .thenReturn(null);

        
        mockMvc.perform(get("/api/alerts/999")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

        
        verify(alertsServices).findById(999L);
    }
}