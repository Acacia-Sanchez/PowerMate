package org.factoriaf5.powermate.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.factoriaf5.powermate.models.Device;
import org.factoriaf5.powermate.models.Schedule;
import org.factoriaf5.powermate.repositories.DeviceRepository;
import org.factoriaf5.powermate.repositories.ScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ScheduleServiceTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private ScheduleService scheduleService;

    private Schedule schedule;
    private Device device;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        //Schedule de ejemplo
        schedule = new Schedule();
        schedule.setId(1L);
        schedule.setDeviceId(1L);
        schedule.setStartTime(LocalDateTime.now().minusHours(1));
        schedule.setEndTime(LocalDateTime.now().plusHours(1));

        //Device de ejemplo
        device = new Device();
        device.setId(1L);
        device.setStatus(false);
    }

    @Test
    void testChangeStatusOn() {
        LocalDateTime startTime = LocalDateTime.now();
        device.setStatus(false);

        scheduleService.changeStatusOn(device, startTime);

        assertTrue(device.isStatus());
        verify(deviceRepository, times(1)).save(device);
    }

    @Test
    void testChangeStatusOff() {
        LocalDateTime endTime = LocalDateTime.now();
        device.setStatus(true);

        scheduleService.changeStatusOff(device, endTime);

        assertFalse(device.isStatus());
        verify(deviceRepository, times(1)).save(device);
    }

    @Test
    void testCreateSchedule() {
        when(scheduleRepository.save(schedule)).thenReturn(schedule);

        scheduleService.createSchedule(schedule);

        verify(scheduleRepository, times(1)).save(schedule);
    }

    @Test
    void testUpdateSchedule() {
        Schedule updatedSchedule = new Schedule();
        updatedSchedule.setStartTime(LocalDateTime.now().plusHours(1));
        updatedSchedule.setEndTime(LocalDateTime.now().plusHours(2));

        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));

        scheduleService.updateSchedule(1L, updatedSchedule);

        assertEquals(updatedSchedule.getStartTime(), schedule.getStartTime());
        assertEquals(updatedSchedule.getEndTime(), schedule.getEndTime());
        verify(scheduleRepository, times(1)).findById(1L);
        verify(scheduleRepository, times(1)).save(schedule);
    }

   /*  @Test
    void testUpdateScheduleNotFound() {
        Schedule updatedSchedule = new Schedule();
        updatedSchedule.setStartTime(LocalDateTime.now().plusHours(1));

        when(scheduleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> scheduleService.updateSchedule(1L, updatedSchedule));
        verify(scheduleRepository, times(1)).findById(1L);
        verify(scheduleRepository, never()).save(any(Schedule.class));
    } */

    @Test
    void testDeleteSchedule() {
        doNothing().when(scheduleRepository).deleteById(1L);

        scheduleService.deleteSchedule(1L);

        verify(scheduleRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetAllSchedules() {
        List<Schedule> schedules = Arrays.asList(schedule);
        when(scheduleRepository.findByDeviceId(1L)).thenReturn(schedules);

        List<Schedule> result = scheduleService.getAllSchedules(1L);

        assertEquals(1, result.size());
        assertEquals(schedule.getDeviceId(), result.get(0).getDeviceId());
        verify(scheduleRepository, times(1)).findByDeviceId(1L);
    }

    @Test
    void testCheckDeviceStatusCorrect() {
        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));
        when(scheduleRepository.findByDeviceId(1L)).thenReturn(Arrays.asList(schedule));

        String result = scheduleService.checkDeviceStatus(1L);

        assertEquals("El dispositivo se encuentra encendido/apagado siguiendo lo programado.", result);
        verify(deviceRepository, times(1)).findById(1L);
        verify(scheduleRepository, times(1)).findByDeviceId(1L);
    }

    @Test
    void testCheckDeviceStatusDeviceShouldBeOn() {
        device.setStatus(false);
        schedule.setDeviceOn(true);

        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));
        when(scheduleRepository.findByDeviceId(1L)).thenReturn(Arrays.asList(schedule));

        String result = scheduleService.checkDeviceStatus(1L);

        assertEquals("El dispositivo debería estar encendido.", result);
    }

    @Test
    void testCheckDeviceStatusDeviceShouldBeOff() {
        device.setStatus(true);
        schedule.setDeviceOn(false);

        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));
        when(scheduleRepository.findByDeviceId(1L)).thenReturn(Arrays.asList(schedule));

        String result = scheduleService.checkDeviceStatus(1L);

        assertEquals("El dispositivo debería estar apagado.", result);
    }
}