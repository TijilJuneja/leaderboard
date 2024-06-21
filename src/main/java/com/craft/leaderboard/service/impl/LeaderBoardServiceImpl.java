package com.craft.leaderboard.service.impl;

import com.craft.leaderboard.exception.LeaderboardException;
import com.craft.leaderboard.models.PlayerScore;
import com.craft.leaderboard.models.response.TopPlayersResponse;
import com.craft.leaderboard.service.LeaderBoardService;
import com.craft.leaderboard.service.RetryService;
import com.craft.leaderboard.utils.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LeaderBoardServiceImpl implements LeaderBoardService {

    private final RedisTemplate<String, String> redisTemplate;
    private final RetryService retryService;

    @Autowired
    public LeaderBoardServiceImpl(RedisTemplate<String, String> redisTemplate, RetryService retryService) {
        this.redisTemplate = redisTemplate;
        this.retryService = retryService;
    }

    @Override
    public void publishScore(PlayerScore score) {
        try {
            ValidationUtil.validatePublishScoreRequest(score);
            redisTemplate.opsForZSet().add("leaderboard", score.getId(), score.getScore());
            log.info("publishScore -- published player score for id :{}, and score :{}", score.getId(), score.getScore());
        }
        catch (LeaderboardException e){
            log.error("publishScore -- Exception in saving player Score in database", e);
            throw e;
        }
        catch (Exception e){
            log.error("publishScore -- Exception in saving player Score in database", e);
            retryService.publishForRetry(score);
            throw e;
        }
    }

    @Override
    public TopPlayersResponse fetchTopPlayers(int topNPlayers) {
        try{
            List<PlayerScore> playerScores =  Objects.requireNonNull(redisTemplate.opsForZSet().reverseRangeWithScores("leaderboard", 0, topNPlayers))
                    .stream()
                    .map(entry -> new PlayerScore(entry.getValue(), entry.getScore()))
                    .collect(Collectors.toList());
            TopPlayersResponse topPlayersResponse = new TopPlayersResponse();
            topPlayersResponse.setPlayerScores(playerScores);
            return topPlayersResponse;
        }
        catch (LeaderboardException e){
            log.error("publishScore -- Exception in fetching top players score from database", e);
            throw e;
        }
        catch (Exception e){
            log.error("publishScore -- Exception in fetching top players score from database", e);
            throw e;
        }
    }

}
