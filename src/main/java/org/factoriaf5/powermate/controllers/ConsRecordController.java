package org.factoriaf5.powermate.controllers;

import org.factoriaf5.powermate.models.ConsRecord;
import org.factoriaf5.powermate.services.ConsRecordService;
import org.factoriaf5.powermate.repositories.ConsRecordRepository;
import org.factoriaf5.powermate.models.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/consumption")
public class ConsRecordController {

    private final ConsRecordService consRecordService;
    private final ConsRecordRepository consRecordRepository;

    @Autowired
    public ConsRecordController(ConsRecordService consRecordService, ConsRecordRepository consRecordRepository) {
        this.consRecordService = consRecordService;
        this.consRecordRepository = consRecordRepository;
    }

    @PostMapping("/{deviceId}")
    public ResponseEntity<ConsRecord> recordConsumption(@PathVariable Long deviceId) {
        Device device = findDeviceById(deviceId);
        if (device == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        double consumption = consRecordService.recordConsumption(device);
        ConsRecord consRecord = new ConsRecord(device, consumption, LocalDateTime.now());
        consRecordRepository.save(consRecord);

        return ResponseEntity.status(HttpStatus.CREATED).body(consRecord);
    }

    // Método auxiliar para obtener el dispositivo (falta añadir según la lógica de device)
    private Device findDeviceById(Long deviceId) {
        return null;
    }
}
