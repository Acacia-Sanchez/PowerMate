package org.factoriaf5.powermate.dtos;

import org.factoriaf5.powermate.models.AlertsModel;

public class AlertsDTO {
    private Long id;
    private Long deviceid;
    private double threshold;

    public AlertsDTO(AlertsModel alerts){
        this.id=alerts.getId();
        this.deviceid=alerts.getDevice().getId();
        this.threshold=alerts.getThreshold();
    }

    public AlertsDTO(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(Long deviceid) {
        this.deviceid = deviceid;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

  
    
}
