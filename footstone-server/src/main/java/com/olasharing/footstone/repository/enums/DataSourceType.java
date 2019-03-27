package com.olasharing.footstone.repository.enums;

/**
 * 数据源类型
 *
 * @author liuyan
 * @date 2019-02-21
 */
public enum DataSourceType {

    MYSQL("MYSQL"),

    REDIS("REDIS"),
    ;

    private final String value;

    DataSourceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
