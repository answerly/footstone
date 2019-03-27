package com.olasharing.footstone.repository.mapper;

import com.olasharing.footstone.repository.domain.AppConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ProjectMapper
 *
 * @author liuyan
 * @date 2019-02-19
 */
@Mapper
@Repository
public interface AppConfigMapper {

    /**
     * 新增
     *
     * @param appConfig
     * @return
     */
    int insert(AppConfig appConfig);

    /**
     * 查询
     *
     * @param appName
     * @return
     */
    AppConfig selectOneByName(@Param("appName") String appName);

    /**
     * 查询
     *
     * @param appName
     * @return
     */
    List<AppConfig> selectListLikeName(@Param("appName") String appName);


    /**
     * 查询
     *
     * @param appName
     * @return
     */
    AppConfig selectOneByUpperName(@Param("appName") String appName);
}
