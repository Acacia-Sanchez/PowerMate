package org.factoriaf5.powermate.controllers;

import org.factoriaf5.powermate.models.Device;
import org.factoriaf5.powermate.services.DeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(DeviceController.class)
public class DeviceControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeviceService service;

    private Device mockDevice;
    
    @BeforeEach
    void setUp() {
        mockDevice = new Device(1L, 1L, "Test Device", true);
    }
    
    @Test
    void testAddDevice() {
        when(service.addDevice()).thenreturn(mockDevice);

        
    }

    @Test
    void testDeleteDevice() {

    }

    @Test
    void testGetDevice() {

    }

    @Test
    void testUpdateDevice() {

    }

    @Test
    void testUpdateStatus() {

    }
}
