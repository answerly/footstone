package com.olasharing.footstone.server.service;

import java.util.List;
import java.util.Set;

/**
 * AdminUserRoleService
 *
 * @author GW00168835
 */
public interface AdminUserRoleService {

    /**
     * getUserMenuCodes
     *
     * @param username
     * @return
     */
    List<String> getUserMenuCodes(String username);

    /**
     * getUserMenuUrls
     *
     * @param username
     * @return
     */
    Set<String> getUserMenuUrls(String username);
}
