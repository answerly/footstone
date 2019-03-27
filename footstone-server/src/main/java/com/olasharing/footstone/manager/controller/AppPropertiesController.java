package com.olasharing.footstone.manager.controller;

import com.olasharing.footstone.common.CommonConstants;
import com.olasharing.footstone.common.broadcast.BroadcastEvent;
import com.olasharing.footstone.common.broadcast.PassBroadcaster;
import com.olasharing.footstone.manager.AbstractController;
import com.olasharing.footstone.manager.protocol.CommonResult;
import com.olasharing.footstone.repository.domain.AppProperties;
import com.olasharing.footstone.server.service.AppPropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 配置管理
 *
 * @author liuyan
 * @date 2019-02-18
 */
@RestController
@RequestMapping(value = "properties")
public class AppPropertiesController extends AbstractController {

    @Autowired
    private PassBroadcaster passBroadcaster;

    @Autowired
    private AppPropertiesService appPropertiesService;

    @PostMapping("add")
    public CommonResult add(@RequestBody AppProperties appProperties) {
        appPropertiesService.insert(appProperties);
        return CommonResult.commonSuccess();
    }

    @PostMapping("edit")
    public CommonResult editValue(@RequestBody AppProperties appProperties) {
        appPropertiesService.updateEditValueById(appProperties);
        return CommonResult.commonSuccess();
    }

    @PostMapping("publish")
    public CommonResult publish(@RequestBody AppProperties propsToUpdate) {
        AppProperties newProperties = appPropertiesService.publish(propsToUpdate);
        sendToApp(newProperties);
        return CommonResult.commonSuccess();
    }

    @PostMapping("offline")
    public CommonResult offline(@RequestBody AppProperties propsToUpdate) {
        AppProperties newProperties = appPropertiesService.offline(propsToUpdate);
        sendToApp(newProperties);
        return CommonResult.commonSuccess();
    }

    @GetMapping("list")
    public CommonResult<List<AppProperties>> list(@RequestParam("appName") String appName,
                                                  @RequestParam("profile") String profile) {
        List<AppProperties> propertiesList = appPropertiesService.listByAppName(appName, profile);
        return CommonResult.dataSuccess(propertiesList);
    }

    private void sendToApp(AppProperties properties) {
        BroadcastEvent event = new BroadcastEvent();
        event.setEvent(CommonConstants.PROPERTIES_CHANGE_EVENT);
        event.setAppName(properties.getAppName());
        event.setProfile(properties.getProfile());
        passBroadcaster.send(event);
    }
}
