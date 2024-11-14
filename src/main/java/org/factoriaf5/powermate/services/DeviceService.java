package org.factoriaf5.powermate.services;

import java.util.List;
import java.util.Optional;

import org.factoriaf5.powermate.models.Device;
import org.factoriaf5.powermate.repositories.DeviceRepository;
import org.springframework.http.HttpStatus;

public class DeviceService {

    DeviceRepository repository;

    public DeviceService(DeviceRepository repository) {
        this.repository = repository;
    }

    //implementar metodos del crud

    public List<Device> getAll(){
        return repository.findAll();
    }

    public Optional<Device> getDevicesById(Long id){
        return repository.findById(id);
    }

    public Device createDevice(Device device){
        return repository.save(device);
    }

    public Device updateDevice(Device device, long deviceId){
        Device existingDevice = repository.findById(deviceId).orElse(null);
        if(existingDevice!=null){
            existingDevice.setName(device.getName());
            existingDevice.setPower(device.getPower());
            existingDevice.setStatus(device.isStatus());
            return repository.save(existingDevice);
        }else{
            throw new RuntimeException("Device not found with id: " + deviceId + ". Status: " + HttpStatus.NOT_FOUND);
        }
    }
    public Device updateStatus(Long deviceId, boolean status){
        Device device = repository.findById(deviceId).orElse(null);
        if(device!=null){
            device.setStatus(status);
            return repository.save(device);
        }else{
            throw new RuntimeException("Device not found with id: " + deviceId + ". Status: " + HttpStatus.NOT_FOUND);
        }
    }
    public void deleteDevice(Long deviceId){
        Device device = repository.findById(deviceId).orElse(null);
        if(device!=null){
            repository.deleteById(deviceId);
        }else{
            throw new RuntimeException("Device not found with id: " + deviceId + ". Status: " + HttpStatus.NOT_FOUND);
        }
    }
}