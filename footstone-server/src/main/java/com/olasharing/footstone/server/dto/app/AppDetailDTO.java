package com.olasharing.footstone.server.dto.app;

import com.olasharing.footstone.server.dto.user.UserDTO;
import lombok.Data;

import java.util.List;

/**
 * AppDetailDTO
 *
 * @author GW00168835
 */
@Data
public class AppDetailDTO {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 项目名称(唯一索引)
     */
    private String appName;

    /**
     * 显示名称
     */
    private String showName;

    /**
     * 应用类型
     */
    private Integer appType;

    /**
     * owner
     */
    private String username;

    /**
     * owner显示名称
     */
    private String displayName;

    /**
     * 代码仓库组
     */
    private String repGroup;

    /**
     * 代码仓库名称
     */
    private String repName;

    /**
     * 修改时间
     */
    private java.util.Date gmtModified;


    /**
     * OPS人员
     */
    private List<UserDTO> masterList;

    /**
     * 研发人员
     */
    private List<UserDTO> developerList;


    /**
     * 测试人员
     */
    private List<UserDTO> testerList;
}
