package org.factoriaf5.powermate.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ConsRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    private double consumption;
    private LocalDateTime timestamp;

    public ConsRecord() {
    }

    public ConsRecord(Device device, double consumption, LocalDateTime timestamp) {
        this.device = device;
        this.consumption = consumption;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
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
}