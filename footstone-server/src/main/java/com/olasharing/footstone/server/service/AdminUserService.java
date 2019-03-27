package com.olasharing.footstone.server.service;

import com.olasharing.footstone.repository.domain.AdminUser;

import java.util.List;

/**
 * AdminUserService
 *
 * @author liuyan
 * @date 2019-03-07
 */
public interface AdminUserService {

    /**
     * 用户信息
     *
     * @param username
     * @return
     */
    AdminUser getUser(String username);

    /**
     * 用户列表
     *
     * @return
     */
    List<AdminUser> getUserList();

    /**
     * 登入
     *
     * @param username
     * @param password
     * @return
     */
    String login(String username, String password);

    /**
     * 登出
     *
     * @param username
     * @return
     */
    void logout(String username);

    /**
     * 检测登陆token
     *
     * @param username
     * @param token
     * @return
     */
    void validateLogin(String username, String token);
}
