package com.olasharing.footstone.repository.domain;

import lombok.Data;

import java.util.Date;

/**
 * UserRole
 *
 * @author GW00168835
 */
@Data
public class AdminUserRole {

    private Integer id;

    private String username;

    private String roleCode;

    private Date gmtModified;
}
