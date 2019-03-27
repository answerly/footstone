package com.olasharing.footstone.manager.controller;

import com.olasharing.footstone.common.CommonConstants;
import com.olasharing.footstone.manager.AbstractController;
import com.olasharing.footstone.manager.protocol.CommonResult;
import com.olasharing.footstone.repository.domain.AppProperties;
import com.olasharing.footstone.server.service.AppPropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * system
 *
 * @author GW00168835
 */
@RestController
@RequestMapping("properties/system")
public class SystemPropertiesController extends AbstractController {

    @Autowired
    private AppPropertiesService appPropertiesService;

    @GetMapping("list")
    public CommonResult<List<AppProperties>> list(@RequestParam("profile") String profile) {
        String appName = CommonConstants.COMMON_PROPS_PROJECT_NAME;
        List<AppProperties> propertiesList = appPropertiesService.listByAppName(appName, profile);
        return CommonResult.dataSuccess(propertiesList);
    }


    @PostMapping("add")
    public CommonResult add(@RequestBody AppProperties appProperties) {
        appPropertiesService.insertApplicationProps(appProperties);
        return CommonResult.commonSuccess();
    }
}
