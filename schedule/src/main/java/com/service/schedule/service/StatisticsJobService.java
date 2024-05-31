package com.service.schedule.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.service.schedule.model.Statistics;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StatisticsJobService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private AtomicInteger count = new AtomicInteger();

    private final String urlPathname = "http://localhost:8090/api/wallets/statistics"; 
    private final RestTemplate restTemplate = new RestTemplate();

    public void executeSampleJob() {
        ResponseEntity<Statistics> statisticsResponse = restTemplate.exchange(urlPathname, HttpMethod.GET, null, Statistics.class);
        
        if (statisticsResponse.getStatusCode() != HttpStatus.OK){
            logger.error("Couldn't retrieve statistics from the server, see logs");
        }

        logger.info(String.format("Overall clients count: %d. With average of %f on a wallet\n", statisticsResponse.getBody().getCount(), statisticsResponse.getBody().getAverageMoney()));
    }

    public int getNumberOfInvocations() {
        return count.get();
    }
}
