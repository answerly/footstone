package com.olasharing.footstone.server.service;

import com.olasharing.footstone.repository.domain.AppDataSource;

import java.util.List;
import java.util.Set;

/**
 * AppDataSourceService
 *
 * @author liuyan
 * @date 2019-02-21
 */
public interface AppDataSourceService {

    /**
     * 新增
     *
     * @param appDataSource
     */
    void insert(AppDataSource appDataSource);


    /**
     * 编辑
     *
     * @param appDataSource
     * @return
     */
    AppDataSource edit(AppDataSource appDataSource);

    /**
     * 根据项目查询
     *
     * @param appName
     * @param profiles
     * @return
     */
    List<AppDataSource> selectByAppName(String appName, Set<String> profiles);


    /**
     * 根据项目查询
     *
     * @param appName
     * @param profile
     * @param datasourceId
     * @return
     */
    AppDataSource selectByAppNameDatasourceId(String appName, String profile, String datasourceId);
}
