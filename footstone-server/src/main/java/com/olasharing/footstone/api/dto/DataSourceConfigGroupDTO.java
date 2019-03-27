package com.olasharing.footstone.api.dto;

import com.olasharing.footstone.repository.domain.AppDataSource;
import lombok.Data;

import java.util.List;

/**
 * DataSourceConfigGroupDTO
 *
 * @author liuyan
 * @date 2019-02-21
 */
@Data
public class DataSourceConfigGroupDTO {

    private List<AppDataSource> dataSourceConfigList;

}
