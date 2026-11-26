package com.energy_tracker.ingestion_service.simulation;

import com.energy_tracker.ingestion_service.dto.EnergyUsageDto;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Instant;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Slf4j
public class ParallelDataSimulator implements CommandLineRunner {
    public final ExecutorService executorService;
    public final RestClient restClient;
    public final Random random=new Random();

    @Value("${simulation.parallel-threads}")
    private int parallelThreads;

    @Value("${simulation.requests-per-interval}")
    public int requestsPerInterval;

    public ParallelDataSimulator(@Value("${simulation.parallel-threads}") int parallelThreads){
        this.parallelThreads = parallelThreads;
        executorService = Executors.newFixedThreadPool(parallelThreads);
        this.restClient = RestClient.builder().baseUrl("http://localhost:8082")
                                    .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE).build();
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("ParallelDataSimulator started...");
    }

    @Scheduled(fixedRateString = "${simulation.interval-ms}")
    public void sendMockData() {
        int batchSize= requestsPerInterval/parallelThreads;
        int remainder= requestsPerInterval%parallelThreads;

        for(int i=0;i<parallelThreads;i++){
            int requestsForThread = batchSize + (i < remainder ? 1: 0);
            executorService.submit(()->{
                for (int j = 0; j < requestsForThread; j++) {
                    EnergyUsageDto dto= EnergyUsageDto.builder()
                                                      .deviceId(random.nextLong(1,3))
                                                      .energyConsumed(Math.round(random.nextDouble(0.5,5)))
                                                      .timeStamp(Instant.now().toString())
                                                      .build();

                    try{
                        restClient.post()
                                  .uri("/api/v1/ingestion/setEnergyConsumption")
                                  .body(dto)
                                  .retrieve()
                                  .toBodilessEntity();
                    }catch(Exception e){
                        log.error("Failed to send data: "+e.getMessage());
                    }
                }
            });
        }
    }

    @PreDestroy
    public void shutdown(){
        executorService.shutdown();
        log.info("ParallelDataSimulator shutdown...");
    }
}
