 package org.factoriaf5.powermate.models;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ScheduleTest {

    @Mock
    private Device mockDevice;  // Creamos un mock de la clase Device

    private Schedule schedule;

    @BeforeEach
    void setUp() {
        // Inicializamos los mocks
        MockitoAnnotations.openMocks(this);

        // Creamos un Schedule con un mock de Device y un rango de tiempo
        LocalDateTime startTime = LocalDateTime.of(2024, 11, 15, 8, 0);  // 15 de noviembre de 2024, 08:00 AM
        LocalDateTime endTime = LocalDateTime.of(2024, 11, 15, 18, 0);    // 15 de noviembre de 2024, 06:00 PM
        schedule = new Schedule(1L,mockDevice, startTime, endTime);
    }

    @Test
    void testConstructor() {
        // Verificamos que los atributos hayan sido correctamente asignados
        assertNotNull(schedule.getDevice());
        assertEquals(LocalDateTime.of(2024, 11, 15, 8, 0), schedule.getStartTime());
        assertEquals(LocalDateTime.of(2024, 11, 15, 18, 0), schedule.getEndTime());
    }

    @Test
    void testIsDeviceOn_ShouldReturnTrue_WhenCurrentTimeIsWithinRange() {
        // Simulamos que la hora actual esté dentro del rango
        LocalDateTime currentTime = LocalDateTime.of(2024, 11, 15, 12, 0); // 12:00 PM

        // Establecemos que el método LocalDateTime.now() devuelva nuestra fecha simulada
        try (var mock = mockStatic(LocalDateTime.class)) {
            mock.when(LocalDateTime::now).thenReturn(currentTime);

            assertTrue(schedule.isDeviceOn(), "El dispositivo debería estar encendido dentro del rango de tiempo.");
        }
    }

    @Test
    void testIsDeviceOn_ShouldReturnFalse_WhenCurrentTimeIsBeforeStartTime() {
        // Simulamos que la hora actual sea antes del tiempo de inicio
        LocalDateTime currentTime = LocalDateTime.of(2024, 11, 15, 7, 30); // 07:30 AM

        try (var mock = mockStatic(LocalDateTime.class)) {
            mock.when(LocalDateTime::now).thenReturn(currentTime);

            assertFalse(schedule.isDeviceOn(), "El dispositivo debería estar apagado antes del tiempo de inicio.");
        }
    }

    @Test
    void testIsDeviceOn_ShouldReturnFalse_WhenCurrentTimeIsAfterEndTime() {
        // Simulamos que la hora actual esté después del tiempo de finalización
        LocalDateTime currentTime = LocalDateTime.of(2024, 11, 15, 18, 30); // 06:30 PM

        try (var mock = mockStatic(LocalDateTime.class)) {
            mock.when(LocalDateTime::now).thenReturn(currentTime);

            assertFalse(schedule.isDeviceOn(), "El dispositivo debería estar apagado después del tiempo de finalización.");
        }
    }
}