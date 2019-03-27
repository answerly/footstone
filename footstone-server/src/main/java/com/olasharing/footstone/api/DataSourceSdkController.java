package com.olasharing.footstone.api;

import com.olasharing.footstone.api.dto.DataSourceConfigGroupDTO;
import com.olasharing.footstone.repository.domain.AppDataSource;
import com.olasharing.footstone.server.service.AppDataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * 数据源针对sdk访问
 *
 * @author liuyan
 * @date 2019-03-06
 */
@Validated
@RestController
public class DataSourceSdkController {

    @Autowired
    private AppDataSourceService appDataSourceService;

    @GetMapping("/sdk/datasources")
    public DataSourceConfigGroupDTO list(@RequestParam("appName") @NotBlank String appName,
                                         @RequestParam("profile") @NotBlank String profile) {
        DataSourceConfigGroupDTO dataSourceConfigGroup = new DataSourceConfigGroupDTO();
        Set<String> profiles = StringUtils.commaDelimitedListToSet(profile);
        List<AppDataSource> appDataSourceList = appDataSourceService.selectByAppName(appName, profiles);
        if (appDataSourceList == null) {
            appDataSourceList = Collections.emptyList();
        }
        appDataSourceList.sort(Comparator.comparing(AppDataSource::getDefaultFlag));
        dataSourceConfigGroup.setDataSourceConfigList(appDataSourceList);
        return dataSourceConfigGroup;
    }
}
