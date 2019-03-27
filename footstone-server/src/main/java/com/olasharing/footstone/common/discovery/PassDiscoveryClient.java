package com.olasharing.footstone.common.discovery;

import com.olasharing.footstone.server.dto.app.ServiceInstanceDTO;

import java.util.List;

/**
 * @author GW00168835
 */
public interface PassDiscoveryClient {

    /**
     * 获取应用实例
     *
     * @param appName
     * @param profile
     * @return
     */
    List<ServiceInstanceDTO> getInstances(String appName, String profile);
}
