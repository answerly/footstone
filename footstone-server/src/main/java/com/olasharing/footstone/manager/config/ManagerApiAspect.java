package com.olasharing.footstone.manager.config;

import com.olasharing.footstone.common.protocol.BizCodeEnum;
import com.olasharing.footstone.common.protocol.BizException;
import com.olasharing.footstone.manager.AbstractController;
import com.olasharing.footstone.manager.annotation.Authorize;
import com.olasharing.footstone.manager.protocol.CommonResult;
import com.olasharing.footstone.server.service.AdminUserRoleService;
import com.olasharing.footstone.server.service.AdminUserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * @author GW00168835
 */
@Aspect
@Component
public class ManagerApiAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(ManagerApiAspect.class);

    @Autowired
    private AdminUserService userService;

    @Autowired
    private AdminUserRoleService userRoleService;

    private static String invokeSignature(ProceedingJoinPoint joinPoint) {
        return joinPoint.getSignature().toShortString();
    }

    @Around("execution(* com.olasharing.footstone.manager.controller.*.*(..))")
    public Object invoke(ProceedingJoinPoint joinPoint) {
        try {
            checkLogin(joinPoint);
            checkAuthorize(joinPoint);
            return joinPoint.proceed();
        } catch (BizException ex) {
            LOGGER.error("biz error: " + invokeSignature(joinPoint), ex);
            CommonResult commonResult = new CommonResult();
            commonResult.setCode(ex.getCode());
            commonResult.setMessage(ex.getMessage());
            return commonResult;
        } catch (Throwable ex) {
            LOGGER.error("system error: " + invokeSignature(joinPoint), ex);
            CommonResult commonResult = new CommonResult();
            commonResult.setCode(BizCodeEnum.SYSTEM_ERROR.getCode());
            commonResult.setMessage(BizCodeEnum.SYSTEM_ERROR.getMessage());
            return commonResult;
        }
    }

    private void checkLogin(ProceedingJoinPoint joinPoint) {
        Authorize authorize = getAuthorizeOnMethod(joinPoint);
        if (authorize != null && authorize.ignore()) {
            return;
        }
        String token = AbstractController.getToken();
        String username = AbstractController.getUsername();
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(username)) {
            CommonResult commonResult = new CommonResult();
            commonResult.setCode(BizCodeEnum.LOGIN_TIMEOUT.getCode());
            commonResult.setMessage(BizCodeEnum.LOGIN_TIMEOUT.getMessage());
            throw BizCodeEnum.LOGIN_TIMEOUT.newBizException();
        }
        userService.validateLogin(username, token);
    }

    private void checkAuthorize(ProceedingJoinPoint joinPoint) {
        Authorize authorize = getAuthorizeOnMethod(joinPoint);
        if (authorize != null && authorize.ignore()) {
            return;
        }
        if (authorize != null && authorize.permitAll()) {
            return;
        }
        String requestUri = getRequestUri();
        String username = AbstractController.getUsername();
        Set<String> menuUrls = userRoleService.getUserMenuUrls(username);
        if (!menuUrls.contains(requestUri)) {
            LOGGER.warn("authorize error:{}", requestUri);
            throw BizCodeEnum.AUTHORIZE_ERROR.newBizException();
        }
    }

    private String getRequestUri() {
        HttpServletRequest request = AbstractController.getRequest();
        return request.getRequestURI();
    }

    private Authorize getAuthorizeOnMethod(ProceedingJoinPoint joinPoint) {
        if (joinPoint.getSignature() instanceof MethodSignature) {
            return ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(Authorize.class);
        }
        return null;
    }
}
