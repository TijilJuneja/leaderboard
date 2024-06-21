package com.craft.leaderboard.listener;

import com.craft.leaderboard.constants.Constants;
import com.craft.leaderboard.models.PlayerScore;
import com.craft.leaderboard.service.LeaderBoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaListener {
    private final LeaderBoardService leaderBoardService;

    @Autowired
    public KafkaListener(LeaderBoardService leaderBoardService) {
        this.leaderBoardService = leaderBoardService;
    }

    @org.springframework.kafka.annotation.KafkaListener(topics = Constants.KAFKA_TOPIC, groupId = Constants.KAFKA_GROUP_ID
    ,properties = {"spring.json.value.default.type=com.craft.leaderboard.models.PlayerScore"})
    public void consumeDataFromKafka(PlayerScore score) {
        try {
            log.info("consumeDataFromKafka -- data consumption from kafka");
            leaderBoardService.publishScore(score);
        } catch (Exception e) {
            log.error("Could not publish new score - " + e.getMessage());
            return;
        }
        log.debug("Published " + score);
    }
}
