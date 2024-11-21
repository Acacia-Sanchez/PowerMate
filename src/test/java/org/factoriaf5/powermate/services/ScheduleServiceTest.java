package org.factoriaf5.powermate.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.factoriaf5.powermate.dtos.ScheduleDTO;
import org.factoriaf5.powermate.models.Device;
import org.factoriaf5.powermate.models.Schedule;
import org.factoriaf5.powermate.repositories.DeviceRepository;
import org.factoriaf5.powermate.repositories.ScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

class ScheduleServiceTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private DeviceRepository deviceRepository;

    private ScheduleService scheduleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa los mocks
        scheduleService = new ScheduleService(scheduleRepository, deviceRepository);
    }
 
    @Test
    void testCreateSchedule() {
        // Arrange: Creamos un dispositivo simulado y un DTO
        Device device = new Device();
        device.setId(1L);
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setDeviceId(1L);
        scheduleDTO.setStartTime(LocalDateTime.of(2024, 11, 21, 10, 0));
        scheduleDTO.setEndTime(LocalDateTime.of(2024, 11, 21, 12, 0));

        // Simulamos la interacción con el repositorio de dispositivos
        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));
        
        Schedule schedule = new Schedule(scheduleDTO.getId(), device, scheduleDTO.getStartTime(), scheduleDTO.getEndTime());
        when(scheduleRepository.save(any(Schedule.class))).thenReturn(schedule);

        // Act: Llamamos al método de servicio
        ScheduleDTO result = scheduleService.createSchedule(scheduleDTO);

        // Assert: Verificamos que el resultado sea el esperado
        assertNotNull(result, "El resultado no debe ser nulo");
        assertEquals(1L, result.getDeviceId(), "El ID del dispositivo debe coincidir");
        assertEquals(LocalDateTime.of(2024, 11, 21, 10, 0), result.getStartTime(), "La hora de inicio debe coincidir");
        assertEquals(LocalDateTime.of(2024, 11, 21, 12, 0), result.getEndTime(), "La hora de fin debe coincidir");

        // Verificamos que el repositorio de dispositivos fue llamado
        verify(deviceRepository).findById(1L);
    }

    @Test
    void testUpdateSchedule() {
        // Arrange: Creamos un dispositivo y un schedule
        Device device = new Device();
        device.setId(1L);
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setDeviceId(1L);
        scheduleDTO.setStartTime(LocalDateTime.of(2024, 11, 21, 10, 0));
        scheduleDTO.setEndTime(LocalDateTime.of(2024, 11, 21, 12, 0));
        Schedule existingSchedule = new Schedule(1L, device, scheduleDTO.getStartTime(), scheduleDTO.getEndTime());

        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(existingSchedule));
        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));
        when(scheduleRepository.save(any(Schedule.class))).thenReturn(existingSchedule);

        // Act: Llamamos al método de servicio para actualizar el schedule
        ScheduleDTO result = scheduleService.updateSchedule(1L, scheduleDTO);

        // Assert: Verificamos que la actualización es correcta
        assertNotNull(result, "El resultado no debe ser nulo");
        assertEquals(1L, result.getId(), "El ID del schedule debe coincidir");
        assertEquals(1L, result.getDeviceId(), "El ID del dispositivo debe coincidir");
        assertEquals(scheduleDTO.getStartTime(), result.getStartTime(), "La hora de inicio debe coincidir");
        assertEquals(scheduleDTO.getEndTime(), result.getEndTime(), "La hora de fin debe coincidir");

        // Verificamos que el repositorio de schedules fue llamado
        verify(scheduleRepository).findById(1L);
    }

    @Test
    void testDeleteSchedule() {
        // Arrange: Creamos un schedule a eliminar
        Schedule schedule = new Schedule();
        schedule.setId(1L);
        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));

        // Act: Llamamos al método de eliminar
        scheduleService.deleteSchedule(1L);

        // Assert: Verificamos que el método de eliminación fue llamado
        verify(scheduleRepository).deleteById(1L);
    }

    @Test
    void testCheckDeviceStatus() {
        // Arrange: Creamos un dispositivo y schedules
        Device device = new Device();
        device.setId(1L);
        device.setStatus(true); // El dispositivo está encendido

        Schedule scheduleOn = new Schedule();
        scheduleOn.setDevice(device);
        scheduleOn.setStartTime(LocalDateTime.now().minusMinutes(10));
        scheduleOn.setEndTime(LocalDateTime.now().plusMinutes(10));
        scheduleOn.setDeviceOn(true);

        Schedule scheduleOff = new Schedule();
        scheduleOff.setDevice(device);
        scheduleOff.setStartTime(LocalDateTime.now().plusMinutes(10));
        scheduleOff.setEndTime(LocalDateTime.now().plusMinutes(20));
        scheduleOff.setDeviceOn(false);

        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));
        when(scheduleRepository.findAll()).thenReturn(List.of(scheduleOn, scheduleOff));

        // Act: Llamamos al método checkDeviceStatus
        String status = scheduleService.checkDeviceStatus(1L);

        // Assert: Verificamos que el mensaje de estado sea correcto
        assertEquals("El dispositivo se encuentra encendido/apagado siguiendo lo programado.", status);

        // Verificamos que el repositorio de dispositivos fue llamado
        verify(deviceRepository).findById(1L);
    }
}
