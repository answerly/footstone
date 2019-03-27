package com.olasharing.footstone.repository.enums;

/**
 * DataExecType
 *
 * @author GW00168835
 */
public enum DataExecType {

    QUERY("query", "查询"),

    UPDATE("update", "更新");

    private final String value;

    private final String desc;

    DataExecType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
