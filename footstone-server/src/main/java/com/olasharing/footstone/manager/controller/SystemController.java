package com.olasharing.footstone.manager.controller;

import com.google.common.collect.Lists;
import com.olasharing.footstone.deploy.git.GitLabApiService;
import com.olasharing.footstone.deploy.git.GitLabProperties;
import com.olasharing.footstone.manager.AbstractController;
import com.olasharing.footstone.manager.annotation.Authorize;
import com.olasharing.footstone.manager.protocol.CommonResult;
import com.olasharing.footstone.repository.enums.*;
import com.olasharing.footstone.server.dto.EntryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * system
 *
 * @author GW00168835
 */
@RestController
@RequestMapping("system")
public class SystemController extends AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemController.class);
    @Autowired
    private GitLabProperties gitLabProperties;

    @Autowired
    private GitLabApiService gitLabApiService;

    @Authorize
    @GetMapping("profiles")
    public CommonResult<List<EntryDTO<String, String>>> profileList() {
        List<EntryDTO<String, String>> entryDTOList = Lists.newArrayList();
        List<ProfileEnum> profileList = Arrays.asList(ProfileEnum.values());
        profileList.sort(Comparator.comparing(ProfileEnum::getOrder));
        for (ProfileEnum profileEnum : profileList) {
            entryDTOList.add(new EntryDTO<>(profileEnum.getValue(), profileEnum.getDesc()));
        }
        return CommonResult.dataSuccess(entryDTOList);
    }

    @Authorize
    @GetMapping("appTypes")
    public CommonResult<List<EntryDTO<Integer, String>>> appTypeList() {
        List<EntryDTO<Integer, String>> entryDTOList = Lists.newArrayList();
        List<AppType> appTypeList = Arrays.asList(AppType.values());
        for (AppType appType : appTypeList) {
            entryDTOList.add(new EntryDTO<>(appType.getValue(), appType.name()));
        }
        return CommonResult.dataSuccess(entryDTOList);
    }

    @Authorize
    @GetMapping("stages")
    public CommonResult<List<EntryDTO<Integer, String>>> iterationStageList() {
        List<EntryDTO<Integer, String>> entryDTOList = Lists.newArrayList();
        List<IterationStageState> stageList = Arrays.asList(IterationStageState.values());
        for (IterationStageState stageState : stageList) {
            entryDTOList.add(new EntryDTO<>(stageState.getStage(), stageState.getDesc()));
        }
        return CommonResult.dataSuccess(entryDTOList);
    }

    @Authorize
    @GetMapping("gitlab/url")
    public CommonResult<String> gitlabHost() {
        return CommonResult.dataSuccess(gitLabProperties.getHostUrl());
    }

    @GetMapping("groups")
    public CommonResult<List<String>> getGroups() {
        List<String> groupList = null;
        try {
            groupList = gitLabApiService.getGroups();
        } catch (IOException e) {
            LOGGER.error("getGroups fail", e);
            groupList = Collections.emptyList();
        }
        return CommonResult.dataSuccess(groupList);
    }

    @Authorize
    @GetMapping("dataExecType")
    public CommonResult<List<EntryDTO<String, String>>> dataExecTypeList() {
        List<EntryDTO<String, String>> entryDTOList = Lists.newArrayList();
        List<DataExecType> dataExecTypeList = Arrays.asList(DataExecType.values());
        for (DataExecType dataExecType : dataExecTypeList) {
            entryDTOList.add(new EntryDTO<>(dataExecType.getValue(), dataExecType.getDesc()));
        }
        return CommonResult.dataSuccess(entryDTOList);
    }

    @Authorize
    @GetMapping("dataExecState")
    public CommonResult<List<EntryDTO<Integer, String>>> dataExecStateList() {
        List<EntryDTO<Integer, String>> entryDTOList = Lists.newArrayList();
        List<DataExecState> dataExecStateList = Arrays.asList(DataExecState.values());
        for (DataExecState dataExecState : dataExecStateList) {
            entryDTOList.add(new EntryDTO<>(dataExecState.getValue(), dataExecState.getDesc()));
        }
        return CommonResult.dataSuccess(entryDTOList);
    }
}
