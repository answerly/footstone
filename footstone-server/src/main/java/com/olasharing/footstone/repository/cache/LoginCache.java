package com.olasharing.footstone.repository.cache;

/**
 * LoginCache
 *
 * @author GW00168835
 */
public interface LoginCache {

    /**
     * setToken
     *
     * @param username
     * @param token
     */
    void setToken(String username, String token);

    /**
     * getToken
     *
     * @param username
     * @return
     */
    String getToken(String username);

    /**
     * removeToken
     *
     * @param username
     */
    void removeToken(String username);
}
