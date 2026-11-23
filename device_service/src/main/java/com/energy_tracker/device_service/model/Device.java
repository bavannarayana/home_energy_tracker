package com.energy_tracker.device_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="device")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String deviceName;

    @Enumerated(EnumType.STRING)
    private DeviceType type;

    private String location;

    private Long userId;
}
