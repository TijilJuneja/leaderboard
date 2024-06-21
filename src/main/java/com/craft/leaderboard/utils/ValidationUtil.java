package com.craft.leaderboard.utils;

import com.craft.leaderboard.enums.ApplicationErrors;
import com.craft.leaderboard.enums.ResourceLayer;
import com.craft.leaderboard.exception.LeaderboardException;
import com.craft.leaderboard.models.PlayerScore;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@UtilityClass
@Slf4j
public class ValidationUtil {
    public static void validatePublishScoreRequest(PlayerScore playerScore){
        if(playerScore.getId().isEmpty()){
            log.error("validatePublishScoreRequest -- PlayerId cannot be null/empty");
            throw new LeaderboardException("PlayerId cannot be null/empty", ApplicationErrors.VALIDATION_ERROR, ResourceLayer.SERVICE);
        }
        if(Objects.isNull(playerScore.getScore())){
            log.error("validatePublishScoreRequest -- Player score cannot be null/empty");
            throw new LeaderboardException("Player score cannot be null/empty", ApplicationErrors.VALIDATION_ERROR, ResourceLayer.SERVICE);
        }
    }
}
