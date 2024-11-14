package org.factoriaf5.powermate.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
    private static ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }


    public void changeStatusOn(LocalDateTime startTime){
        LocalDateTime currentTime = LocalDateTime.now();
        if (startTime == currentTime){
        DeviceController.setIsStatus = true;
        }
        
    }

    public void changeStatusOff(LocalDateTime endTime){
        LocalDateTime currentTime = LocalDateTime.now();
        if (endTime == currentTime){
            DeviceController.setIsStatus = false;
        }
    }

    public void createSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public void updateSchedule(Long id, Schedule schedule){
        return scheduleRepository.findById(id)
            .map(schedule -> {
                schedule.setStartTime(schedule.getStartTime());
                schedule.setEndTime(schedule.getEndTime());
                return scheduleRepository.save(schedule);
            })
            .orElseThrow(() -> new RuntimeException("Schedule not found"));
    }

    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
        }
    
    public void getAllSchedules(Long id){
        return scheduleRepository.findAll();
    }
    

}
