package com.olasharing.footstone.repository.domain;

import lombok.Data;

import java.util.Date;

/**
 * AdminMenu
 *
 * @author GW00168835
 */
@Data
public class AdminMenu {

    private Integer id;

    private String menuCode;

    private String menuName;

    private String menuUrls;

    private Date gmtModified;

    private Integer menuType;

    private String menuState;
}
