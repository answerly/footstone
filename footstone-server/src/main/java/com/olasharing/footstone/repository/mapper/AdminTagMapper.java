package com.olasharing.footstone.repository.mapper;

import com.olasharing.footstone.repository.domain.AdminTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * AdminTagMapper
 *
 * @author GW00168835
 */
@Mapper
public interface AdminTagMapper {

    /**
     * 插入
     *
     * @param adminTagList
     * @return
     */
    int insert(@Param("adminTagList") List<AdminTag> adminTagList);

    /**
     * 查询
     *
     * @param tagType
     * @param tagRef
     * @return
     */
    List<AdminTag> selectListByRef(@Param("tagType") String tagType, @Param("tagRef") String tagRef);

    /**
     * 查询
     *
     * @param tagType
     * @param tagRef
     * @param tagCode
     * @return
     */
    AdminTag selectOneByCode(@Param("tagType") String tagType, @Param("tagRef") String tagRef, @Param("tagCode") String tagCode);
}
