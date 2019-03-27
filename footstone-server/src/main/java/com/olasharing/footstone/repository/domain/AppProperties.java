package com.olasharing.footstone.repository.domain;

import lombok.Data;

/**
 * 系统配置
 *
 * @author liuyan
 * @date 2019-02-19
 */
@Data
public class AppProperties {

    private Long id;

    private String key;

    private String value;

    private String appName;

    private String appShowName;

    private String profile;

    private String label;

    /**
     * 发布 0=未发布 1=已删除 2=已发布 3=已下线
     */
    private Integer state;

    private String editValue;

}
