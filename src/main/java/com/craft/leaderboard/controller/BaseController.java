package com.craft.leaderboard.controller;

import com.craft.leaderboard.models.base.BaseResponse;
import org.springframework.http.HttpStatus;

public class BaseController {
    public <T extends BaseResponse> T buildSuccessResponse(T response) {
        response.setSuccess(true);
        response.setCode(HttpStatus.OK.value());
        response.setMessage(response.getMessage() == null ? "success" : response.getMessage());
        return response;
    }

    public <T extends BaseResponse> T buildSuccessResponse(T response, boolean success) {
        response.setSuccess(success);
        response.setCode(HttpStatus.OK.value());
        response.setMessage(response.getMessage() == null && success ? "success" : response.getMessage() != null ? response.getMessage() : "failed");
        return response;
    }

    public <T extends BaseResponse> T buildErrorResponse(T response, String message) {
        response.setSuccess(false);
        response.setCode(HttpStatus.BAD_REQUEST.value());
        response.setMessage(message);
        return response;
    }
}
