package com.energy_tracker.kafka.event;

import lombok.Builder;

import java.time.Instant;

@Builder
public record EnergyUsageEvent (
        Long deviceId,
        double energyConsumed,
        Instant timeStamp
) {}