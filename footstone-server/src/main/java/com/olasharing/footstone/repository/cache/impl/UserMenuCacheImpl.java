package com.olasharing.footstone.repository.cache.impl;

import com.olasharing.footstone.repository.cache.UserMenuCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * UserMenuCacheImpl
 *
 * @author GW00168835
 */
@Repository
public class UserMenuCacheImpl implements UserMenuCache {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Set<String> getUserMenuUrls(String username) {
        String rawKey = rawKey(username);
        return redisTemplate.opsForSet().members(rawKey);
    }

    @Override
    public void setUserMenuUrls(String username, Set<String> menuUrls) {
        String rawKey = rawKey(username);
        redisTemplate.opsForSet().add(rawKey, menuUrls.toArray(new String[menuUrls.size()]));
        redisTemplate.expire(rawKey, 5, TimeUnit.MINUTES);
    }

    private String rawKey(String username) {
        return "pass:login:menus:" + username;
    }
}
