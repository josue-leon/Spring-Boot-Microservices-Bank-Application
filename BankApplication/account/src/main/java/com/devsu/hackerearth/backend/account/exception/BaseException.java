package com.devsu.hackerearth.backend.account.exception;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseException extends RuntimeException {

    private final ErrorCode errorCode;
    private final Map<String, Object> details;

    protected BaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.details = new HashMap<>();
    }

    protected BaseException(ErrorCode errorCode, String customMessage) {
        super(customMessage);
        this.errorCode = errorCode;
        this.details = new HashMap<>();
    }

    protected BaseException(ErrorCode errorCode, String customMessage, Throwable cause) {
        super(customMessage, cause);
        this.errorCode = errorCode;
        this.details = new HashMap<>();
    }

    public BaseException addDetail(String key, Object value) {
        this.details.put(key, value);
        return this;
    }

    public Map<String, Object> getDetails() {
        return new HashMap<>(details);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public EnumCode getCode() {
        return errorCode.getCode();
    }

}
