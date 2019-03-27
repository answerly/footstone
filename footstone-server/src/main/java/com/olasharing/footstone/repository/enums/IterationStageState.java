package com.olasharing.footstone.repository.enums;

/**
 * 迭代阶段
 *
 * @author liuyan
 * @date 2019-02-19
 */
public enum IterationStageState {

    DEV(0, "开发"),
    TEST(1, "测试"),
    STAGE(2, "预发"),
    PROD(3, "生产");


    private Integer stage;

    private String desc;

    IterationStageState(Integer stage, String desc) {
        this.stage = stage;
        this.desc = desc;
    }

    public Integer getStage() {
        return stage;
    }

    public String getDesc() {
        return desc;
    }
}
