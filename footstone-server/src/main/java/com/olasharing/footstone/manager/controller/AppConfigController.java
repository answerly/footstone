package com.olasharing.footstone.manager.controller;

import com.google.common.collect.Lists;
import com.olasharing.footstone.common.discovery.PassDiscoveryClient;
import com.olasharing.footstone.common.protocol.BizCodeEnum;
import com.olasharing.footstone.manager.AbstractController;
import com.olasharing.footstone.manager.protocol.CommonResult;
import com.olasharing.footstone.repository.domain.AdminUser;
import com.olasharing.footstone.repository.domain.AppConfig;
import com.olasharing.footstone.repository.enums.ProfileEnum;
import com.olasharing.footstone.server.dto.app.AddMemberDTO;
import com.olasharing.footstone.server.dto.app.AppDetailDTO;
import com.olasharing.footstone.server.dto.app.ServiceInstanceDTO;
import com.olasharing.footstone.server.service.AdminUserService;
import com.olasharing.footstone.server.service.AppConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 项目管理
 *
 * @author liuyan
 * @date 2019-02-18
 */
@RestController
@RequestMapping("app")
public class AppConfigController extends AbstractController {

    @Autowired
    private PassDiscoveryClient discoveryClient;

    @Autowired
    private AppConfigService appConfigService;

    @Autowired
    private AdminUserService adminUserService;

    @PostMapping("add")
    public CommonResult add(@RequestBody AppConfig appConfig) {
        appConfig.setId(null);
        appConfig.setGmtModified(new Date());
        appConfigService.insert(appConfig);
        return CommonResult.commonSuccess();
    }

    @GetMapping("get")
    public CommonResult<AppDetailDTO> getByAppName(@RequestParam("appName") String appName) {
        AppDetailDTO appDetailDTO = appConfigService.getDetailByAppName(appName);
        return CommonResult.dataSuccess(appDetailDTO);
    }

    @GetMapping("list")
    public CommonResult<List<AppConfig>> list(@RequestParam(value = "appName", required = false) String appName) {
        List<AppConfig> appConfigList = appConfigService.list(appName);
        return CommonResult.dataSuccess(appConfigList);
    }

    @GetMapping("instances")
    public CommonResult<List<ServiceInstanceDTO>> instances(@RequestParam("appName") String appName) {
        AppConfig appConfig = appConfigService.getByAppName(appName);
        if (appConfig == null) {
            return CommonResult.dataSuccess(Collections.emptyList());
        }
        List<ServiceInstanceDTO> instanceDTOList = Lists.newArrayList();
        for (ProfileEnum profileEnum : ProfileEnum.values()) {
            List<ServiceInstanceDTO> tmpInstanceList = discoveryClient.getInstances(appName, profileEnum.getValue());
            if (!CollectionUtils.isEmpty(tmpInstanceList)) {
                instanceDTOList.addAll(tmpInstanceList);
            }
        }
        return CommonResult.dataSuccess(instanceDTOList);
    }


    @PostMapping("/member/add")
    public CommonResult addDeveloper(@RequestBody AddMemberDTO addMemberDTO) {
        String username = getUsername();
        String appName = addMemberDTO.getAppName();
        String developerName = addMemberDTO.getUsername();
        AppConfig appConfig = appConfigService.getByAppName(appName);
        if (!Objects.equals(username, appConfig.getUsername())) {
            throw BizCodeEnum.AUTHORIZE_ERROR.newBizException();
        }
        AdminUser adminUser = adminUserService.getUser(developerName);
        addMemberDTO.setDisplayName(adminUser.getDisplayName());
        appConfigService.addMember(addMemberDTO);
        return CommonResult.commonSuccess();
    }

}
