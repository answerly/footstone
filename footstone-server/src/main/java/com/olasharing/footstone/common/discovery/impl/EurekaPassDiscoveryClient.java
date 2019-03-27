package com.olasharing.footstone.common.discovery.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.transport.EurekaHttpResponse;
import com.olasharing.footstone.common.discovery.PassDiscoveryClient;
import com.olasharing.footstone.common.protocol.BizCodeEnum;
import com.olasharing.footstone.server.dto.app.ServiceInstanceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.eureka.http.RestTemplateEurekaHttpClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author GW00168835
 */
@Component
public class EurekaPassDiscoveryClient extends AbstractPassDiscoveryClient implements PassDiscoveryClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(EurekaPassDiscoveryClient.class);

    @Override
    public List<ServiceInstanceDTO> getInstances(String appName, String profile) {
        String serviceUrlConfig = getServiceUrl(profile);
        if (StringUtils.isEmpty(serviceUrlConfig)) {
            return Collections.emptyList();
        }
        Set<String> serviceUrls = StringUtils.commaDelimitedListToSet(serviceUrlConfig);
        RestTemplate restTemplate = getRestTemplate();
        Map<String, InstanceInfo> instanceInfoMap = Maps.newHashMap();
        for (String serviceUrl : serviceUrls) {
            Application application = getApplicationDefaultNull(restTemplate, serviceUrl, appName);
            if (application != null) {
                instanceInfoMap = appendInstances(instanceInfoMap, application);
            }
        }
        return convert(instanceInfoMap, profile);
    }

    private Application getApplicationDefaultNull(RestTemplate restTemplate, String serviceUrl, String appName) {
        try {
            return getApplication(restTemplate, serviceUrl, appName);
        } catch (HttpClientErrorException ex) {
            LOGGER.error("get application error: " + serviceUrl, ex);
            return null;
        }
    }

    private Application getApplication(RestTemplate restTemplate, String serviceUrl, String appName) {
        RestTemplateEurekaHttpClient httpClient = new RestTemplateEurekaHttpClient(restTemplate, serviceUrl);
        EurekaHttpResponse<Application> response = null;
        try {
            response = httpClient.getApplication(appName);
        } catch (HttpClientErrorException ex) {
            checkGetApplicationError(ex, appName, serviceUrl);
            return null;
        }
        if (response.getStatusCode() == HttpStatus.OK.value()) {
            return response.getEntity();
        }
        return null;
    }

    private void checkGetApplicationError(HttpClientErrorException ex, String appName, String serviceUrl) {
        int serverErrorCode = 500;
        int serverParamCode = 400;
        // 未发现服务
        if (ex.getStatusCode().value() >= serverParamCode
                && ex.getStatusCode().value() < serverErrorCode) {
            LOGGER.warn("not found application from eureka: appName={} serviceUrl={}", appName, serviceUrl);
            return;
        }
        // 服务发现报错
        if (ex.getStatusCode().value() >= serverErrorCode) {
            throw BizCodeEnum.DISCOVERY_ERROR.newBizException(ex);
        }
    }

    private Map<String, InstanceInfo> appendInstances(Map<String, InstanceInfo> instanceInfoMap, Application application) {
        List<InstanceInfo> instanceInfoList = application.getInstances();
        if (!CollectionUtils.isEmpty(instanceInfoList)) {
            for (InstanceInfo instanceInfo : instanceInfoList) {
                instanceInfoMap.put(instanceInfo.getInstanceId(), instanceInfo);
            }
        }
        return instanceInfoMap;
    }

    private List<ServiceInstanceDTO> convert(Map<String, InstanceInfo> instanceInfoMap, String profile) {
        List<ServiceInstanceDTO> instanceDTOList = Lists.newArrayList();
        for (InstanceInfo instanceInfo : instanceInfoMap.values()) {
            ServiceInstanceDTO instanceDTO = new ServiceInstanceDTO();
            instanceDTO.setPort(instanceInfo.getPort());
            instanceDTO.setHost(instanceInfo.getIPAddr());
            instanceDTO.setProfile(profile);
            instanceDTOList.add(instanceDTO);
        }
        return instanceDTOList;
    }

}
