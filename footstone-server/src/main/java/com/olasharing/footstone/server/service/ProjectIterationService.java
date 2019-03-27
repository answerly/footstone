package com.olasharing.footstone.server.service;

import com.olasharing.footstone.repository.domain.ProjectIteration;

import java.util.List;

/**
 * ProjectIterationService
 *
 * @author liuyan
 * @date 2019-02-19
 */
public interface ProjectIterationService {

    /**
     * 新建
     *
     * @param projectIteration
     */
    void insert(ProjectIteration projectIteration);

    /**
     * 查询
     *
     * @param appName
     * @return
     */
    List<ProjectIteration> listByAppName(String appName);

    /**
     * 详情
     *
     * @param id
     * @return
     */
    ProjectIteration getAppIterationById(Integer id);

    /**
     * 测试
     *
     * @param projectIteration
     */
    void test(ProjectIteration projectIteration);


    /**
     * 预发
     *
     * @param projectIteration
     */
    void uat(ProjectIteration projectIteration);

    /**
     * 生产
     *
     * @param projectIteration
     */
    void prod(ProjectIteration projectIteration);
}
