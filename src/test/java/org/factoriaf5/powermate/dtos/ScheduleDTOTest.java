package org.factoriaf5.powermate.dtos;

import static org.junit.jupiter.api.Assertions.*;

import org.factoriaf5.powermate.models.Device;
import org.factoriaf5.powermate.models.Schedule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class ScheduleDTOTest {

    private Schedule schedule;
    private Device device;

    @BeforeEach
    void setUp() {
        // Configurar el dispositivo
        device = new Device();
        device.setId(1L);
        
        // Configurar el horario
        schedule = new Schedule();
        schedule.setId(1L);
        schedule.setDevice(device);
        schedule.setStartTime(LocalDateTime.of(2024, 11, 21, 10, 0));
        schedule.setEndTime(LocalDateTime.of(2024, 11, 21, 12, 0));
    }

    @Test
    void testScheduleDTOConstructor() {
        // Crear DTO usando el constructor que recibe un Schedule
        ScheduleDTO scheduleDTO = new ScheduleDTO(schedule);

        // Validar que los valores sean los esperados
        assertNotNull(scheduleDTO, "El DTO no debe ser nulo.");
        assertEquals(1L, scheduleDTO.getId(), "El ID del schedule debe coincidir.");
        assertEquals(1L, scheduleDTO.getDeviceId(), "El ID del dispositivo debe coincidir.");
        assertEquals(LocalDateTime.of(2024, 11, 21, 10, 0), scheduleDTO.getStartTime(), "La hora de inicio debe coincidir.");
        assertEquals(LocalDateTime.of(2024, 11, 21, 12, 0), scheduleDTO.getEndTime(), "La hora de finalización debe coincidir.");
    }

    @Test
    void testSettersAndGetters() {
        ScheduleDTO scheduleDTO = new ScheduleDTO(schedule);

        // Verificar que se pueden modificar y recuperar los valores correctamente
        scheduleDTO.setId(2L);
        scheduleDTO.setDeviceId(2L);
        scheduleDTO.setStartTime(LocalDateTime.of(2024, 11, 22, 10, 0));
        scheduleDTO.setEndTime(LocalDateTime.of(2024, 11, 22, 12, 0));

        // Verificar que los valores se han actualizado correctamente
        assertEquals(2L, scheduleDTO.getId(), "El ID debe haber sido actualizado.");
        assertEquals(2L, scheduleDTO.getDeviceId(), "El ID del dispositivo debe haber sido actualizado.");
        assertEquals(LocalDateTime.of(2024, 11, 22, 10, 0), scheduleDTO.getStartTime(), "La hora de inicio debe haber sido actualizada.");
        assertEquals(LocalDateTime.of(2024, 11, 22, 12, 0), scheduleDTO.getEndTime(), "La hora de finalización debe haber sido actualizada.");
    }
    
}

