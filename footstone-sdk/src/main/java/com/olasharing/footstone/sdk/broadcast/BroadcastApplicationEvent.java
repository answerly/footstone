package com.olasharing.footstone.sdk.broadcast;

import org.springframework.context.ApplicationEvent;

/**
 * BroadcastApplicationEvent
 *
 * @author liuyan
 * @date 2019-02-22
 */
public class BroadcastApplicationEvent extends ApplicationEvent {

    private final String event;

    public BroadcastApplicationEvent(String event) {
        super(event);
        this.event = event;
    }

    public String getEvent() {
        return event;
    }
}
