package com.olasharing.footstone.repository.enums;

/**
 * 系统类型
 *
 * @author liuyan
 * @date 2019-02-20
 */
public enum AppType {

    JAVA(0),

    NODE(1),
    ;

    private final Integer value;

    AppType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
