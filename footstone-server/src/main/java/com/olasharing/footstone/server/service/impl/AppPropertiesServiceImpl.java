package com.olasharing.footstone.server.service.impl;

import com.olasharing.footstone.common.CommonConstants;
import com.olasharing.footstone.common.protocol.BizCodeEnum;
import com.olasharing.footstone.repository.domain.AppConfig;
import com.olasharing.footstone.repository.domain.AppProperties;
import com.olasharing.footstone.repository.enums.PropertiesStateEnum;
import com.olasharing.footstone.repository.mapper.AppPropertiesMapper;
import com.olasharing.footstone.server.service.AppConfigService;
import com.olasharing.footstone.server.service.AppPropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * AppPropertiesServiceImpl
 *
 * @author liuyan
 * @date 2019-02-19
 */
@Service
public class AppPropertiesServiceImpl implements AppPropertiesService {

    @Autowired
    private AppPropertiesMapper appPropertiesMapper;

    @Autowired
    private AppConfigService appConfigService;

    @Override
    public void insert(AppProperties appProperties) {
        String appName = appProperties.getAppName();
        AppConfig appConfig = appConfigService.getByAppName(appName);
        appProperties.setValue("");
        appProperties.setState(PropertiesStateEnum.INIT.getValue());
        appProperties.setLabel("master");
        appProperties.setAppShowName(appConfig.getShowName());
        try {
            appPropertiesMapper.insert(appProperties);
        } catch (DuplicateKeyException ex) {
            throw BizCodeEnum.PROPERTIES_HAS_DUPLICATE.newBizException(ex);
        }
    }

    @Override
    public void updateEditValueById(AppProperties appProperties) {
        Long id = appProperties.getId();
        AppProperties checkProps = appPropertiesMapper.selectByPrimaryKey(id);
        if (checkProps == null) {
            throw BizCodeEnum.PROPERTIES_NOT_EXIST.newBizException();
        }
        if (PropertiesStateEnum.OFFLINE.getValue().equals(checkProps.getState())) {
            throw BizCodeEnum.PROPERTIES_HAS_OFFLINE.newBizException();
        }
        if (CommonConstants.COMMON_PROPS_PROJECT_NAME.equals(appProperties.getAppName())) {
            throw BizCodeEnum.PROPERTIES_NOT_EXIST.newBizException();
        }
        AppProperties ppToUpdate = new AppProperties();
        ppToUpdate.setId(appProperties.getId());
        ppToUpdate.setEditValue(appProperties.getEditValue());
        appPropertiesMapper.updateByPrimaryKey(ppToUpdate);
    }

    @Override
    public AppProperties publish(AppProperties propsToUpdate) {
        Long id = propsToUpdate.getId();
        String appName = propsToUpdate.getAppName();
        AppProperties properties = appPropertiesMapper.selectByPrimaryKey(id);
        if (!Objects.equals(appName, properties.getAppName())) {
            throw BizCodeEnum.PROPERTIES_NOT_EXIST.newBizException();
        }
        if (PropertiesStateEnum.OFFLINE.getValue().equals(properties.getState())) {
            throw BizCodeEnum.PROPERTIES_HAS_OFFLINE.newBizException();
        }
        if (CommonConstants.COMMON_PROPS_PROJECT_NAME.equals(properties.getAppName())) {
            throw BizCodeEnum.PROPERTIES_NOT_EXIST.newBizException();
        }
        int rows = appPropertiesMapper.updatePublishByPrimaryKey(id,
                PropertiesStateEnum.PUBLISH.getValue(),
                PropertiesStateEnum.OFFLINE.getValue());
        if (rows != 1) {
            throw new IllegalArgumentException("配置已下线:" + properties.getKey());
        }
        return properties;
    }

    @Override
    public AppProperties offline(AppProperties propsToUpdate) {
        Long id = propsToUpdate.getId();
        String appName = propsToUpdate.getAppName();
        AppProperties properties = appPropertiesMapper.selectByPrimaryKey(id);
        if (!Objects.equals(appName, properties.getAppName())) {
            throw BizCodeEnum.PROPERTIES_NOT_EXIST.newBizException();
        }
        if (PropertiesStateEnum.OFFLINE.getValue().equals(properties.getState())) {
            throw BizCodeEnum.PROPERTIES_HAS_OFFLINE.newBizException();
        }
        if (CommonConstants.COMMON_PROPS_PROJECT_NAME.equals(properties.getAppName())) {
            throw BizCodeEnum.PROPERTIES_NOT_EXIST.newBizException();
        }
        int rows = appPropertiesMapper.updateOfflineByPrimaryKey(id,
                PropertiesStateEnum.OFFLINE.getValue());
        if (rows != 1) {
            throw BizCodeEnum.PROPERTIES_HAS_CHANGE.newBizException();
        }
        return properties;
    }

    @Override
    public List<AppProperties> listByAppName(String appName, String profile) {
        return appPropertiesMapper.selectListByAppName(appName, profile);
    }

    @Override
    public void insertApplicationProps(AppProperties appProperties) {
        appProperties.setLabel("master");
        appProperties.setEditValue(appProperties.getValue());
        appProperties.setAppName(CommonConstants.COMMON_PROPS_PROJECT_NAME);
        appProperties.setState(PropertiesStateEnum.PUBLISH.getValue());
        try {
            appPropertiesMapper.insert(appProperties);
        } catch (DuplicateKeyException ex) {
            throw BizCodeEnum.PROPERTIES_HAS_DUPLICATE.newBizException(ex);
        }
    }

    @Override
    public String getApplicationValue(String profile, String key) {
        String appName = CommonConstants.COMMON_PROPS_PROJECT_NAME;
        List<AppProperties> propertiesList = listByAppName(appName, profile);
        for (AppProperties properties : propertiesList) {
            if (Objects.equals(properties.getKey(), key)) {
                return properties.getValue();
            }
        }
        return null;
    }
}
