package com.olasharing.footstone.server.dto.datasource;

import lombok.Data;

/**
 * AppDataSourceDTO
 *
 * @author GW00168835
 */
@Data
public class AppDataSourceDTO {

    private Integer id;

    private String appName;

    private String appShowName;

    private String dataSourceId;

    private String url;

    private String username;

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
