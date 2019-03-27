package com.olasharing.footstone.server.service;

import com.olasharing.footstone.repository.domain.AppProperties;

import java.util.List;

/**
 * AppPropertiesService
 *
 * @author liuyan
 * @date 2019-02-19
 */
public interface AppPropertiesService {

    /**
     * 新建
     *
     * @param appProperties
     */
    void insert(AppProperties appProperties);

    /**
     * 修改配置
     *
     * @param appProperties
     * @return
     */
    void updateEditValueById(AppProperties appProperties);


    /**
     * 发布配置
     *
     * @param propsToUpdate
     * @return appName
     */
    AppProperties publish(AppProperties propsToUpdate);

    /**
     * 删除配置
     *
     * @param propsToUpdate
     * @return
     */
    AppProperties offline(AppProperties propsToUpdate);

    /**
     * 项目配置
     *
     * @param appName
     * @param profile
     * @return
     */
    List<AppProperties> listByAppName(String appName, String profile);


    /**
     * 新建
     *
     * @param appProperties
     */
    void insertApplicationProps(AppProperties appProperties);

    /**
     * getApplicationValue
     *
     * @param profile
     * @param key
     * @return
     */
    String getApplicationValue(String profile, String key);
}
