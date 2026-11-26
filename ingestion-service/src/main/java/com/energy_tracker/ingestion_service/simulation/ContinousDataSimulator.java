package com.energy_tracker.ingestion_service.simulation;

import com.energy_tracker.ingestion_service.dto.EnergyUsageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Random;

@Slf4j
@Component
public class ContinousDataSimulator implements CommandLineRunner {

    public final RestClient restClient;
    public final Random random = new Random();

    @Value("${simulation.requests-per-interval}")
    private int requestsPerInterval;

    public ContinousDataSimulator() {
        this.restClient = RestClient.builder().baseUrl("http://localhost:8082")
                  .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE).build();
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("ContinousDataSimulator started");
    }

//    @Scheduled(fixedRateString = "${simulation.interval-ms}")
    public void scheduled() {
        for (int i=0;i< requestsPerInterval;i++){
            EnergyUsageDto dto = EnergyUsageDto.builder()
                          .deviceId(random.nextLong(1,3))
                          .energyConsumed(random.nextLong(2,5))
                          .timeStamp(Instant.now().toString())
                          .build();

            try{
                restClient.post()
                          .uri("/api/v1/ingestion/setEnergyConsumption")
                          .body(dto)
                          .retrieve()
                          .toBodilessEntity();
            }catch(Exception e){
                log.error(e.getMessage());
            }
        }
    }
}
