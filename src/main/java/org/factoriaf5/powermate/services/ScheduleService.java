package org.factoriaf5.powermate.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
    private static ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }


    public void changeStatusOn(LocalDateTime startTime) {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        String formattedStartTime = startTime.format(formatter);
        String formattedCurrentTime = currentTime.format(formatter);

        if (formattedStartTime.equals(formattedCurrentTime)) {
            Device.setIsStatus = true;
        }
    }

    public void changeStatusOff(LocalDateTime endTime) {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        String formattedEndTime = endTime.format(formatter);
        String formattedCurrentTime = currentTime.format(formatter);

        if (formattedEndTime.equals(formattedCurrentTime)) {
            Device.setIsStatus = false;
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
