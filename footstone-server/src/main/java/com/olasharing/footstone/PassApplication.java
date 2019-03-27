package com.olasharing.footstone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;

/**
 * 项目打包
 *
 * @author liuyan
 * @date 2019-02-18
 */
@EnableEurekaServer
@EnableConfigServer
@EnableLdapRepositories
@SpringBootApplication
public class PassApplication {

    public static void main(String[] args) {
        SpringApplication.run(PassApplication.class, args);
    }
}
