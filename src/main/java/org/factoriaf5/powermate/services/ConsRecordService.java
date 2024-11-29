package org.factoriaf5.powermate.services;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import org.factoriaf5.powermate.models.ConsRecord;
import org.factoriaf5.powermate.models.Device;
import org.factoriaf5.powermate.repositories.ConsRecordRepository;
import org.factoriaf5.powermate.repositories.DeviceRepository;
import org.springframework.stereotype.Service;

@Service
public class ConsRecordService {

    private double consumption = 0.0;
    private long timeOn = 0;
    private long durationInNano = 0;
    private double durationInHours = 0.0;

    private final ConsRecordRepository consRecordRepository;
    private final DeviceRepository deviceRepository;

    private final Map<Long, Double> totalConsumptions = new HashMap<>();

    public ConsRecordService(ConsRecordRepository consRecordRepository, DeviceRepository deviceRepository) {
        this.consRecordRepository = consRecordRepository;
        this.deviceRepository = deviceRepository;
    }

    public Device findDeviceById(Long deviceId) {
        return deviceRepository.findById(deviceId).orElse(null);
    }

    public ConsRecord recordConsumption(Long deviceId) {
        Device device = findDeviceById(deviceId);
        durationInNano = 0;

        while (LocalTime.now().isBefore(LocalTime.of(23, 59, 59))) {
            try {
                if (device.isStatus()) {
                    if (timeOn == 0) {
                        timeOn = System.nanoTime();
                    }
                } else {
                    if (timeOn != 0) {
                        durationInNano += System.nanoTime() - timeOn;
                        timeOn = 0;
                    }
                }

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (device.isStatus() && timeOn != 0) {
            durationInNano += System.nanoTime() - timeOn;
        }

        durationInHours = durationInNano / 3_600_000_000_000.0;
        consumption = device.getPower() * durationInHours;
        totalConsumptions.put(device.getId(), totalConsumptions.getOrDefault(device.getId(), 0.0) + consumption);

        ConsRecord consRecord = new ConsRecord(device, consumption, LocalDateTime.now());
        consRecordRepository.save(consRecord);
        return consRecord;
    }

    public double getTotalConsumption(Long deviceId) {
        return totalConsumptions.getOrDefault(deviceId, 0.0);
    }
}