package com.olasharing.footstone.manager.controller;

import com.olasharing.footstone.deploy.git.GitLabApiService;
import com.olasharing.footstone.manager.AbstractController;
import com.olasharing.footstone.manager.protocol.CommonResult;
import com.olasharing.footstone.repository.domain.ProjectIteration;
import com.olasharing.footstone.server.service.AppConfigService;
import com.olasharing.footstone.server.service.ProjectIterationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 迭代管理
 *
 * @author liuyan
 * @date 2019-02-18
 */
@RestController
@RequestMapping("iteration")
public class ProjectIterationController extends AbstractController {

    @Autowired
    private ProjectIterationService iterationService;

    @Autowired
    private AppConfigService appConfigService;

    @Autowired
    private GitLabApiService gitLabApiService;

    @PostMapping("add")
    public CommonResult add(@RequestBody ProjectIteration projectIteration) {
        iterationService.insert(projectIteration);
        return CommonResult.commonSuccess();
    }

    @GetMapping("list")
    public CommonResult<List<ProjectIteration>> list(@RequestParam("appName") String appName) {
        List<ProjectIteration> iterationList = iterationService.listByAppName(appName);
        return CommonResult.dataSuccess(iterationList);
    }
}
