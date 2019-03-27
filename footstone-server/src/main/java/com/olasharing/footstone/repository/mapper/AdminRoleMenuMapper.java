package com.olasharing.footstone.repository.mapper;

import com.olasharing.footstone.repository.domain.AdminRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * AdminRoleMenuMapper
 *
 * @author GW00168835
 */
@Mapper
public interface AdminRoleMenuMapper {

    /**
     * 查询
     *
     * @param roleCodeList
     * @return
     */
    List<AdminRoleMenu> selectListByRoleCodes(@Param("roleCodeList") List<String> roleCodeList);
}
