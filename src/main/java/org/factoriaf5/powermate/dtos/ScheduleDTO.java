package org.factoriaf5.powermate.dtos;

import java.time.LocalDateTime;

import org.factoriaf5.powermate.models.Schedule;

public class ScheduleDTO {
    private Long id;
    private Long deviceId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public ScheduleDTO(){
    }

    public ScheduleDTO(Schedule schedule) {
    this.id = schedule.getId();
    this.deviceId = schedule.getDevice().getId();
    this.startTime = schedule.getStartTime();
    this.endTime = schedule.getEndTime();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
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
    
}
