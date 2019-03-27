package com.olasharing.footstone.repository.enums;

/**
 * 环境枚举
 *
 * @author liuyan
 * @date 2019-02-19
 */
public enum ProfileEnum {

    DEV("dev", "开发环境", 0),

    TEST("test", "测试环境", 1),

    UAT("uat", "预发环境", 2),

    PROD("prod", "生产环境", 3),
    ;

    private final String value;

    private final String desc;

    private final Integer order;

    ProfileEnum(String value, String desc, Integer order) {
        this.value = value;
        this.desc = desc;
        this.order = order;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getOrder() {
        return order;
    }
}
