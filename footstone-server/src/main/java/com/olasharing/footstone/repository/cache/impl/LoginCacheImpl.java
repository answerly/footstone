package com.olasharing.footstone.repository.cache.impl;

import com.olasharing.footstone.manager.config.LoginProperties;
import com.olasharing.footstone.repository.cache.LoginCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

/**
 * LoginCacheImpl
 *
 * @author GW00168835
 */
@Repository
public class LoginCacheImpl implements LoginCache {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private LoginProperties loginProperties;

    @Override
    public void setToken(String username, String token) {
        String rawKey = rawKey(username);
        redisTemplate.opsForValue().set(rawKey, token, loginProperties.getTimeout(), TimeUnit.MINUTES);
    }

    @Override
    public String getToken(String username) {

        String rawKey = rawKey(username);
        return redisTemplate.opsForValue().get(rawKey);
    }

    @Override
    public void removeToken(String username) {

        String rawKey = rawKey(username);
        redisTemplate.delete(rawKey);
    }

    private String rawKey(String username) {
        return "pass:login:token:" + username;
    }
}
