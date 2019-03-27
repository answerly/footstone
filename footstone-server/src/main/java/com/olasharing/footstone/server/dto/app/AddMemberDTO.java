package com.olasharing.footstone.server.dto.app;

import lombok.Data;

/**
 * AddDeveloperDTO
 *
 * @author GW00168835
 */
@Data
public class AddMemberDTO {

    private String appName;

    private String username;

    private String displayName;

    private String memberTag;
}
