package com.olasharing.footstone.sdk.broadcast;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;

/**
 * BroadcastEventEndpointAutoConfiguration
 *
 * @author liuyan
 * @date 2019-02-22
 */
public class BroadcastEventEndpointAutoConfiguration {

    @Bean
    public BroadcastEventEndpoint BroadcastEventEndpoint(ApplicationEventPublisher context) {
        return new BroadcastEventEndpoint(context);
    }
}
