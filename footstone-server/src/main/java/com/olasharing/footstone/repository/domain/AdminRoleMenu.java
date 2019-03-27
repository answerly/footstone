package com.olasharing.footstone.repository.domain;

import lombok.Data;

import java.util.Date;

/**
 * AdminRoleMenu
 *
 * @author GW00168835
 */
@Data
public class AdminRoleMenu {

    private Integer id;

    private String roleCode;

    private String menuCode;

    private Date gmtModified;

    private Integer relationState;
}
