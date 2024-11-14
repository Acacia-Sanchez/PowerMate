package org.factoriaf5.powermate.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "devices")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Long userId;

    @Column(name = "device_name", nullable = false)
    private String name;

    @Column(name = "power", nullable = false)
    private int power;

    @Column(name = "status", nullable = false)
    private boolean status;

    public Device(Long id, Long userId, String name, Boolean status, int power) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.status = status;
        this.power = power;
    }

    public Device() {

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
