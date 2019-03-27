package com.olasharing.footstone.deploy.git;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * GitProperties
 *
 * @author GW00168835
 */
@Data
@Component
@ConfigurationProperties(prefix = "pass.gitlab")
public class GitLabProperties {

    /**
     * https://gitlab.olafuwu.com
     */
    private String hostUrl;

    /**
     * 5xxWqyzzNdCPxGnCYCXY
     */
    private String privateAccessToken;

}
