/* package org.factoriaf5.powermate.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.factoriaf5.powermate.models.Device;
import org.factoriaf5.powermate.repositories.ConsRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class ConsRecordServiceTest {
    // Se hacen los mocks correspondientes
    @Mock
    private ConsRecordRepository consRecordRepository;

    @Mock
    private Device mockDevice;
    private ConsRecordService consRecordService;

    @BeforeEach
    void setUp() {
        consRecordService = new ConsRecordService(consRecordRepository);
    }

    @Test
    void testRecordConsumption() {
        // se configura el compartamiento de los mockDevice
        when(mockDevice.isStatus()).thenReturn(true);
        when(mockDevice.getPower()).thenReturn(100);
        // se crea una variable que almacena el valor del metodo recordConsumption
        double consumption = consRecordService.recordConsumption(mockDevice);

        assertTrue(consumption > 0, "El consumo debe ser mayor que cero");
        verify(mockDevice, atLeastOnce()).isStatus();
        verify(mockDevice, atLeastOnce()).getPower();
    }
}
 */