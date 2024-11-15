package org.factoriaf5.powermate.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.factoriaf5.powermate.models.Device;
import org.factoriaf5.powermate.models.Schedule;
import org.factoriaf5.powermate.repositories.DeviceRepository;
import org.factoriaf5.powermate.repositories.ScheduleRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ScheduleServiceTest {

    @InjectMocks
    ScheduleService service;

    @Mock
    ScheduleRepository scheduleRepository;

    @Mock
    DeviceRepository deviceRepository;

    @Test
    void testGetAllSchedules() {
        List<Schedule> schedules = new ArrayList<>();
        Schedule schedule1 = new Schedule();
        schedule1.setId(1L);
        schedule1.setStartTime(LocalDateTime.now());
        schedule1.setEndTime(LocalDateTime.now().plusHours(1));
        schedule1.setDeviceId(1L);

        Schedule schedule2 = new Schedule();
        schedule2.setId(2L);
        schedule2.setStartTime(LocalDateTime.now().plusHours(2));
        schedule2.setEndTime(LocalDateTime.now().plusHours(3));
        schedule2.setDeviceId(1L);

        schedules.add(schedule1);
        schedules.add(schedule2);

        when(scheduleRepository.findByDeviceId(1L)).thenReturn(schedules);

        List<Schedule> result = service.getAllSchedules(1L);

        assertThat(result.size(), equalTo(2));
        assertThat(result, contains(schedule1, schedule2));
    }

    @Test
    void testChangeStatusOn() {
        Device device = new Device();
        device.setId(1L);
        device.setStatus(false);

        LocalDateTime currentTime = LocalDateTime.now();
        service.changeStatusOn(device, currentTime);

        verify(deviceRepository).save(device);
        assertThat(device.isStatus(), equalTo(true));
    }

    @Test
    void testChangeStatusOff() {
        Device device = new Device();
        device.setId(1L);
        device.setStatus(true);

        LocalDateTime currentTime = LocalDateTime.now();
        service.changeStatusOff(device, currentTime);

        verify(deviceRepository).save(device);
        assertThat(device.isStatus(), equalTo(false));
    }

    @Test
    void testCheckDeviceStatus_ValidStatus() {
        Device device = new Device();
        device.setId(1L);
        device.setStatus(true);

        Schedule schedule = new Schedule();
        schedule.setDeviceId(1L);
        schedule.setDeviceOn(true);

        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));
        when(scheduleRepository.findByDeviceId(1L)).thenReturn(List.of(schedule));

        String result = service.checkDeviceStatus(1L);

        assertThat(result, equalTo("El dispositivo se encuentra encendido/apagado siguiendo lo programado."));
    }

    @Test
    void testCheckDeviceStatus_ShouldBeOn() {
        Device device = new Device();
        device.setId(1L);
        device.setStatus(false);

        Schedule schedule = new Schedule();
        schedule.setDeviceId(1L);
        schedule.setDeviceOn(true);

        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));
        when(scheduleRepository.findByDeviceId(1L)).thenReturn(List.of(schedule));

        String result = service.checkDeviceStatus(1L);

        assertThat(result, equalTo("El dispositivo debería estar encendido."));
    }

    @Test
    void testCheckDeviceStatus_ShouldBeOff() {
    Device device = new Device();
    device.setId(1L);
    device.setStatus(true);

    Schedule schedule = new Schedule();
    schedule.setDeviceId(1L);
    schedule.setDeviceOn(false);

    when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));
    when(scheduleRepository.findByDeviceId(1L)).thenReturn(List.of(schedule));

    String result = service.checkDeviceStatus(1L);

    assertThat(result, equalTo("El dispositivo debería estar apagado."));
}
}