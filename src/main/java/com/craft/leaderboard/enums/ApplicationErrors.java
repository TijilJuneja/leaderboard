package com.craft.leaderboard.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplicationErrors {
    EMPTY_RESPONSE("-22"),
    PROFILE_ERROR("-4"),
    UNABLE_TO_FETCH_AUTH_TOKEN("-5"),
    UNABLE_TO_SEND_MESSAGE("-8"),
    VALIDATION_ERROR("-3");

    private final String errorCode;
}
