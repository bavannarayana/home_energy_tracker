package com.energy_tracker.device_service.service;

import com.energy_tracker.device_service.dto.DeviceDto;
import com.energy_tracker.device_service.exception.UserNotFoundException;
import com.energy_tracker.device_service.model.Device;
import com.energy_tracker.device_service.repository.DeviceRepository;
import com.energy_tracker.device_service.exception.DeviceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DeviceService {
    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    // Register new Device if not already exists
    public DeviceDto registerDevice(DeviceDto deviceDto){
        Device d=deviceRepository.findById(deviceDto.getId()).orElse(null);

        if(d!=null){
            throw new DeviceNotFoundException("Device already exists");
        }

        Device device = toDevice(deviceDto);
        return toDeviceDto(deviceRepository.save(device));
    }

    // Update existing device
    public DeviceDto updateDevice(DeviceDto deviceDto){
        deviceRepository.findById(deviceDto.getId())
                        .orElseThrow(()-> new DeviceNotFoundException("Device not found"));

        Device device=toDevice(deviceDto);
        return toDeviceDto(deviceRepository.save(device));
    }

    // Get all devices by checking userId if exists otherwise throw exception
    public List<Device> getAllDevices(Long userId) {
        deviceRepository.findById(userId)
                        .orElseThrow(()-> new UserNotFoundException("User not found"));

        return deviceRepository.findAllByUserId(userId);
    }

    // Deletion method
    public String deleteDevice(Long deviceId){
        Device existingDevice=deviceRepository.findById(deviceId)
                                                 .orElse(null);

        if(existingDevice==null){
            throw new DeviceNotFoundException("Device with id:"+deviceId+" does not exist");
        }
        deviceRepository.deleteById(deviceId);
        return "Device with id: "+deviceId+" has been deleted";
    }

    public Device toDevice(DeviceDto device){
        return Device.builder()
                     .id(device.getId())
                     .deviceName(device.getDeviceName())
                     .type(device.getType())
                     .location(device.getLocation())
                     .userId(device.getUserId())
                     .build();
    }

    public DeviceDto toDeviceDto(Device device){
        return DeviceDto.builder()
                        .id(device.getId())
                     .deviceName(device.getDeviceName())
                     .type(device.getType())
                     .location(device.getLocation())
                        .userId(device.getUserId())
                     .build();
    }
}
