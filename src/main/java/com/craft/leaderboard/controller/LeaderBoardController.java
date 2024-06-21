package com.craft.leaderboard.controller;

import com.craft.leaderboard.models.PlayerScore;
import com.craft.leaderboard.models.response.TopPlayersResponse;
import com.craft.leaderboard.service.LeaderBoardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import static com.craft.leaderboard.constants.Constants.KAFKA_TOPIC;

@RestController
@RequestMapping("/v1")
@Slf4j
public class LeaderBoardController extends BaseController{
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper;
    private final LeaderBoardService leaderBoardService;

    @Autowired
    public LeaderBoardController(KafkaTemplate<String, String> kafkaTemplate,
                                 ObjectMapper mapper,
                                 LeaderBoardService leaderBoardService) {
        this.kafkaTemplate = kafkaTemplate;
        this.mapper = mapper;
        this.leaderBoardService = leaderBoardService;
    }
    @PostMapping( "/publish-score")
    public void produceGameScore(@RequestBody PlayerScore playerScore){
        try{
            kafkaTemplate.send(KAFKA_TOPIC, playerScore.getId(), mapper.writeValueAsString(playerScore));
        }
        catch (Exception e){
            log.error("Error in publishing test score to kafka", e);
        }
    }

    @GetMapping("/top/players")
    public ResponseEntity<TopPlayersResponse> getTopPlayers(@RequestParam("range") int topNPlayers){
        TopPlayersResponse topPlayersResponse = new TopPlayersResponse();
        try {
            topPlayersResponse = leaderBoardService.fetchTopPlayers(topNPlayers);
            return ResponseEntity.ok().body(buildSuccessResponse(topPlayersResponse));
        }
        catch (Exception e){
            log.error("getTopPlayers -- Error in fetching top players score", e);
            return ResponseEntity.badRequest().body(buildErrorResponse(topPlayersResponse, e.getMessage()));
        }
    }
}
