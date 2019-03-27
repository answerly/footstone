package com.olasharing.footstone.repository.domain;

import lombok.Data;

/**
 * 系统配置
 *
 * @author liuyan
 * @date 2019-02-18
 */
@Data
public class AppConfig {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 项目名称(唯一索引)
     */
    private String appName;

    /**
     * 显示名称
     */
    private String showName;

    /**
     * 应用类型
     */
    private Integer appType;

    /**
     * owner
     */
    private String username;

    /**
     * owner显示名称
     */
    private String displayName;

    /**
     * 代码仓库组
     */
    private String repGroup;

    /**
     * 代码仓库名称
     */
    private String repName;

    /**
     * 修改时间
     */
    private java.util.Date gmtModified;
}
