package org.factoriaf5.powermate.models;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne  // Relación de muchos a uno con Device (clave foránea)
    @JoinColumn(name = "device_id")  // La columna device_id en la tabla Schedule se refiere a Device
    private Device device;  // Aquí solo usamos la relación

    private LocalDateTime startTime;  // Hora de encendido
    private LocalDateTime endTime;    // Hora de apagado

    public Schedule() {
    } 
    public Schedule(Device device, LocalDateTime startTime, LocalDateTime endTime) { this.device = device; this.startTime = startTime; this.endTime = endTime; }
    
    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    // Método que verifica si el dispositivo debe estar encendido o apagado según la hora actual
    public boolean isDeviceOn() {
        LocalDateTime now = LocalDateTime.now();
        return !now.isBefore(startTime) && !now.isAfter(endTime);  // Si la hora actual está dentro del rango, está encendido
    }
}
