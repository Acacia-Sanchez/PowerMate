package org.factoriaf5.powermate.repositories;
import org.factoriaf5.powermate.models.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device,Long> {
    public boolean existsByName(String name);
}
