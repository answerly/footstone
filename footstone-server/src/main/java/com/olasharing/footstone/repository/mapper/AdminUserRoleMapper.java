package com.olasharing.footstone.repository.mapper;

import com.olasharing.footstone.repository.domain.AdminUserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * UserRoleMapper
 *
 * @author GW00168835
 */
@Mapper
public interface AdminUserRoleMapper {

    /**
     * 新增
     *
     * @param adminUserRole
     * @return
     */
    int insert(AdminUserRole adminUserRole);

    /**
     * 查询
     *
     * @param username
     * @return
     */
    List<AdminUserRole> getUserRoles(String username);
}
