package org.factoriaf5.powermate.controllers;

import java.util.List;

import org.factoriaf5.powermate.dtos.DeviceDTO;
import org.factoriaf5.powermate.models.Device;
import org.factoriaf5.powermate.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class DeviceController {

    @Autowired
    DeviceService service;

    @PostMapping(path = "/admin/devices")
    public ResponseEntity<DeviceDTO> addDevice(@RequestBody DeviceDTO device) {
        return new ResponseEntity<>(service.createDevice(device), HttpStatus.CREATED);
    }

    @GetMapping(path = "/devices")
    public ResponseEntity<List<DeviceDTO>> getDevice() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @PutMapping(path = "/admin/devices/{deviceId}")
    public ResponseEntity<DeviceDTO> updateDevice(@RequestBody DeviceDTO device, @PathVariable Long deviceId) {
        return new ResponseEntity<>(service.updateDevice(device, deviceId), HttpStatus.OK);
    }

    @PatchMapping(path = "/devices/{deviceId}")
    public ResponseEntity<DeviceDTO> updateStatus(@PathVariable Long deviceId, @RequestParam Boolean status) {
        return new ResponseEntity<>(service.updateStatus(deviceId, status), HttpStatus.OK);
    }

    @DeleteMapping(path = "/admin/devices/{deviceId}")
    public ResponseEntity<Device> deleteDevice(@PathVariable Long deviceId) {
        service.deleteDevice(deviceId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
