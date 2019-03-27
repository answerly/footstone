package com.olasharing.footstone.common.discovery.impl;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.converters.jackson.mixin.ApplicationsJsonMixIn;
import com.netflix.discovery.converters.jackson.mixin.InstanceInfoJsonMixIn;
import com.netflix.discovery.converters.jackson.serializer.InstanceInfoJsonBeanSerializer;
import com.netflix.discovery.shared.Applications;
import com.olasharing.footstone.common.discovery.DiscoveryProperties;
import com.olasharing.footstone.common.discovery.PassDiscoveryClient;
import com.olasharing.footstone.server.service.AppPropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 * @author GW00168835
 */
public abstract class AbstractPassDiscoveryClient implements PassDiscoveryClient {

    @Autowired
    private DiscoveryProperties discoveryProperties;

    @Autowired
    private AppPropertiesService propertiesService;

    private static MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE));

        SimpleModule jsonModule = new SimpleModule();
        jsonModule.setSerializerModifier(createJsonSerializerModifier());
        converter.getObjectMapper().registerModule(jsonModule);

        converter.getObjectMapper().configure(SerializationFeature.WRAP_ROOT_VALUE, true);
        converter.getObjectMapper().configure(DeserializationFeature.UNWRAP_ROOT_VALUE,
                true);
        converter.getObjectMapper().addMixIn(Applications.class, ApplicationsJsonMixIn.class);
        converter.getObjectMapper().addMixIn(InstanceInfo.class, InstanceInfoJsonMixIn.class);

        return converter;
    }

    private static BeanSerializerModifier createJsonSerializerModifier() {
        return new BeanSerializerModifier() {
            @Override
            public JsonSerializer<?> modifySerializer(SerializationConfig config,
                                                      BeanDescription beanDesc, JsonSerializer<?> serializer) {
                if (beanDesc.getBeanClass().isAssignableFrom(InstanceInfo.class)) {
                    return new InstanceInfoJsonBeanSerializer((BeanSerializerBase) serializer, false);
                }
                return serializer;
            }
        };
    }

    protected String getServiceUrl(String profile) {
        String discoveryKey = discoveryProperties.getKey();
        String url = propertiesService.getApplicationValue(profile, discoveryKey);
        if (!StringUtils.isEmpty(url)) {
            return url;
        }
        return null;
    }

    protected RestTemplate getRestTemplate() {
        SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(10000);
        clientHttpRequestFactory.setConnectTimeout(10000);
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
        restTemplate.getMessageConverters().add(0, mappingJacksonHttpMessageConverter());
        return restTemplate;
    }

}
