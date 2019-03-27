package com.olasharing.footstone.server.dto.datasource;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * DataExecResultDTO
 *
 * @author GW00168835
 */
@Data
public class DataExecResultDTO {

    /**
     * 0 = 未执行
     * 1 = 查询成功
     * 2 = 更新成功
     * 3 = 执行失败
     */
    private Integer code;

    private String message;

    private List<String> queryHeaders;

    private List<Map> queryDataList;
}
