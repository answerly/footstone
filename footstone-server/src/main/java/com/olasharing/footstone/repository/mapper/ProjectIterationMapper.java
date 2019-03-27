package com.olasharing.footstone.repository.mapper;

import com.olasharing.footstone.repository.domain.ProjectIteration;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ProjectIterationMapper
 *
 * @author liuyan
 * @date 2019-02-19
 */
@Mapper
public interface ProjectIterationMapper {

    /**
     * 系统迭代
     *
     * @param projectIteration
     * @return
     */
    int insert(ProjectIteration projectIteration);

    /**
     * 查询迭代
     *
     * @param appName
     * @return
     */
    List<ProjectIteration> selectListByAppName(@Param("appName") String appName);

    /**
     * 查询
     *
     * @param id
     * @return
     */
    ProjectIteration selectOneById(@Param("id") Integer id);

    /**
     * 更新状态
     *
     * @param id
     * @param sourceStage
     * @param targetStage
     * @return
     */
    int updateStageByIdAndSource(@Param("id") Integer id,
                                 @Param("sourceStage") Integer sourceStage,
                                 @Param("targetStage") Integer targetStage);
}
