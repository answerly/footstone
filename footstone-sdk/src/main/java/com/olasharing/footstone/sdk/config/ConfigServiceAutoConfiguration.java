package com.olasharing.footstone.sdk.config;

import com.olasharing.footstone.sdk.broadcast.BroadcastApplicationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * ConfigServicePropertySourceAutoConfiguration
 *
 * @author liuyan
 * @date 2019-02-21
 */
@Configuration
public class ConfigServiceAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigServiceRefreshConfiguration.class);

    @Configuration
    public class ConfigServiceRefreshConfiguration {

        private static final String EVENT = "configService";


        @Autowired
        ContextRefresher contextRefresher;

        @EventListener(BroadcastApplicationEvent.class)
        public void onApplicationEvent(BroadcastApplicationEvent event) {
            if (EVENT.equals(event.getEvent())) {
                contextRefresher.refresh();
                LOGGER.info("refresh config ok!");
            }
        }
    }
}
