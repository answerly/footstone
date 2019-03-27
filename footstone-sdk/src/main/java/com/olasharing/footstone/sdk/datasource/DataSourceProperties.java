package com.olasharing.footstone.sdk.datasource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * DataSourceProperties
 *
 * @author liuyan
 * @date 2019-02-20
 */
@ConfigurationProperties(prefix = "footstone.datasource")
public class DataSourceProperties {

    private Boolean enabled;

    private String uri;

    @Value("${spring.application.name}")
    private String appName;

    private Integer readTimeout = 10000;

    private Integer connectTimeout = 10000;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Integer getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Integer readTimeout) {
        this.readTimeout = readTimeout;
    }

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }
}
