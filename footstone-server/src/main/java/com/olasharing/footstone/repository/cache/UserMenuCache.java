package com.olasharing.footstone.repository.cache;

import java.util.Set;

/**
 * UserMenuCache
 *
 * @author GW00168835
 */
public interface UserMenuCache {

    /**
     * 查询
     *
     * @param username
     * @return
     */
    Set<String> getUserMenuUrls(String username);

    /**
     * 插入
     *
     * @param username
     * @param menuUrls
     */
    void setUserMenuUrls(String username, Set<String> menuUrls);
}
