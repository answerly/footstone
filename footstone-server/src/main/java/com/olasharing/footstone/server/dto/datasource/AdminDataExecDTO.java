package com.olasharing.footstone.server.dto.datasource;

import lombok.Data;

import java.util.Date;

/**
 * AdminDataExecDTO
 *
 * @author GW00168835
 */
@Data
public class AdminDataExecDTO {

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

    private String createUsername;

    private String createDisplayName;

    private String execUsername;

    private String execDisplayName;

    private Date gmtCreate;

    private Date gmtModified;

    private DataExecResultDTO execResultData;
}
