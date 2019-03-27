package com.olasharing.footstone.repository.mapper;

import com.olasharing.footstone.repository.domain.AdminDataExec;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * AdminDataExecMapper
 *
 * @author GW00168835
 */
@Mapper
public interface AdminDataExecMapper {

    /**
     * 插入
     *
     * @param adminDataExec
     * @return
     */
    int insert(AdminDataExec adminDataExec);

    /**
     * 查询
     *
     * @param id
     * @return
     */
    AdminDataExec selectOneByPrimaryKey(Integer id);


    /**
     * 查询
     *
     * @param adminDataExec
     * @return
     */
    List<AdminDataExec> selectListBySelective(AdminDataExec adminDataExec);


    /**
     * 更新
     *
     * @param id
     * @param oldState
     * @param newState
     * @return
     */
    int updateStateByPrimaryKey(@Param("id") Integer id,
                                @Param("oldState") Integer oldState,
                                @Param("newState") Integer newState);

    /**
     * 更新
     *
     * @param id
     * @param execResult
     * @param newState
     * @return
     */
    int updateResultByPrimaryKey(@Param("id") Integer id, @Param("execResult") String execResult, @Param("newState") Integer newState);
}
