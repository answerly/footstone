package com.olasharing.footstone.server.service;

import com.olasharing.footstone.repository.domain.AppConfig;
import com.olasharing.footstone.server.dto.app.AddMemberDTO;
import com.olasharing.footstone.server.dto.app.AppDetailDTO;

import java.util.List;

/**
 * AppConfigService
 *
 * @author liuyan
 * @date 2019-02-19
 */
public interface AppConfigService {

    /**
     * 新增
     *
     * @param appConfig
     */
    void insert(AppConfig appConfig);

    /**
     * 详情
     *
     * @param appName
     * @return
     */
    List<AppConfig> list(String appName);

    /**
     * 详情
     *
     * @param appName
     * @return
     */
    AppConfig getByAppName(String appName);

    /**
     * 详情
     *
     * @param appName
     * @return
     */
    AppConfig getByUpperAppName(String appName);

    /**
     * 添加开发人
     *
     * @param addMemberDTO
     */
    void addMember(AddMemberDTO addMemberDTO);


    /**
     * 详情
     *
     * @param appName
     * @return
     */
    AppDetailDTO getDetailByAppName(String appName);
}
