package com.olasharing.footstone.repository.domain;

import lombok.Data;

import java.util.Date;

/**
 * 数据库变更申请
 *
 * @author GW00168835
 */
@Data
public class AdminDataExec {

    private Integer id;

    private String execDesc;

    private Integer execState;

    private String appName;

    private String appShowName;

    private String datasourceId;

    private String profile;

    private String execType;

    private Integer execNum;

    private String execScript;

    private String execResult;

    private String createUsername;

    private String createDisplayName;

    private String execUsername;

    private String execDisplayName;

    private Date gmtCreate;

    private Date gmtModified;
}
