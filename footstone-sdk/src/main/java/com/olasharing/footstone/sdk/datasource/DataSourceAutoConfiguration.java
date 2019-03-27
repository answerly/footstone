package com.olasharing.footstone.sdk.datasource;

import com.google.common.collect.Maps;
import com.olasharing.footstone.sdk.broadcast.BroadcastApplicationEvent;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.util.Map;

/**
 * DataSourceAutoConfiguration
 *
 * @author liuyan
 * @date 2019-02-20
 */
@ConditionalOnClass(HikariDataSource.class)
@EnableConfigurationProperties(DataSourceProperties.class)
public class DataSourceAutoConfiguration {

    private static final Class<? extends DataSource> DATA_SOURCE_TYPE = HikariDataSource.class;

    @Bean
    @ConditionalOnProperty(value = "footstone.datasource.enabled", matchIfMissing = true)
    public DynamicDataSourceFactory dynamicDataSourceFactory(Environment environment,
                                                             DataSourceProperties dataSourceProperties) {
        DynamicDataSourceFactory dataSourceFactory
                = new DynamicDataSourceFactory(environment, dataSourceProperties);
        dataSourceFactory.loadDataSources();
        return dataSourceFactory;
    }

    @Bean
    @ConditionalOnBean(DynamicDataSourceFactory.class)
    public DataSource mutableDataSourceRoute(DynamicDataSourceFactory dynamicDataSourceFactory) {
        DataSource defaultDataSource = dynamicDataSourceFactory.getDefaultDataSource();
        Map<String, DataSource> dataSources = dynamicDataSourceFactory.getDataSources();
        MutableDataSourceRoute dataSourceRoute = new MutableDataSourceRoute();
        dataSourceRoute.setDefaultTargetDataSource(defaultDataSource);
        dataSourceRoute.setTargetDataSources(Maps.newHashMap(dataSources));
        return dataSourceRoute;
    }

    @Configuration
    @ConditionalOnBean(DynamicDataSourceFactory.class)
    public class DataSourceRefreshConfiguration {

        private static final String EVENT = "datasource";
        @Autowired
        DynamicDataSourceFactory dynamicDataSourceFactory;

        @EventListener(BroadcastApplicationEvent.class)
        public void onApplicationEvent(BroadcastApplicationEvent event) {
            if (EVENT.equals(event.getEvent())) {
                dynamicDataSourceFactory.refresh();
            }
        }
    }

    class MutableDataSourceRoute extends AbstractRoutingDataSource {

        @Override
        protected Object determineCurrentLookupKey() {
            return TransactionSynchronizationManager.getCurrentTransactionName();
        }
    }


}
