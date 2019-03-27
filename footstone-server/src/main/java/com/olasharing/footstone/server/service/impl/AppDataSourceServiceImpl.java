package com.olasharing.footstone.server.service.impl;

import com.olasharing.footstone.common.protocol.BizCodeEnum;
import com.olasharing.footstone.repository.domain.AppConfig;
import com.olasharing.footstone.repository.domain.AppDataSource;
import com.olasharing.footstone.repository.mapper.AppDataSourceMapper;
import com.olasharing.footstone.server.service.AppConfigService;
import com.olasharing.footstone.server.service.AppDataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * AppDataSourceServiceImpl
 *
 * @author liuyan
 * @date 2019-02-21
 */
@Service
public class AppDataSourceServiceImpl implements AppDataSourceService {

    @Autowired
    private AppDataSourceMapper appDataSourceMapper;

    @Autowired
    private AppConfigService appConfigService;

    @Override
    public void insert(AppDataSource appDataSource) {
        AppConfig appConfig = appConfigService.getByAppName(appDataSource.getAppName());
        try {
            appDataSource.setAppShowName(appConfig.getShowName());
            appDataSource.setGmtModified(new Date());
            this.appDataSourceMapper.insert(appDataSource);
        } catch (DuplicateKeyException ex) {
            throw BizCodeEnum.DATASOURCE_HAS_DUPLICATE.newBizException(ex);
        }
    }

    @Override
    public AppDataSource edit(AppDataSource appDataSource) {
        int rows = this.appDataSourceMapper.updateByPrimaryKey(appDataSource);
        if (rows == 1) {
            return this.appDataSourceMapper.selectByPrimaryKey(appDataSource.getId());
        }
        return null;
    }

    @Override
    public List<AppDataSource> selectByAppName(String appName, Set<String> profiles) {
        return appDataSourceMapper.selectListByAppName(appName, profiles);
    }

    @Override
    public AppDataSource selectByAppNameDatasourceId(String appName, String profile, String datasourceId) {
        AppDataSource query = new AppDataSource();
        query.setAppName(appName);
        query.setProfile(profile);
        query.setDataSourceId(datasourceId);
        AppDataSource appDataSource = appDataSourceMapper.selectByUniqueKey(query);
        if (appDataSource == null) {
            throw BizCodeEnum.DATASOURCE_NOT_EXIST.newBizException();
        }
        return appDataSource;
    }
}
