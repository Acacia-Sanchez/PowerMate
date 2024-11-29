package org.factoriaf5.powermate.models;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne  
    @JoinColumn(name = "device_id") 
    private Device device;  

    private LocalDateTime startTime; 
    private LocalDateTime endTime;  

    public Schedule() {
    }
    public Schedule(Long id, Device device, LocalDateTime startTime, LocalDateTime endTime) 
    {   this.id = id;
        this.device = device;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
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

    public boolean isDeviceOn() {
        LocalDateTime now = LocalDateTime.now();
        return !now.isBefore(startTime) && !now.isAfter(endTime); 
    }
}
