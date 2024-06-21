package com.craft.leaderboard.service;

import com.craft.leaderboard.models.PlayerScore;

public interface RetryService {
    void publishForRetry(PlayerScore playerScore);
}
