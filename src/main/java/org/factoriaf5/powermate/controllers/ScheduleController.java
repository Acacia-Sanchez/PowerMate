package org.factoriaf5.powermate.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.factoriaf5.powermate.models.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schedule/device{id}")
public class ScheduleController {
    private final Schedule scheduleService;
    public ScheduleController (Schedule scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<Schedule> createSchedule(
            @RequestParam Long deviceId,
            @RequestParam LocalDateTime startTime,
            @RequestParam LocalDateTime endTime) {
        Schedule schedule = new Schedule(deviceId, startTime, endTime);
        Schedule createdSchedule = scheduleService.createSchedule(schedule);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSchedule);
    }
    
    @PutMapping
    public ResponseEntity<Schedule> updateSchedule(
            @RequestParam Long scheduleId,
            @RequestParam LocalDateTime startTime,
            @RequestParam LocalDateTime endTime) {
        Schedule schedule = new Schedule(scheduleId, startTime, endTime);
        Schedule updatedSchedule = scheduleService.updateSchedule(scheduleId, schedule);
        return ResponseEntity.ok(updatedSchedule);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteSchedule(@RequestParam Long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Schedule>> getAllSchedules(@RequestParam Long deviceId) {
        List<Schedule> schedules = scheduleService.getAllSchedules(deviceId);
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/check-status")
    public ResponseEntity<String> checkDeviceStatus(@PathVariable Long deviceId) {
        String statusMessage = scheduleService.checkDeviceStatus(deviceId);
        return ResponseEntity.ok(statusMessage);
    }

}
