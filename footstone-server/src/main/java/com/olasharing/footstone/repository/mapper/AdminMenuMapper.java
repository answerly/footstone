package com.olasharing.footstone.repository.mapper;

import com.olasharing.footstone.repository.domain.AdminMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * AdminMenuMapper
 *
 * @author GW00168835
 */
@Mapper
public interface AdminMenuMapper {

    /**
     * 查询
     *
     * @param menuCodeList
     * @return
     */
    List<AdminMenu> selectListByCodes(@Param("menuCodeList") List<String> menuCodeList);
}
