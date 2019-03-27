package com.olasharing.footstone.common.protocol;

/**
 * @author GW00168835
 */
public class BizException extends RuntimeException {

    private String code;

    private String message;

    private Throwable cause;

    public BizException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
        this.cause = cause;
    }

    public BizException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
        this.cause = cause;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }
}
