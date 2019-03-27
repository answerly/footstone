package com.olasharing.footstone.repository.mapper;

import com.olasharing.footstone.repository.domain.AppDataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * AppDataSourceMapper
 *
 * @author liuyan
 * @date 2019-02-19
 */
@Mapper
public interface AppDataSourceMapper {

    /**
     * 新增
     *
     * @param appDataSource
     * @return
     */
    int insert(AppDataSource appDataSource);

    /**
     * 查询
     *
     * @param appName
     * @param profiles
     * @return
     */
    List<AppDataSource> selectListByAppName(@Param("appName") String appName,
                                            @Param("profiles") Collection<String> profiles);


    /**
     * 新增
     *
     * @param appDataSource
     * @return
     */
    int updateByPrimaryKey(AppDataSource appDataSource);


    /**
     * 查询
     *
     * @param id
     * @return
     */
    AppDataSource selectByPrimaryKey(Integer id);

    /**
     * 查询
     *
     * @param appDataSource
     * @return
     */
    AppDataSource selectByUniqueKey(AppDataSource appDataSource);
}
