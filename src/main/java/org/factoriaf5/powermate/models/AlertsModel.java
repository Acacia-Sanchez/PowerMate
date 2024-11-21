package org.factoriaf5.powermate.models;

import jakarta.persistence.*;

@Entity
@Table(name = "alerts")
public class AlertsModel {


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id")
private Long id;
@OneToOne
@JoinColumn(name = "device_id")
private Device device;
@Column(name = "threshold")
private double threshold;

public AlertsModel(){

}

public AlertsModel(long id){
    this.id=id;
}

public AlertsModel(long id, Device device, double threshold){
    this.id = id;
    this.device = device;
    this.threshold = threshold;
}

public Long getId() {
    return id;
}
public Device getDevice() {
    return device;
}
public double getThreshold() {
    return threshold;
}
public void setId(Long id) {
    this.id = id;
}
public void setDevice(Device device) {
    this.device = device;
}
public void setThreshold(double threshold) {
    this.threshold = threshold;
}


}
