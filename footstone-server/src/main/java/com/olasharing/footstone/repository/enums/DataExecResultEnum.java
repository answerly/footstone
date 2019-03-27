package com.olasharing.footstone.repository.enums;

/**
 * DataExecResultEnum
 *
 * @author GW00168835
 */
public enum DataExecResultEnum {

    UNDO(0, "未执行"),

    QUERY_OK(1, "查询成功: "),

    UPDATE_OK(2, "更新成功: "),

    FAIL(3, "执行失败: ");

    private final Integer code;

    private final String message;

    DataExecResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }}
