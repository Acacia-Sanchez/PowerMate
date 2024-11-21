package org.factoriaf5.powermate.dtos;

import java.time.LocalDateTime;

import org.factoriaf5.powermate.models.ConsRecord;

public class ConsRecordDTO {

    private Long id;
    private double consumption;
    private LocalDateTime timestamp;

    private Long deviceId; // deviceID es la JoinColumn en el model

    public ConsRecordDTO(ConsRecord consRecord) {
        this.id = consRecord.getId();
        this.consumption = consRecord.getConsumption();
        this.timestamp = consRecord.getTimestamp();

        this.deviceId = consRecord.getDevice().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getConsumption() {
        return consumption;
    }

    public void setConsumption(double consumption) {
        this.consumption = consumption;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

}
