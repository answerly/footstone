package com.olasharing.footstone.server.config;

import com.olasharing.footstone.common.CommonConstants;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author GW00168835
 */
@Aspect
@Component
public class ConfigServiceAspect {

    @Around("execution(* org.springframework.cloud.config.server.environment.JdbcEnvironmentRepository.findOne(String,String,String))")
    public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {
        String commonPropsName = CommonConstants.COMMON_PROPS_PROJECT_NAME;
        Object[] args = joinPoint.getArgs();
        String application = (String) (args[0]);
        if (application.startsWith(commonPropsName) && !application.equals(commonPropsName)) {
            application = commonPropsName + "," + application;
        }
        args[0] = application;
        return joinPoint.proceed(args);
    }
}
