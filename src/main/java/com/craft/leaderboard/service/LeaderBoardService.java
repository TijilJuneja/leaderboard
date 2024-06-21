package com.craft.leaderboard.service;

import com.craft.leaderboard.models.PlayerScore;
import com.craft.leaderboard.models.response.TopPlayersResponse;

import java.util.List;

public interface LeaderBoardService {
    void publishScore(PlayerScore score);

    TopPlayersResponse fetchTopPlayers(int n);
}
