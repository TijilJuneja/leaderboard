package com.craft.leaderboard.models.base;

import lombok.Getter;

import java.io.Serializable;

@Getter
public abstract class BaseResponse implements Serializable {
    private boolean success;
    private int code;
    private Object message;

    public BaseResponse()
    {
    }

    public BaseResponse(String message, int code, boolean success)
    {
        this.message = message;
        this.code = code;
        this.success = success;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
