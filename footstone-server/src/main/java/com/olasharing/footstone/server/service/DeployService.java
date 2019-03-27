package com.olasharing.footstone.server.service;

import com.olasharing.footstone.deploy.maven.MavenProject;

import java.util.List;

/**
 * @author GW00168835
 */
public interface DeployService {

    /**
     * 获取模块
     *
     * @param gitUrl
     * @param branchName
     * @return
     */
    List<MavenProject> getModules(String gitUrl, String branchName);


    /**
     * 打包
     *
     * @param gitUrl
     * @param branchName
     * @return
     */
    String packaging(String gitUrl, String branchName);
}
