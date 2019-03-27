package com.olasharing.footstone.sdk.broadcast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.context.ApplicationEventPublisher;

/**
 * BroadcastEventEndpoint
 *
 * @author liuyan
 * @date 2019-02-22
 */
@Endpoint(id = "broadcast")
public class BroadcastEventEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(BroadcastEventEndpoint.class);

    private ApplicationEventPublisher context;

    public BroadcastEventEndpoint(ApplicationEventPublisher context) {
        this.context = context;
    }

    @WriteOperation
    public boolean receive(@Selector String event) {
        LOGGER.info("receive broadcast:{}", event);
        context.publishEvent(new BroadcastApplicationEvent(event));
        return true;
    }
}
