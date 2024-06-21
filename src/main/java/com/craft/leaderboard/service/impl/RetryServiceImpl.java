package com.craft.leaderboard.service.impl;

import com.craft.leaderboard.models.PlayerScore;
import com.craft.leaderboard.service.RetryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.craft.leaderboard.constants.Constants.KAFKA_RETRY_TOPIC;
import static com.craft.leaderboard.constants.Constants.KAFKA_TOPIC;

@Service
@Slf4j
public class RetryServiceImpl implements RetryService {
    
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper;
    
    @Autowired
    public RetryServiceImpl(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper mapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.mapper = mapper;
    }

    @Override
    public void publishForRetry(PlayerScore playerScore) {
        try{
            log.info("publishForRetry -- publishing player score in DLQ for retrying later");
            kafkaTemplate.send(KAFKA_RETRY_TOPIC, playerScore.getId(), mapper.writeValueAsString(playerScore));
        }
        catch (Exception e){
            log.error("publishForRetry -- Error in publishing test score to kafka", e);
        }
    }
}
