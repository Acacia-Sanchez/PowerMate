package org.factoriaf5.powermate.models;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "alerts")
public class Alerts implements Serializable {
//private static final long serialVersionUID = 1L;

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id")
private Long id;
@Column(name = "user_id")
private Long userid;
@Column(name = "device_id")
private long deviceid;
@Column(name = "threshold")

private double threshold;

public Long getId() {
    return id;
}
public Long getUserid() {
    return userid;
}
public long getDeviceid() {
    return deviceid;
}
public double getThreshold() {
    return threshold;
}
public void setId(Long id) {
    this.id = id;
}
public void setUserid(Long userid) {
    this.userid = userid;
}
public void setDeviceid(long deviceid) {
    this.deviceid = deviceid;
}
public void setThreshold(double threshold) {
    this.threshold = threshold;
}

 
}
