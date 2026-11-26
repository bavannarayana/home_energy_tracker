package com.energy_tracker.ingestion_service.service;

import com.energy_tracker.ingestion_service.dto.EnergyUsageDto;
import com.energy_tracker.kafka.event.EnergyUsageEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
public class IngestionService {
    private final KafkaTemplate<String, EnergyUsageEvent> kafkaTemplate;

    public IngestionService(KafkaTemplate<String, EnergyUsageEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void ingestEnergyUsage(EnergyUsageDto energyUsageDto){
        EnergyUsageEvent energyUsageEvent = EnergyUsageEvent.builder()
                        .deviceId(energyUsageDto.deviceId())
                        .energyConsumed(energyUsageDto.energyConsumed())
                        .timeStamp(Instant.parse(energyUsageDto.timeStamp()))
                        .build();

        kafkaTemplate.send("energy_usage", energyUsageEvent);
        log.info("Energy Usage Event sent to topic");
    }
}
