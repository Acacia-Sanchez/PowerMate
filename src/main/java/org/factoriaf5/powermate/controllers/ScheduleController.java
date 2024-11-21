package org.factoriaf5.powermate.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.factoriaf5.powermate.models.Device;
import org.factoriaf5.powermate.models.Schedule;
import org.factoriaf5.powermate.repositories.DeviceRepository;
import org.factoriaf5.powermate.services.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final DeviceRepository deviceRepository;

    public ScheduleController(ScheduleService scheduleService, DeviceRepository deviceRepository) {
        this.scheduleService = scheduleService;
        this.deviceRepository = deviceRepository;
    }

    @PostMapping
    public ResponseEntity<Schedule> createSchedule(
            @RequestBody Long deviceId,
            @RequestBody LocalDateTime startTime,
            @RequestBody LocalDateTime endTime) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new RuntimeException("Dispositivo no encontrado"));
        Schedule schedule = new Schedule(device, startTime, endTime);
        scheduleService.createSchedule(schedule);
        return ResponseEntity.status(HttpStatus.CREATED).body(schedule);
    }
    
    @PutMapping ("/{scheduleId}")
    public ResponseEntity<Schedule> updateSchedule(
            @PathVariable Long scheduleId,
            @RequestBody LocalDateTime startTime,
            @RequestBody LocalDateTime endTime) {
        Schedule schedule = new Schedule();
        schedule.setId(scheduleId);
        schedule.setStartTime(startTime);
        schedule.setEndTime(endTime);
        scheduleService.updateSchedule(scheduleId, schedule);
        return ResponseEntity.ok(schedule);
    }

    @DeleteMapping ("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.noContent().build();
    }

    //Este método es para que te salgan todos los horarios programados de un dispositivo
    @GetMapping ("/{deviceId}")
    public ResponseEntity<List<Schedule>> getAllSchedules(@PathVariable Long deviceId) {
        List<Schedule> schedules = scheduleService.getAllSchedulesByDeviceId(deviceId);
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/check-status/{deviceId}")
    public ResponseEntity<String> checkDeviceStatus(@PathVariable Long deviceId) {
        String statusMessage = scheduleService.checkDeviceStatus(deviceId);
        return ResponseEntity.ok(statusMessage);
    }

}
