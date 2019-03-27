package com.olasharing.footstone.repository.mapper;

import com.olasharing.footstone.repository.domain.AppProperties;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ProjectPropertiesMapper
 *
 * @author liuyan
 * @date 2019-02-19
 */
@Mapper
public interface AppPropertiesMapper {

    /**
     * 插入
     *
     * @param appProperties
     * @return
     */
    int insert(AppProperties appProperties);

    /**
     * 主键更新
     *
     * @param appProperties
     * @return
     */
    int updateByPrimaryKey(AppProperties appProperties);

    /**
     * 查询项目配置
     *
     * @param appName
     * @param profile
     * @return
     */
    List<AppProperties> selectListByAppName(@Param("appName") String appName,
                                            @Param("profile") String profile);

    /**
     * 查询
     *
     * @param id
     * @return
     */
    AppProperties selectByPrimaryKey(@Param("id") Long id);


    /**
     * 主键更新
     *
     * @param id
     * @param newState
     * @param notEqualState
     * @return
     */
    int updatePublishByPrimaryKey(@Param("id") Long id, @Param("newState") Integer newState,
                                  @Param("notEqualState") Integer notEqualState);

    /**
     * 主键更新
     *
     * @param id
     * @param newState
     * @return
     */
    int updateOfflineByPrimaryKey(@Param("id") Long id, @Param("newState") Integer newState);
}
