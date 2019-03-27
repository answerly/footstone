package com.olasharing.footstone.server.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.olasharing.footstone.common.JsonUtils;
import com.olasharing.footstone.repository.cache.UserMenuCache;
import com.olasharing.footstone.repository.domain.AdminMenu;
import com.olasharing.footstone.repository.domain.AdminRoleMenu;
import com.olasharing.footstone.repository.domain.AdminUserRole;
import com.olasharing.footstone.repository.mapper.AdminMenuMapper;
import com.olasharing.footstone.repository.mapper.AdminRoleMenuMapper;
import com.olasharing.footstone.repository.mapper.AdminUserRoleMapper;
import com.olasharing.footstone.server.service.AdminUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * AdminUserRoleServiceImpl
 *
 * @author GW00168835
 */
@Service
public class AdminUserRoleServiceImpl implements AdminUserRoleService {

    @Autowired
    private AdminUserRoleMapper adminUserRoleMapper;

    @Autowired
    private AdminRoleMenuMapper adminRoleMenuMapper;

    @Autowired
    private AdminMenuMapper adminMenuMapper;

    @Autowired
    private UserMenuCache userMenuCache;

    @Override
    public List<String> getUserMenuCodes(String username) {
        List<AdminRoleMenu> roleMenuList = null;
        List<AdminUserRole> userRoleList = adminUserRoleMapper.getUserRoles(username);
        if (!CollectionUtils.isEmpty(userRoleList)) {
            List<String> roleCodeList = userRoleList.stream().map(AdminUserRole::getRoleCode).collect(Collectors.toList());
            roleMenuList = adminRoleMenuMapper.selectListByRoleCodes(roleCodeList);
        }
        if (!CollectionUtils.isEmpty(roleMenuList)) {
            List<String> menuCodeList = Lists.newArrayList();
            for (AdminRoleMenu roleMenu : roleMenuList) {
                menuCodeList.add(roleMenu.getMenuCode());
            }
            return menuCodeList;
        }
        return Collections.emptyList();
    }

    @Override
    public Set<String> getUserMenuUrls(String username) {
        Set<String> userMenuUrls = userMenuCache.getUserMenuUrls(username);
        if (CollectionUtils.isEmpty(userMenuUrls)) {
            userMenuUrls = getUserMenuUrls0(username);
            if (!CollectionUtils.isEmpty(userMenuUrls)) {
                userMenuCache.setUserMenuUrls(username, userMenuUrls);
            }
        }
        return userMenuUrls;
    }

    private Set<String> getUserMenuUrls0(String username) {
        List<AdminMenu> menuList = Collections.emptyList();
        List<String> menuCodeList = getUserMenuCodes(username);
        if (!CollectionUtils.isEmpty(menuCodeList)) {
            menuList = adminMenuMapper.selectListByCodes(menuCodeList);
        }
        if (!CollectionUtils.isEmpty(menuList)) {
            Set<String> menuUrlsList = Sets.newHashSet();
            for (AdminMenu menu : menuList) {
                menuUrlsList.addAll(JsonUtils.parseArray(menu.getMenuUrls(), String.class));
            }
            return menuUrlsList;
        }
        return Collections.emptySet();
    }
}
