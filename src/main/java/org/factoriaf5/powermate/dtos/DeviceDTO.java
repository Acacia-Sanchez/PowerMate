package org.factoriaf5.powermate.dtos;

import org.factoriaf5.powermate.models.Device;

public class DeviceDTO {
    private Long id;
    private Long userId;
    private String name;
    private int power;
    private boolean status;
    

    public DeviceDTO(Device device){
        this.id=device.getId();
        this.userId=device.getUser().getId();
        this.name=device.getName();
        this.power=device.getPower();
        this.status=device.isStatus();
    }

    public DeviceDTO(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    
}
