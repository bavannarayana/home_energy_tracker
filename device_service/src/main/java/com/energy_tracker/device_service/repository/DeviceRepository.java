package com.energy_tracker.device_service.repository;

import com.energy_tracker.device_service.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device,Long> {
    public List<Device> findAllByUserId(Long customerId);
}
