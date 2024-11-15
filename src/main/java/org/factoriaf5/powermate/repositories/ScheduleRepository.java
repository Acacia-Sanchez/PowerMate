package org.factoriaf5.powermate.repositories;

import java.util.List;

import org.factoriaf5.powermate.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByDeviceId(Long deviceId);
}
