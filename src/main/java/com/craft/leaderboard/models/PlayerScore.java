package com.craft.leaderboard.models;

import com.craft.leaderboard.models.base.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerScore{
    private String id;
    private Double score;
}
