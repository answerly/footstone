package com.olasharing.footstone.server.service.impl;

import com.google.common.collect.Lists;
import com.olasharing.footstone.common.CommonConstants;
import com.olasharing.footstone.common.protocol.BizCodeEnum;
import com.olasharing.footstone.deploy.git.GitLabApiService;
import com.olasharing.footstone.repository.domain.AdminTag;
import com.olasharing.footstone.repository.domain.AdminUser;
import com.olasharing.footstone.repository.domain.AppConfig;
import com.olasharing.footstone.repository.enums.AdminTagType;
import com.olasharing.footstone.repository.mapper.AppConfigMapper;
import com.olasharing.footstone.server.dto.app.AddMemberDTO;
import com.olasharing.footstone.server.dto.app.AppDetailDTO;
import com.olasharing.footstone.server.dto.user.UserDTO;
import com.olasharing.footstone.server.service.AdminTagService;
import com.olasharing.footstone.server.service.AdminUserService;
import com.olasharing.footstone.server.service.AppConfigService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * AppConfigServiceImpl
 *
 * @author liuyan
 * @date 2019-02-19
 */
@Service
public class AppConfigServiceImpl implements AppConfigService {

    @Autowired
    private AppConfigMapper appConfigMapper;

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private GitLabApiService gitLabApiService;

    @Autowired
    private AdminTagService adminTagService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(AppConfig appConfig) {
        AdminUser user = adminUserService.getUser(appConfig.getUsername());
        appConfig.setUsername(user.getUsername());
        appConfig.setDisplayName(user.getDisplayName());
        if (CommonConstants.COMMON_PROPS_PROJECT_NAME.equals(appConfig.getAppName())) {
            throw BizCodeEnum.APP_HAS_DUPLICATE.newBizException();
        }
        try {
            appConfigMapper.insert(appConfig);
        } catch (DuplicateKeyException ex) {
            throw BizCodeEnum.APP_HAS_DUPLICATE.newBizException(ex);
        }
        try {
            gitLabApiService.createProject(appConfig.getRepGroup(), appConfig.getRepName(), appConfig.getShowName());
        } catch (IOException ex) {
            throw BizCodeEnum.PROJECT_CREATE_ERROR.newBizException(ex);
        }
    }

    @Override
    public List<AppConfig> list(String appName) {
        return appConfigMapper.selectListLikeName(appName);
    }

    @Override
    public AppConfig getByAppName(String appName) {
        AppConfig appConfig = appConfigMapper.selectOneByName(appName);
        if (appConfig == null) {
            throw BizCodeEnum.APP_NOT_EXIST.newBizException();
        }
        return appConfig;
    }

    @Override
    public AppConfig getByUpperAppName(String appName) {
        appName = appName.toUpperCase();
        return appConfigMapper.selectOneByUpperName(appName);
    }

    @Override
    public void addMember(AddMemberDTO addMemberDTO) {
        String appName = addMemberDTO.getAppName();
        String username = addMemberDTO.getUsername();
        String displayName = addMemberDTO.getDisplayName();
        String tagType = AdminTagType.valueOf(addMemberDTO.getMemberTag()).name();
        AdminTag adminTag = adminTagService.getAdminTag(tagType, appName, username);
        if (adminTag == null) {
            adminTag = new AdminTag();
            adminTag.setTagType(tagType);
            adminTag.setTagRef(appName);
            adminTag.setTagCode(username);
            adminTag.setTagValue(displayName);
            adminTag.setGmtModified(new Date());
            adminTagService.addAdminTag(adminTag);
        }
    }

    @Override
    public AppDetailDTO getDetailByAppName(String appName) {
        AppDetailDTO appDetailDTO = new AppDetailDTO();

        AppConfig appConfig = getByAppName(appName);
        BeanUtils.copyProperties(appConfig, appDetailDTO);
        appDetailDTO.setMasterList(Lists.newArrayList());
        appDetailDTO.setDeveloperList(Lists.newArrayList());
        appDetailDTO.setTesterList(Lists.newArrayList());

        // OPS人员
        String tagType = AdminTagType.project_master.name();
        appendMember(tagType, appName, appDetailDTO.getMasterList());

        // 研发人员
        tagType = AdminTagType.project_developer.name();
        appendMember(tagType, appName, appDetailDTO.getDeveloperList());

        // 测试人员
        tagType = AdminTagType.project_tester.name();
        appendMember(tagType, appName, appDetailDTO.getTesterList());
        return appDetailDTO;
    }

    private void appendMember(String tagType, String appName, List<UserDTO> userDTOList) {
        List<AdminTag> adminTagList = adminTagService.getAdminTagList(tagType, appName);
        if (!CollectionUtils.isEmpty(adminTagList)) {
            for (AdminTag adminTag : adminTagList) {
                UserDTO userDTO = new UserDTO();
                userDTO.setUsername(adminTag.getTagCode());
                userDTO.setDisplayName(adminTag.getTagValue());
                userDTOList.add(userDTO);
            }
        }
    }
}
