package com.olasharing.footstone.manager.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.olasharing.footstone.common.CommonConstants;
import com.olasharing.footstone.common.JsonUtils;
import com.olasharing.footstone.common.broadcast.BroadcastEvent;
import com.olasharing.footstone.common.broadcast.PassBroadcaster;
import com.olasharing.footstone.common.protocol.BizCodeEnum;
import com.olasharing.footstone.manager.AbstractController;
import com.olasharing.footstone.manager.protocol.CommonResult;
import com.olasharing.footstone.repository.domain.AdminDataExec;
import com.olasharing.footstone.repository.domain.AppDataSource;
import com.olasharing.footstone.repository.enums.DataExecState;
import com.olasharing.footstone.server.dto.datasource.AdminDataExecDTO;
import com.olasharing.footstone.server.dto.datasource.AppDataSourceDTO;
import com.olasharing.footstone.server.dto.datasource.DataExecResultDTO;
import com.olasharing.footstone.server.service.AdminDataExecService;
import com.olasharing.footstone.server.service.AppDataSourceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * AppDataSourceController
 *
 * @author liuyan
 * @date 2019-02-21
 */
@RestController
@RequestMapping("datasource")
public class AppDataSourceController extends AbstractController {

    @Autowired
    private PassBroadcaster passBroadcaster;

    @Autowired
    private AppDataSourceService appDataSourceService;

    @Autowired
    private AdminDataExecService adminDataExecService;

    @PostMapping("add")
    public CommonResult add(@RequestBody AppDataSource appDataSource) {
        this.appDataSourceService.insert(appDataSource);
        sendToApp(appDataSource);
        return CommonResult.commonSuccess();
    }

    @PostMapping("edit")
    public CommonResult edit(@RequestBody AppDataSource appDataSource) {
        AppDataSource newDataSource = this.appDataSourceService.edit(appDataSource);
        sendToApp(newDataSource);
        return CommonResult.commonSuccess();
    }

    @GetMapping("list")
    public CommonResult<List<AppDataSourceDTO>> list(@RequestParam(value = "appName", required = false) String appName,
                                                     @RequestParam(value = "profile", required = false) String profile) {
        Set<String> profiles = Collections.emptySet();
        List<AppDataSourceDTO> dataSourceDTOList = Lists.newArrayList();
        if (!StringUtils.isEmpty(profile)) {
            profiles = Sets.newHashSet(profile);
        }
        List<AppDataSource> appDataSourceList = appDataSourceService.selectByAppName(appName, profiles);
        if (!CollectionUtils.isEmpty(appDataSourceList)) {
            for (AppDataSource appDataSource : appDataSourceList) {
                AppDataSourceDTO appDataSourceDTO = new AppDataSourceDTO();
                BeanUtils.copyProperties(appDataSource, appDataSourceDTO);
                dataSourceDTOList.add(appDataSourceDTO);
            }
        }
        return CommonResult.dataSuccess(dataSourceDTOList);
    }

    @PostMapping("/exec/commit")
    public CommonResult commitDataExec(@RequestBody AdminDataExec adminDataExec) {
        String username = getUsername();
        adminDataExec.setExecResult("");
        adminDataExec.setCreateUsername(username);
        adminDataExecService.insert(adminDataExec);
        return CommonResult.commonSuccess();
    }

    @GetMapping("/exec/get")
    public CommonResult<AdminDataExecDTO> getDataExec(@RequestParam("id") Integer id) {
        String username = getUsername();
        AdminDataExecDTO adminDataExecDTO = new AdminDataExecDTO();
        AdminDataExec adminDataExec = adminDataExecService.getAdminDataExec(id, username);
        BeanUtils.copyProperties(adminDataExec, adminDataExecDTO);
        adminDataExecDTO.setExecResultData(JsonUtils.parseObject(adminDataExec.getExecResult(), DataExecResultDTO.class));
        return CommonResult.dataSuccess(adminDataExecDTO);
    }

    @GetMapping("/exec/list")
    public CommonResult<List<AdminDataExec>> listDataExec(@RequestParam("audit") String audit,
                                                          @RequestParam(value = "execState", required = false) Integer execState) {
        String username = getUsername();
        Boolean auditList = "0".equals(audit);
        List<AdminDataExec> dataExecList = adminDataExecService.getListByCondition(username, execState, auditList);
        return CommonResult.dataSuccess(dataExecList);
    }

    @PostMapping("/exec/pass")
    public CommonResult passDataExec(@RequestBody AdminDataExec params) {
        Integer id = params.getId();
        String username = getUsername();
        AdminDataExec adminDataExec = adminDataExecService.getAdminDataExec(id, username);
        if (!adminDataExec.getExecUsername().equals(username)) {
            throw BizCodeEnum.DATA_NOT_EXIST.newBizException();
        }
        if (!adminDataExec.getExecState().equals(DataExecState.COMMIT.getValue())) {
            throw BizCodeEnum.DATA_NOT_EXIST.newBizException();
        }
        adminDataExecService.passDataExec(adminDataExec);
        return CommonResult.commonSuccess();
    }

    @PostMapping("/exec/reject")
    public CommonResult rejectDataExec(@RequestBody AdminDataExec params) {
        Integer id = params.getId();
        String username = getUsername();
        AdminDataExec adminDataExec = adminDataExecService.getAdminDataExec(id, username);
        if (!adminDataExec.getExecUsername().equals(username)) {
            throw BizCodeEnum.DATA_NOT_EXIST.newBizException();
        }
        if (!adminDataExec.getExecState().equals(DataExecState.COMMIT.getValue())) {
            throw BizCodeEnum.DATA_NOT_EXIST.newBizException();
        }
        adminDataExecService.rejectDataExec(adminDataExec);
        return CommonResult.commonSuccess();
    }

    @PostMapping("/exec/execute")
    public CommonResult executeDataExec(@RequestBody AdminDataExec params) {
        Integer id = params.getId();
        String username = getUsername();
        AdminDataExec adminDataExec = adminDataExecService.getAdminDataExec(id, username);
        if (!adminDataExec.getCreateUsername().equals(username)) {
            throw BizCodeEnum.DATA_NOT_EXIST.newBizException();
        }
        if (!adminDataExec.getExecState().equals(DataExecState.PASS.getValue())) {
            throw BizCodeEnum.DATA_NOT_EXIST.newBizException();
        }
        adminDataExecService.executeDataExec(adminDataExec);
        return CommonResult.commonSuccess();
    }

    private void sendToApp(AppDataSource dataSource) {
        BroadcastEvent event = new BroadcastEvent();
        event.setEvent(CommonConstants.DATASOURCE_CHANGE_EVENT);
        event.setAppName(dataSource.getAppName());
        event.setProfile(dataSource.getProfile());
        passBroadcaster.send(event);
    }
}
