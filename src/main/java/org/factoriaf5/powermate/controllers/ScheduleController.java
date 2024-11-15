package org.factoriaf5.powermate.controllers;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schedule/device")
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
    


}
