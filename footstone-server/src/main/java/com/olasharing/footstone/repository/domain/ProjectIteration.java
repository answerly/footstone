package com.olasharing.footstone.repository.domain;

import lombok.Data;

import java.util.Date;

/**
 * 系统迭代
 *
 * @author liuyan
 * @date 2019-02-18
 */
@Data
public class ProjectIteration {

    private Integer id;

    /**
     * 迭代名称
     */
    private String name;

    /**
     * 系统名称
     */
    private String appName;

    private String appShowName;

    /**
     * 分支名称
     */
    private String branchName;

    /**
     * 项目阶段
     */
    private Integer stage;

    private Date gmtCreate;
}
