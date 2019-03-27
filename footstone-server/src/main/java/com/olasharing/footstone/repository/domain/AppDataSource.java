package com.olasharing.footstone.repository.domain;

import lombok.Data;

/**
 * 数据源
 *
 * @author liuyan
 * @date 2019-02-19
 */
@Data
public class AppDataSource {

    private Integer id;

    private String appName;

    private String appShowName;

    private String dataSourceId;

    private String url;

    private String username;

    private String password;

    private String driverClassName;

    /**
     * 更新时间
     */
    private java.util.Date gmtModified;

    /**
     * 环境
     */
    private String profile;

    /**
     * 是否默认数据源
     */
    private String defaultFlag;

}
