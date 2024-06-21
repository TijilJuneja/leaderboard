package com.craft.leaderboard.models.response;

import com.craft.leaderboard.models.PlayerScore;
import com.craft.leaderboard.models.base.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopPlayersResponse extends BaseResponse {
    List<PlayerScore> playerScores;
}
