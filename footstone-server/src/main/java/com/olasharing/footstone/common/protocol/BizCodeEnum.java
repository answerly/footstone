package com.olasharing.footstone.common.protocol;

/**
 * @author GW00168835
 */
public enum BizCodeEnum {

    SUCCESS("操作成功"),

    SYSTEM_ERROR("系统异常"),

    LOGIN_TIMEOUT("登陆超时"),

    USER_NOT_EXIST("用户不存在"),

    USERNAME_OR_PWD_ERROR("用户名密码错误"),

    AUTHORIZE_ERROR("权限不足"),

    DISCOVERY_ERROR("服务发现异常"),

    BRANCH_CREATE_ERROR("分支创建失败"),

    ITERATION_NOT_EXIST("迭代不存在"),

    PROJECT_CREATE_ERROR("仓库创建异常"),

    APP_HAS_DUPLICATE("应用已存在"),

    APP_NOT_EXIST("应用不存在"),

    DATASOURCE_HAS_DUPLICATE("数据源已存在"),

    PROPERTIES_HAS_DUPLICATE("配置已存在"),

    PROPERTIES_NOT_EXIST("配置不存在"),

    PROPERTIES_HAS_OFFLINE("配置已下线"),

    PROPERTIES_HAS_CHANGE("配置发生变更"),

    DATASOURCE_NOT_EXIST("数据源不存在"),

    DATA_NOT_EXIST("数据不存在"),

    DATA_EXEC_ERROR("数据执行错误"),
    ;

    private String message;

    BizCodeEnum(String message) {
        this.message = message;
    }

    public static String getMessage(String code) {
        for (BizCodeEnum bizCodeEnum : BizCodeEnum.values()) {
            if (bizCodeEnum.getCode().equals(code)) {
                return bizCodeEnum.getMessage();
            }
        }
        return null;
    }

    public String getCode() {
        return name();
    }

    public String getMessage() {
        return message;
    }

    public BizException newBizException() {
        return new BizException(getCode(), getMessage());
    }

    public BizException newBizException(Throwable cause) {
        return new BizException(getCode(), getMessage(), cause);
    }

}
