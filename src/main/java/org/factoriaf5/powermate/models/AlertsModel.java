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
private Device deviceid;
@Column(name = "threshold")
private double threshold;

public AlertsModel(){

}

public AlertsModel(long id){
    this.id=id;
}

public AlertsModel(long id, long userid, Device deviceid, double threshold){
    this.id = id;
    this.deviceid = deviceid;
    this.threshold = threshold;
}

public Long getId() {
    return id;
}
public Device getDeviceid() {
    return deviceid;
}
public double getThreshold() {
    return threshold;
}
public void setId(Long id) {
    this.id = id;
}
public void setDeviceid(Device deviceid) {
    this.deviceid = deviceid;
}
public void setThreshold(double threshold) {
    this.threshold = threshold;
}


}
