package org.factoriaf5.powermate.services;

import java.time.LocalDateTime;
import java.util.List;

import org.factoriaf5.powermate.dtos.DeviceDTO;
import org.factoriaf5.powermate.dtos.ScheduleDTO;
import org.factoriaf5.powermate.models.Device;
import org.factoriaf5.powermate.models.Schedule;
import org.factoriaf5.powermate.repositories.DeviceRepository;
import org.factoriaf5.powermate.repositories.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
    ScheduleRepository repository;
    DeviceRepository deviceRepository;

public ScheduleService(ScheduleRepository repository, DeviceRepository deviceRepository){
    this.repository = repository;
    this.deviceRepository = deviceRepository;
}

public void changeStatusOn(DeviceDTO deviceDto, LocalDateTime startTime) {
    LocalDateTime currentTime = LocalDateTime.now();
    Device device = deviceRepository.findById(deviceDto.getId()).orElse(null);
    if (startTime.equals(currentTime) && device != null) {
        device.setStatus(true);
        deviceRepository.save(device);
    }
}

public void changeStatusOff(DeviceDTO deviceDto, LocalDateTime endTime) {
    LocalDateTime currentTime = LocalDateTime.now();
    Device device = deviceRepository.findById(deviceDto.getId()).orElse(null);
    if (endTime.equals(currentTime) && device != null) {
        device.setStatus(true);
        deviceRepository.save(device);
    }
}

    public ScheduleDTO createSchedule(ScheduleDTO scheduleDto) {
        Device device = deviceRepository.findById(scheduleDto.getDeviceId()).orElse(null);
        if(device!=null){
            Schedule schedule = new Schedule(scheduleDto.getId(), device, scheduleDto.getStartTime(), scheduleDto.getEndTime());
            return new ScheduleDTO(repository.save(schedule));
        }else {
            throw new RuntimeException("Device not found with deviceId: " + scheduleDto.getDeviceId() + ". Status: " + HttpStatus.NOT_FOUND);
        }
        
    }

    public ScheduleDTO updateSchedule(Long id, ScheduleDTO schedule) {
        Schedule existingSchedule = repository.findById(id).orElse(null);
        if(existingSchedule!=null){
            Device device = deviceRepository.findById(schedule.getDeviceId()).orElse(null);
            existingSchedule.setDevice(device);
            existingSchedule.setStartTime(schedule.getStartTime());
            existingSchedule.setEndTime(schedule.getEndTime());
            return new ScheduleDTO(repository.save(existingSchedule));
        }else{
            throw new RuntimeException("Schedule not found with id: " + id + ". Status: " + HttpStatus.NOT_FOUND);
        }
    }

    public void deleteSchedule(Long id) {
        Schedule schedule = repository.findById(id).orElse(null);
        if(schedule!=null){
            repository.deleteById(id);
        }else{
            throw new RuntimeException("Schedule not found with id: " + id + ". Status: " + HttpStatus.NOT_FOUND);
        }
    }
    
    public List<ScheduleDTO> getAllSchedulesByDeviceId(Long deviceId) {
        return repository.findAll().stream().filter(x -> x.getDevice().getId().equals(deviceId)).map(ScheduleDTO::new).toList();
    }
        

    public String checkDeviceStatus(Long deviceId) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new RuntimeException("No se encontró el dispositivo"));
    
        List<Schedule> schedules = repository.findAll().stream().filter(x -> x.getDevice().getId().equals(deviceId)).toList();
    
        for (Schedule schedule : schedules) {
            if (schedule.isDeviceOn()&&!device.isStatus()) {
                return "El dispositivo debería estar encendido y esta apagado.";
            } 
            if (!schedule.isDeviceOn()&&device.isStatus()){
                return "El dispositivo debería estar apagado y esta encendido.";
            }
        }
        return "El dispositivo se encuentra encendido/apagado siguiendo lo programado.";
    }
}
