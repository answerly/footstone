package com.olasharing.footstone.repository.enums;

/**
 * PropertiesStateEnum
 *
 * @author liuyan
 * @date 2019-02-19
 */
public enum PropertiesStateEnum {

    INIT(0, "未发布"),

    PUBLISH(1, "已发布"),

    OFFLINE(2, "已下线"),

    ;

    private final Integer value;

    private final String desc;

    PropertiesStateEnum(Integer value, String desc) {
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
