package com.olasharing.footstone.manager.protocol;

import com.olasharing.footstone.common.protocol.BizCodeEnum;

/**
 * @author GW00168835
 */
public class CommonResult<T> {

    private String code;
    private String message;
    private T data;

    public static CommonResult commonSuccess() {
        return result(BizCodeEnum.SUCCESS.getCode(), BizCodeEnum.SUCCESS.getMessage(), null);
    }

    public static <T> CommonResult<T> dataSuccess(T data) {
        return result(BizCodeEnum.SUCCESS.getCode(), BizCodeEnum.SUCCESS.getMessage(), data);
    }

    public static <T> CommonResult<T> result(String code, String message, T data) {
        CommonResult commonResult = new CommonResult();
        commonResult.setCode(code);
        commonResult.setMessage(message);
        commonResult.setData(data);
        return commonResult;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
