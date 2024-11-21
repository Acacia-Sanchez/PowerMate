package org.factoriaf5.powermate.controllers;

import org.factoriaf5.powermate.dtos.ConsRecordDTO;
import org.factoriaf5.powermate.models.ConsRecord;
import org.factoriaf5.powermate.services.ConsRecordService;
import org.factoriaf5.powermate.repositories.ConsRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/consumption")
public class ConsRecordController {

    private final ConsRecordService consRecordService;

    @Autowired
    public ConsRecordController(ConsRecordService consRecordService, ConsRecordRepository consRecordRepository) {
        this.consRecordService = consRecordService;
    }

    @PostMapping("/{deviceId}")
    public ResponseEntity<ConsRecordDTO> recordConsumption(@PathVariable Long deviceId) {

        ConsRecord consRecord = consRecordService.recordConsumption(deviceId);
        ConsRecordDTO responseDTO = new ConsRecordDTO(consRecord);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{deviceId}/totalConsumption")
    public ResponseEntity<Double> getTotalConsumption(@PathVariable Long deviceId) {
        double totalConsumption = consRecordService.getTotalConsumption(deviceId);
        return ResponseEntity.ok(totalConsumption);
    }
    
}
