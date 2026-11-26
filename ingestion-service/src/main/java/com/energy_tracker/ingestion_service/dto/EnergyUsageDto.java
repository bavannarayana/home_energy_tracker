package com.energy_tracker.ingestion_service.dto;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
public record EnergyUsageDto(
        Long deviceId,
        double energyConsumed,
        String timeStamp
        ) {}
