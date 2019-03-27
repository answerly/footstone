package com.olasharing.footstone.common.discovery;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author GW00168835
 */
@Component
@ConfigurationProperties(prefix = "pass.discovery")
public class DiscoveryProperties {

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
