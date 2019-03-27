package com.olasharing.footstone.manager;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * AbstractController
 *
 * @author GW00168835
 */
public abstract class AbstractController {

    public static String getUsername() {
        String usernameKey = "X-OLA-USERNAME";
        String username = getParameter(usernameKey);
        return username;
    }

    public static String getToken() {
        String tokenKey = "X-OLA-TOKEN";
        String token = getParameter(tokenKey);
        return token;
    }

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    public static String getParameter(String key) {
        HttpServletRequest request = getRequest();
        String headerToken = request.getHeader(key);
        if (StringUtils.isEmpty(headerToken)) {
            return request.getParameter(key);
        }
        return headerToken;
    }
}
