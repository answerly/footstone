package com.olasharing.footstone.sdk.datasource;

import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * DataSourceConfig
 *
 * @author liuyan
 * @date 2019-02-21
 */
class DataSourceConfig {

    private String dataSourceId;

    private String url;

    private String username;

    private String password;

    private String driverClassName;

    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
