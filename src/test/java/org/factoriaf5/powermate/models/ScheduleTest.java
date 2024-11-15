package org.factoriaf5.powermate.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ScheduleTest {

    private Schedule schedule;
    private Device device;

    @BeforeEach
    public void setUp() {
        // Crear instancia de Device (puedes simularlo si es necesario)
        device = Mockito.mock(Device.class);  // Usamos Mockito para simular el objeto Device
        
        // Crear instancia de Schedule y asociarle el dispositivo simulado
        schedule = new Schedule();
        schedule.setDevice(device);  // Asocia el objeto Device a Schedule
    }

    @Test
    public void testGettersAndSetters() {
        // Probar que los getters y setters funcionan correctamente
        
        LocalDateTime startTime = LocalDateTime.of(2024, 11, 14, 8, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 11, 14, 17, 0);

        schedule.setStartTime(startTime);
        schedule.setEndTime(endTime);

        // Verificar que los valores se guardan y recuperan correctamente
        assertEquals(startTime, schedule.getStartTime());
        assertEquals(endTime, schedule.getEndTime());
    }

    @Test
    public void testIsDeviceOn_True() {
        // Probar que isDeviceOn() devuelve true cuando la hora actual está dentro del rango

        LocalDateTime startTime = LocalDateTime.of(2024, 11, 14, 8, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 11, 14, 17, 0);

        schedule.setStartTime(startTime);
        schedule.setEndTime(endTime);

        // Establecer la hora actual dentro del rango
        LocalDateTime now = LocalDateTime.of(2024, 11, 14, 12, 0);  // Hora dentro del rango
        LocalDateTime nowMock = Mockito.mock(LocalDateTime.class);
        Mockito.when(nowMock.now()).thenReturn(now);  // Simular la hora actual

        assertTrue(schedule.isDeviceOn());
    }

    @Test
    public void testIsDeviceOn_False_BeforeStartTime() {
        // Probar que isDeviceOn() devuelve false cuando la hora actual es antes de la hora de inicio

        LocalDateTime startTime = LocalDateTime.of(2024, 11, 14, 8, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 11, 14, 17, 0);

        schedule.setStartTime(startTime);
        schedule.setEndTime(endTime);

        // Establecer la hora actual antes del inicio
        LocalDateTime now = LocalDateTime.of(2024, 11, 14, 7, 0);  // Hora antes del rango
        assertFalse(schedule.isDeviceOn());
    }

    @Test
    public void testIsDeviceOn_False_AfterEndTime() {
        // Probar que isDeviceOn() devuelve false cuando la hora actual es después de la hora de fin

        LocalDateTime startTime = LocalDateTime.of(2024, 11, 14, 8, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 11, 14, 17, 0);

        schedule.setStartTime(startTime);
        schedule.setEndTime(endTime);

        // Establecer la hora actual después del final
        LocalDateTime now = LocalDateTime.of(2024, 11, 14, 18, 0);  // Hora después del rango
        assertFalse(schedule.isDeviceOn());
    }
}
