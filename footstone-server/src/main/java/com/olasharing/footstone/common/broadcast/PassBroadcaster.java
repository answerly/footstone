package com.olasharing.footstone.common.broadcast;

import com.google.common.collect.Lists;
import com.olasharing.footstone.common.discovery.PassDiscoveryClient;
import com.olasharing.footstone.server.dto.app.ServiceInstanceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

/**
 * EurekaBroadcaster
 *
 * @author liuyan
 * @date 2019-02-22
 */
@Component
public class PassBroadcaster implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(PassBroadcaster.class);

    private RestTemplate restTemplate;

    @Autowired
    private PassDiscoveryClient discoveryClient;

    @Override
    public void afterPropertiesSet() {
        SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(10000);
        clientHttpRequestFactory.setConnectTimeout(10000);
        this.restTemplate = new RestTemplate(clientHttpRequestFactory);
    }

    /**
     * @param event
     * @return fail count
     */
    public int send(BroadcastEvent event) {
        int failCount = 0;
        List<String> broadcastUrls = getBroadcastUrls(event);
        for (String broadcastUrl : broadcastUrls) {
            boolean result = false;
            try {
                result = send0(broadcastUrl);
                LOGGER.info("send[{}] result:{}", broadcastUrl, result);
            } catch (Exception ex) {
                LOGGER.error("send fail:" + broadcastUrl, ex);
            }
            if (!result) {
                failCount++;
            }
        }
        LOGGER.info("send BroadcastEvent fail:{}", failCount);
        return failCount;
    }

    private List<String> getBroadcastUrls(BroadcastEvent event) {
        String appName = event.getAppName();
        String profile = event.getProfile();
        List<ServiceInstanceDTO> serviceInstances = discoveryClient.getInstances(appName, profile);
        if (CollectionUtils.isEmpty(serviceInstances)) {
            return Collections.emptyList();
        }
        List<String> broadcastUrlList = Lists.newArrayList();
        for (ServiceInstanceDTO instanceDTO : serviceInstances) {
            StringBuilder broadcastUrlSb = new StringBuilder();
            broadcastUrlSb.append("http://").append(instanceDTO.getHost()).append(":").append(instanceDTO.getPort());
            broadcastUrlSb.append("/actuator/broadcast/").append(event.getEvent());
            broadcastUrlList.add(broadcastUrlSb.toString());
        }
        return broadcastUrlList;
    }

    private boolean send0(String broadcastUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/vnd.spring-boot.actuator.v2+json;charset=UTF-8");
        HttpEntity<Void> entity = new HttpEntity<>((Void) null, headers);
        ResponseEntity<Boolean> response = restTemplate.exchange(broadcastUrl, HttpMethod.POST, entity, Boolean.TYPE);
        return response != null && response.getStatusCode() == HttpStatus.OK;
    }

}
