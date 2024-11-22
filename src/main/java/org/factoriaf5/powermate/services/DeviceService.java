package org.factoriaf5.powermate.services;

import java.util.List;
import java.util.Optional;

import org.factoriaf5.powermate.dtos.DeviceDTO;
import org.factoriaf5.powermate.models.Device;
import org.factoriaf5.powermate.models.User;
import org.factoriaf5.powermate.repositories.DeviceRepository;
import org.factoriaf5.powermate.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

    DeviceRepository repository;
    UserRepository userRepository;

    public DeviceService(DeviceRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public List<DeviceDTO> getAll(){
        return repository.findAll().stream().map(x -> new DeviceDTO(x)).toList();
    }

    public Optional<DeviceDTO> getDevicesById(Long deviceId){
        return repository.findById(deviceId).map(x -> new DeviceDTO(x));
    }

    public DeviceDTO createDevice(DeviceDTO deviceDto){
        User user = userRepository.findById(deviceDto.getUserId()).orElse(null);
        if(user!=null){
            Device device = new Device(deviceDto.getId(), user, deviceDto.getName(), deviceDto.isStatus(), deviceDto.getPower());
            return new DeviceDTO(repository.save(device));
        }else{
            throw new RuntimeException("User not found with deviceId: " + deviceDto.getUserId() + ". Status: " + HttpStatus.NOT_FOUND);
        }
        
    }

    public DeviceDTO updateDevice(DeviceDTO deviceDto, long deviceId){
        Device existingDevice = repository.findById(deviceId).orElse(null);
        if(existingDevice!=null){
            User user = userRepository.findById(deviceDto.getUserId()).orElse(null);
            existingDevice.setName(deviceDto.getName());
            existingDevice.setPower(deviceDto.getPower());
            existingDevice.setStatus(deviceDto.isStatus());
            existingDevice.setUser(user);
            return new DeviceDTO(repository.save(existingDevice));
        }else{
            throw new RuntimeException("User not found with deviceId: " + deviceId + ". Status: " + HttpStatus.NOT_FOUND);
        }
    }

    public DeviceDTO updateStatus(Long deviceId, boolean status){
        Device device = repository.findById(deviceId).orElse(null);
        if(device!=null){
            device.setStatus(status);
            return new DeviceDTO(repository.save(device));
        }else{
            throw new RuntimeException("Device not found with deviceId: " + deviceId + ". Status: " + HttpStatus.NOT_FOUND);
        }
    }

    public void deleteDevice(Long deviceId){
        Device device = repository.findById(deviceId).orElse(null);
        if(device!=null){
            repository.deleteById(deviceId);
        }else{
            throw new RuntimeException("Device not found with deviceId: " + deviceId + ". Status: " + HttpStatus.NOT_FOUND);
        }
    }
}