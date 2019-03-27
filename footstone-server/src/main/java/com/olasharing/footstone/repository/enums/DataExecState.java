package com.olasharing.footstone.repository.enums;

/**
 * DataExecState
 *
 * @author GW00168835
 */
public enum DataExecState {

    COMMIT(0, "已提交"),

    PASS(1, "已审核"),

    EXECUTE(2, "已执行"),

    CLOSE(3, "已关闭");

    private final Integer value;

    private final String desc;

    DataExecState(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
