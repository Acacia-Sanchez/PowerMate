package org.factoriaf5.powermate.controllers;


import static org.mockito.Mockito.*;

import org.factoriaf5.powermate.models.Device;
import org.factoriaf5.powermate.services.DeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(DeviceController.class)
public class DeviceControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
       
    @MockitoBean
    private DeviceService service;
    
    private Device mockDevice;
    private Device mockDeviceSaved;
    private ObjectMapper objectMapper = new ObjectMapper();
    
    // @BeforeEach
    // void setUp() {
    //     MockitoAnnotations.openMocks(this);
    //     mockDevice = new Device(null,2L, "Test Device", true,100);
    //     mockDeviceSaved = new Device(1L,2L, "Test Device", true,100);

    // }

    
    // @Test
    // void testAddDevice() throws Exception{
        
    //     when(service.createDevice(Mockito.any(Device.class))).thenReturn(mockDeviceSaved);

    //     String json = objectMapper.writeValueAsString(mockDevice);

    //     mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/devices")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(json))
    //             .andExpect(MockMvcResultMatchers.status().isCreated())
    //             .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(1L)))
    //             .andExpect(MockMvcResultMatchers.jsonPath("$.userId", is(2L)))
    //             .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("Test Device")))
    //             .andExpect(MockMvcResultMatchers.jsonPath("$.power", is(100)))
    //             .andExpect(MockMvcResultMatchers.jsonPath("$.status", is(true)));

    // }
            
    

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
