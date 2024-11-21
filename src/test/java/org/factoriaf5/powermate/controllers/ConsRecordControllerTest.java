/* package org.factoriaf5.powermate.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.factoriaf5.powermate.models.ConsRecord;
import org.factoriaf5.powermate.models.Device;
import org.factoriaf5.powermate.repositories.ConsRecordRepository;
import org.factoriaf5.powermate.services.ConsRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.http.MediaType;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ConsRecordController.class)
public class ConsRecordControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private Device mockDevice;

    @Mock
    private ConsRecordRepository consRecordRepository;

    @Mock
    private ConsRecordService consRecordService;

    private ConsRecord mockConsRecord;

    @BeforeEach
    public void setUp() {
        mockDevice = mock(Device.class);
        when(mockDevice.getId()).thenReturn(1L);
        mockConsRecord = new ConsRecord(mockDevice, 300.5, LocalDateTime.of(2024,11,18,23,59));
    }

    @Test
    @DisplayName("Should record a consumption of a Device by its id in the ConsRecord Model")
    void testRecordConsumption() throws Exception {
        when(consRecordService.recordConsumption(any(Device.class))).thenReturn(300.5);
        when(consRecordRepository.save(any(ConsRecord.class))).thenReturn(mockConsRecord);

        // Mockear el método findDeviceById del controlador
        doReturn(mockDevice).when(consRecordService).findDeviceById(1L);

        mockMvc.perform(post("/api/consumption/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.device.id").value(1L))
                .andExpect(jsonPath("$.consumption").value(300.5))
                .andExpect(jsonPath("$.timestamp").exists());
            
        verify(consRecordService, times(1)).recordConsumption(any(Device.class));
        verify(consRecordRepository, times(1)).save(any(ConsRecord.class));
    }
} */