package com.olasharing.footstone.sdk.datasource;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * DynamicDataSourceFactory
 *
 * @author liuyan
 * @date 2019-02-21
 */
class DynamicDataSourceFactory implements DisposableBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicDataSourceFactory.class);

    private Environment environment;
    private DataSourceProperties dataSourceProperties;

    private RestTemplate restTemplate;
    private DynamicDataSource defaultDataSource;
    private Map<String, DynamicDataSource> dataSources;

    public DynamicDataSourceFactory(Environment environment,
                                    DataSourceProperties dataSourceProperties) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(dataSourceProperties.getReadTimeout());
        requestFactory.setConnectTimeout(dataSourceProperties.getConnectTimeout());

        this.restTemplate = new RestTemplate(requestFactory);
        this.environment = environment;
        this.dataSourceProperties = dataSourceProperties;
    }

    public DynamicDataSource getDefaultDataSource() {
        return defaultDataSource;
    }

    public Map<String, DataSource> getDataSources() {
        return Collections.unmodifiableMap(dataSources);
    }

    @Override
    public void destroy() {
        if (!CollectionUtils.isEmpty(dataSources)) {
            for (DynamicDataSource dataSource : dataSources.values()) {
                try {
                    dataSource.close();
                } catch (IOException e) {
                    LOGGER.error("close datasource fail:" + dataSource.getDataSourceId(), e);
                }
            }
        }
    }

    public void loadDataSources() {
        List<DataSourceConfig> dataSourceConfigs = getRemoteDataSourceConfigList();
        if (CollectionUtils.isEmpty(dataSourceConfigs)) {
            LOGGER.warn("find datasource from pass is empty");
            this.dataSources = Collections.unmodifiableMap(Collections.emptyMap());
            return;
        }
        DynamicDataSource tmpDefaultDataSource = null;
        Map<String, DynamicDataSource> tmpDataSources = Maps.newHashMap();
        for (DataSourceConfig dataSourceConfig : dataSourceConfigs) {
            String dataSourceId = dataSourceConfig.getDataSourceId();
            try {
                DynamicDataSource dataSource = DynamicDataSource.build(dataSourceConfig);
                tmpDataSources.put(dataSourceId, dataSource);
                if (tmpDefaultDataSource == null) {
                    tmpDefaultDataSource = dataSource;
                }
            } catch (Exception ex) {
                LOGGER.error("build datasource fail:" + dataSourceId, ex);
                throw new RuntimeException(ex);
            }
        }
        this.defaultDataSource = tmpDefaultDataSource;
        this.dataSources = Collections.unmodifiableMap(tmpDataSources);
    }

    public void refresh() {
        List<DataSourceConfig> dataSourceConfigs = getRemoteDataSourceConfigList();
        if (CollectionUtils.isEmpty(dataSourceConfigs)) {
            throw new IllegalArgumentException("'footstone.datasource.enabled' is true, but pass not exist config");
        }
        for (DataSourceConfig dataSourceConfig : dataSourceConfigs) {
            String dataSourceId = dataSourceConfig.getDataSourceId();
            DynamicDataSource dynamicDataSource = dataSources.get(dataSourceId);
            if (dynamicDataSource != null) {
                dynamicDataSource.updateDataSourceConfig(dataSourceConfig);
            }
        }
    }

    private List<DataSourceConfig> getRemoteDataSourceConfigList() {
        String[] profiles = this.environment.getActiveProfiles();
        if (profiles == null || profiles.length == 0) {
            profiles = this.environment.getDefaultProfiles();
        }
        if (profiles == null || profiles.length == 0) {
            throw new IllegalArgumentException("profiles is empty");
        }
        String profile = StringUtils.arrayToCommaDelimitedString(profiles);

        String uri = this.dataSourceProperties.getUri();
        String appName = this.dataSourceProperties.getAppName();

        String homePage = getHomePage(uri, appName, profile);
        HttpHeaders headers = new HttpHeaders();
        final HttpEntity<Void> entity = new HttpEntity<>((Void) null, headers);
        ResponseEntity<DataSourceConfigGroup> response = this.restTemplate.exchange(
                homePage, HttpMethod.GET, entity, DataSourceConfigGroup.class);

        if (response == null || response.getStatusCode() != HttpStatus.OK) {
            return null;
        }
        return response.getBody().getDataSourceConfigList();
    }

    private String getHomePage(String uri, String appName, String profile) {
        return uri + "/sdk/datasources?appName=" + appName + "&profile=" + profile;
    }
}
