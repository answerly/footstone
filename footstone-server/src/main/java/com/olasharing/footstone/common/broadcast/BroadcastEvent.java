package com.olasharing.footstone.common.broadcast;

/**
 * BroadcastEvent
 *
 * @author liuyan
 * @date 2019-02-22
 */
public class BroadcastEvent {

    private String event;

    private String appName;

    private String profile;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
