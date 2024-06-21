package com.craft.leaderboard.service;

import com.craft.leaderboard.models.PlayerScore;
import com.craft.leaderboard.models.response.TopPlayersResponse;
import com.craft.leaderboard.service.impl.LeaderBoardServiceImpl;
import com.craft.leaderboard.utils.ValidationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LeaderBoardServiceImplTest {
    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @Mock
    private ZSetOperations<String, String> zSetOperations;

    @InjectMocks
    private LeaderBoardServiceImpl leaderBoardService;

    @Test
    public void testPublishScore() {
        PlayerScore score = new PlayerScore("playerId", 100.0);
        ValidationUtil.validatePublishScoreRequest(score);

        when(redisTemplate.opsForZSet()).thenReturn(zSetOperations);
        when(zSetOperations.add(anyString(), anyString(), anyDouble())).thenReturn(true);

        leaderBoardService.publishScore(score);

        verify(redisTemplate, times(1)).opsForZSet();
        verify(zSetOperations, times(1)).add(eq("leaderboard"), eq(score.getId()), eq(score.getScore()));
    }

    @Test
    public void testFetchTopPlayers() throws Exception {
        int n = 2;
        PlayerScore score1 = new PlayerScore("playerId1", 100.0);
        PlayerScore score2 = new PlayerScore("playerId2", 200.0);
        Set<ZSetOperations.TypedTuple<String>> scores = new HashSet<>();
        scores.add(ZSetOperations.TypedTuple.of("playerId1", 100.0));
        scores.add(ZSetOperations.TypedTuple.of("playerId2", 200.0));
        when(redisTemplate.opsForZSet()).thenReturn(zSetOperations);
        when(zSetOperations.reverseRangeWithScores("leaderboard", 0, n))
                .thenReturn(scores);

        TopPlayersResponse response = leaderBoardService.fetchTopPlayers(n);

        assertEquals(n, response.getPlayerScores().size());
        assertTrue(response.getPlayerScores().contains(score1));
        assertTrue(response.getPlayerScores().contains(score2));

        verify(redisTemplate, times(1)).opsForZSet();
        verify(zSetOperations, times(1)).reverseRangeWithScores("leaderboard", 0, n);
    }

}
