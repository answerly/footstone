package com.olasharing.footstone.server.service.impl;

import com.olasharing.footstone.common.protocol.BizCodeEnum;
import com.olasharing.footstone.deploy.git.GitLabApiService;
import com.olasharing.footstone.repository.domain.AppConfig;
import com.olasharing.footstone.repository.domain.ProjectIteration;
import com.olasharing.footstone.repository.enums.IterationStageState;
import com.olasharing.footstone.repository.mapper.ProjectIterationMapper;
import com.olasharing.footstone.server.service.AppConfigService;
import com.olasharing.footstone.server.service.ProjectIterationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * ProjectIterationServiceImpl
 *
 * @author liuyan
 * @date 2019-02-19
 */
@Service
public class ProjectIterationServiceImpl implements ProjectIterationService {

    @Autowired
    private AppConfigService appConfigService;

    @Autowired
    private ProjectIterationMapper iterationMapper;

    @Autowired
    private GitLabApiService gitLabApiService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(ProjectIteration projectIteration) {
        String appName = projectIteration.getAppName();
        String branchName = projectIteration.getBranchName();

        AppConfig appConfig = appConfigService.getByAppName(appName);
        projectIteration.setStage(IterationStageState.DEV.getStage());
        projectIteration.setGmtCreate(new Date());
        projectIteration.setAppShowName(appConfig.getShowName());
        iterationMapper.insert(projectIteration);
        try {
            gitLabApiService.createBranch(appConfig.getRepGroup(), appConfig.getRepName(), branchName);
        } catch (Exception ex) {
            throw BizCodeEnum.DISCOVERY_ERROR.newBizException(ex);
        }
    }

    @Override
    public List<ProjectIteration> listByAppName(String appName) {
        return iterationMapper.selectListByAppName(appName);
    }

    @Override
    public ProjectIteration getAppIterationById(Integer id) {
        ProjectIteration iteration = iterationMapper.selectOneById(id);
        if (iteration == null) {
            throw BizCodeEnum.ITERATION_NOT_EXIST.newBizException();
        }
        return iteration;
    }

    @Override
    public void test(ProjectIteration projectIteration) {
        Integer id = projectIteration.getId();
        Integer sourceStage = IterationStageState.DEV.getStage();
        Integer targetStage = IterationStageState.TEST.getStage();

        int rows = iterationMapper.updateStageByIdAndSource(id, sourceStage, targetStage);
        if (rows != 1) {
            throw new IllegalArgumentException("迭代不能进入测试阶段");
        }
    }

    @Override
    public void uat(ProjectIteration projectIteration) {
        Integer id = projectIteration.getId();
        Integer sourceStage = IterationStageState.TEST.getStage();
        Integer targetStage = IterationStageState.STAGE.getStage();

        int rows = iterationMapper.updateStageByIdAndSource(id, sourceStage, targetStage);
        if (rows != 1) {
            throw new IllegalArgumentException("迭代不能进入预发阶段");
        }
    }

    @Override
    public void prod(ProjectIteration projectIteration) {
        Integer id = projectIteration.getId();
        Integer sourceStage = IterationStageState.STAGE.getStage();
        Integer targetStage = IterationStageState.PROD.getStage();

        int rows = iterationMapper.updateStageByIdAndSource(id, sourceStage, targetStage);
        if (rows != 1) {
            throw new IllegalArgumentException("迭代不能进入生产阶段");
        }
    }

}
