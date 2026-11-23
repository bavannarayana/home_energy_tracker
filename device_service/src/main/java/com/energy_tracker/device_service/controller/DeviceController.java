package com.energy_tracker.device_service.controller;

import com.energy_tracker.device_service.dto.DeviceDto;
import com.energy_tracker.device_service.model.Device;
import com.energy_tracker.device_service.service.DeviceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/api/device")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService){
        this.deviceService = deviceService;
    }

    @PostMapping("/registerDevice")
    public DeviceDto registerDevice(@RequestBody DeviceDto deviceDto){
        return deviceService.registerDevice(deviceDto);
    }

    @GetMapping("/getAllDevices/{customerId}")
    public List<Device> getAllDevices(@PathVariable("customerId") Long customerId){
        return deviceService.getAllDevices(customerId);
    }

    @PostMapping("/updateDevice")
    public DeviceDto updateDevice(@RequestBody DeviceDto deviceDto){
        return deviceService.updateDevice(deviceDto);
    }

    @DeleteMapping("/deleteDevice/{id}")
    public String deleteDevice(@PathVariable("id") Long id){
        return deviceService.deleteDevice(id);
    }
}