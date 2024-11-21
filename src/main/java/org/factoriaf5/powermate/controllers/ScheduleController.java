package org.factoriaf5.powermate.controllers;

import java.util.List;

import org.factoriaf5.powermate.dtos.ScheduleDTO;
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

    ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<ScheduleDTO> createSchedule(@RequestBody ScheduleDTO schedule) {
        return new ResponseEntity<>(scheduleService.createSchedule(schedule), HttpStatus.CREATED);
    }
    
    @PutMapping ("/{scheduleId}")
    public ResponseEntity<ScheduleDTO> updateSchedule(
            @PathVariable Long scheduleId,
            @RequestBody ScheduleDTO scheduleDto) {
                return new ResponseEntity<>(scheduleService.updateSchedule(scheduleId, scheduleDto), HttpStatus.OK);
    }

    @DeleteMapping ("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.noContent().build();
    }

    //Este método es para que te salgan todos los horarios programados de un dispositivo
    @GetMapping ("/{deviceId}")
    public ResponseEntity<List<ScheduleDTO>> getAllSchedules(@PathVariable Long deviceId) {
        return new ResponseEntity<>(scheduleService.getAllSchedulesByDeviceId(deviceId), HttpStatus.OK);
    }

    @GetMapping("/check-status/{deviceId}")
    public ResponseEntity<String> checkDeviceStatus(@PathVariable Long deviceId) {
        String statusMessage = scheduleService.checkDeviceStatus(deviceId);
        return ResponseEntity.ok(statusMessage);
    }

}
