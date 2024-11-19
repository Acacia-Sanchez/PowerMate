package org.factoriaf5.powermate.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
//import java.time.format.DateTimeFormatter;
import java.util.List;

import org.factoriaf5.powermate.models.Schedule;
import org.factoriaf5.powermate.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
    private ScheduleRepository scheduleRepository;
    private DeviceRepository deviceRepository;

@Autowired
public ScheduleService(ScheduleRepository scheduleRepository, DeviceRepository deviceRepository) {
    this.scheduleRepository = scheduleRepository;
    this.deviceRepository = deviceRepository;
}

public void changeStatusOn(Device device, LocalDateTime startTime) {
    LocalDateTime currentTime = LocalDateTime.now();
    if (startTime.equals(currentTime)) {
        device.setStatus(true);
        deviceRepository.save(device);
    }
}

public void changeStatusOff(Device device, LocalDateTime endTime) {
    LocalDateTime currentTime = LocalDateTime.now();
    if (endTime.equals(currentTime)) {
        device.setStatus(false);
        deviceRepository.save(device);
    }
}

    public void createSchedule(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    public void updateSchedule(Long id, Schedule schedule) {
        scheduleRepository.findById(id)
            .map(existingSchedule -> {
                existingSchedule.setStartTime(schedule.getStartTime());
                existingSchedule.setEndTime(schedule.getEndTime());
                return scheduleRepository.save(existingSchedule);
            })
            .orElseThrow(() -> new RuntimeException("No se encontró el programa"));
    }

    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
        }
    
    public List<Schedule> getAllSchedules(Long deviceId) {
        return scheduleRepository.findByDeviceId(deviceId);
    }
        

    public String checkDeviceStatus(Long deviceId) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new RuntimeException("No se encontró el dispositivo"));
    
        List<Schedule> schedules = scheduleRepository.findByDeviceId(deviceId);
    
        for (Schedule schedule : schedules) {
            if (schedule.isDeviceOn()) {
                if (!device.isStatus()) {
                    return "El dispositivo debería estar encendido.";
                }
            } else {
                if (device.isStatus()) {
                    return "El dispositivo debería estar apagado.";
                }
            }
        }
        return "El dispositivo se encuentra encendido/apagado siguiendo lo programado.";
    }
}
