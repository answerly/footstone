package com.olasharing.footstone.server.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.olasharing.footstone.common.JsonUtils;
import com.olasharing.footstone.common.protocol.BizCodeEnum;
import com.olasharing.footstone.repository.domain.AdminDataExec;
import com.olasharing.footstone.repository.domain.AdminUser;
import com.olasharing.footstone.repository.domain.AppConfig;
import com.olasharing.footstone.repository.domain.AppDataSource;
import com.olasharing.footstone.repository.enums.DataExecResultEnum;
import com.olasharing.footstone.repository.enums.DataExecState;
import com.olasharing.footstone.repository.enums.DataExecType;
import com.olasharing.footstone.repository.mapper.AdminDataExecMapper;
import com.olasharing.footstone.server.dto.datasource.DataExecResultDTO;
import com.olasharing.footstone.server.service.AdminDataExecService;
import com.olasharing.footstone.server.service.AdminUserService;
import com.olasharing.footstone.server.service.AppConfigService;
import com.olasharing.footstone.server.service.AppDataSourceService;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * AdminDataExecServiceImpl
 *
 * @author GW00168835
 */
@Service
public class AdminDataExecServiceImpl implements AdminDataExecService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminDataExecServiceImpl.class);

    @Autowired
    private AdminDataExecMapper adminDataExecMapper;

    @Autowired
    private AppConfigService appConfigService;

    @Autowired
    private AppDataSourceService appDataSourceService;

    @Autowired
    private AdminUserService adminUserService;

    @Override
    public void insert(AdminDataExec adminDataExec) {
        String appName = adminDataExec.getAppName();
        String profile = adminDataExec.getProfile();
        String datasourceId = adminDataExec.getDatasourceId();

        DataExecResultDTO execResultData = new DataExecResultDTO();
        execResultData.setCode(DataExecResultEnum.UNDO.getCode());
        execResultData.setMessage(DataExecResultEnum.UNDO.getMessage());

        AppConfig appConfig = appConfigService.getByAppName(appName);
        appDataSourceService.selectByAppNameDatasourceId(appName, profile, datasourceId);

        AdminUser execAdminUser = adminUserService.getUser(appConfig.getUsername());
        AdminUser createAdminUser = adminUserService.getUser(adminDataExec.getCreateUsername());

        adminDataExec.setExecState(DataExecState.COMMIT.getValue());
        adminDataExec.setAppShowName(appConfig.getShowName());
        adminDataExec.setCreateDisplayName(createAdminUser.getDisplayName());
        adminDataExec.setExecUsername(execAdminUser.getUsername());
        adminDataExec.setExecDisplayName(execAdminUser.getDisplayName());
        adminDataExec.setGmtCreate(new Date());
        adminDataExec.setGmtModified(new Date());
        adminDataExec.setExecResult(JsonUtils.toJsonString(execResultData));
        adminDataExecMapper.insert(adminDataExec);
    }

    @Override
    public AdminDataExec getAdminDataExec(Integer id, String username) {
        AdminDataExec dataExec = adminDataExecMapper.selectOneByPrimaryKey(id);
        if (dataExec == null) {
            throw BizCodeEnum.DATA_NOT_EXIST.newBizException();
        }
        if (!Objects.equals(username, dataExec.getCreateUsername())
                && !Objects.equals(username, dataExec.getExecUsername())) {
            throw BizCodeEnum.DATA_NOT_EXIST.newBizException();
        }
        return dataExec;
    }

    @Override
    public List<AdminDataExec> getListByCondition(String username, Integer execState, boolean auditList) {
        AdminDataExec query = new AdminDataExec();
        query.setExecState(execState);
        if (auditList) {
            query.setExecUsername(username);
        }
        if (!auditList) {
            query.setCreateUsername(username);
        }
        return adminDataExecMapper.selectListBySelective(query);
    }

    @Override
    public void passDataExec(AdminDataExec adminDataExec) {
        Integer id = adminDataExec.getId();
        Integer oldState = DataExecState.COMMIT.getValue();
        Integer newState = DataExecState.PASS.getValue();
        int rows = adminDataExecMapper.updateStateByPrimaryKey(id, oldState, newState);
        LOGGER.info("auditDataExec:{}", rows);
    }

    @Override
    public void rejectDataExec(AdminDataExec adminDataExec) {
        Integer id = adminDataExec.getId();
        Integer oldState = DataExecState.COMMIT.getValue();
        Integer newState = DataExecState.CLOSE.getValue();
        int rows = adminDataExecMapper.updateStateByPrimaryKey(id, oldState, newState);
        LOGGER.info("rejectDataExec:{}", rows);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void executeDataExec(AdminDataExec adminDataExec) {
        Integer id = adminDataExec.getId();
        Integer oldState = DataExecState.PASS.getValue();
        Integer newState = DataExecState.EXECUTE.getValue();


        String appName = adminDataExec.getAppName();
        String profile = adminDataExec.getProfile();
        String datasourceId = adminDataExec.getDatasourceId();
        AppDataSource appDataSource = appDataSourceService.selectByAppNameDatasourceId(appName, profile, datasourceId);

        int rows = adminDataExecMapper.updateStateByPrimaryKey(id, oldState, newState);
        LOGGER.info("auditDataExec:{}", rows);
        if (rows == 1) {
            DataExecResultDTO execResultData = executeInDataSource(appDataSource, adminDataExec);
            String execResult = JsonUtils.toJsonString(execResultData);
            Integer newExecState =
                    DataExecResultEnum.FAIL.getCode().equals(execResultData.getCode()) ? oldState : newState;
            adminDataExecMapper.updateResultByPrimaryKey(id, execResult, newExecState);
        }
    }

    private DataExecResultDTO executeInDataSource(AppDataSource dataSourceConfig, AdminDataExec adminDataExec) {
        String driverClassName = dataSourceConfig.getDriverClassName();
        String url = dataSourceConfig.getUrl();
        String username = dataSourceConfig.getUsername();
        String password = dataSourceConfig.getPassword();
        try (Connection connection = new UnpooledDataSource(driverClassName, url, username, password).getConnection()) {
            PreparedStatement statement = connection.prepareStatement(adminDataExec.getExecScript());
            statement.setMaxRows(9999);
            if (DataExecType.UPDATE.getValue().equals(adminDataExec.getExecType())) {
                return executeUpdate(statement);
            }
            if (DataExecType.QUERY.getValue().equals(adminDataExec.getExecType())) {
                return executeQuery(statement);
            }
            return null;
        } catch (SQLException ex) {
            DataExecResultDTO execResultData = new DataExecResultDTO();
            execResultData.setCode(DataExecResultEnum.FAIL.getCode());
            execResultData.setMessage(DataExecResultEnum.FAIL.getMessage() + ex.getErrorCode());
            return execResultData;
        }
    }

    private DataExecResultDTO executeUpdate(PreparedStatement statement) throws SQLException {
        int rows = statement.executeUpdate();
        DataExecResultDTO execResultData = new DataExecResultDTO();
        execResultData.setCode(DataExecResultEnum.UPDATE_OK.getCode());
        execResultData.setMessage(DataExecResultEnum.UPDATE_OK.getMessage() + rows);
        return execResultData;
    }

    private DataExecResultDTO executeQuery(PreparedStatement statement) throws SQLException {
        List<String> keys = Lists.newArrayList();
        List<Map> dataList = Lists.newArrayList();
        ResultSetMetaData metaData = statement.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            keys.add(metaData.getColumnLabel(i));
        }
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            Map<String, String> data = Maps.newHashMap();
            for (int i = 0; i < columnCount; i++) {
                data.put(keys.get(i), rs.getString(i + 1));
            }
            dataList.add(data);
        }
        DataExecResultDTO execResultData = new DataExecResultDTO();
        execResultData.setCode(DataExecResultEnum.QUERY_OK.getCode());
        execResultData.setMessage(DataExecResultEnum.QUERY_OK.getMessage() + dataList.size());
        execResultData.setQueryHeaders(keys);
        execResultData.setQueryDataList(dataList);
        return execResultData;
    }
}
