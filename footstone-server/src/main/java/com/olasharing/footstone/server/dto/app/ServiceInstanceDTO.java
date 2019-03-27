package com.olasharing.footstone.server.dto.app;

import lombok.Data;

/**
 * ServiceInstanceDTO
 *
 * @author liuyan
 * @date 2019-03-06
 */
@Data
public class ServiceInstanceDTO {

    private String host;

    private Integer port;

    private String profile;

}
