package com.olasharing.footstone.server.service.impl;

import com.google.common.collect.Lists;
import com.olasharing.footstone.common.protocol.BizCodeEnum;
import com.olasharing.footstone.manager.config.LoginProperties;
import com.olasharing.footstone.repository.cache.LoginCache;
import com.olasharing.footstone.repository.domain.AdminUser;
import com.olasharing.footstone.repository.mapper.AdminUserMapper;
import com.olasharing.footstone.server.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * AdminUserServiceImpl
 *
 * @author liuyan
 * @date 2019-03-07
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private AdminUserMapper adminUserMapper;
    @Autowired
    private LoginProperties loginProperties;
    @Autowired
    private LoginCache loginCache;

    @Override
    public AdminUser getUser(String username) {
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectClass", "person"));
        filter.and(new EqualsFilter("sAMAccountName", username));
        LdapQuery ldapQuery = LdapQueryBuilder.query()
                .base("ou=OlaSharing").filter(filter);
        Optional<AdminUser> optional = adminUserMapper.findOne(ldapQuery);
        if (!optional.isPresent()) {
            throw BizCodeEnum.USER_NOT_EXIST.newBizException();
        }
        return optional.get();
    }

    @Override
    public List<AdminUser> getUserList() {
        LdapQuery ldapQuery = LdapQueryBuilder.query()
                .base("ou=OlaSharing")
                .filter(new EqualsFilter("objectClass", "person"));
        Iterable<AdminUser> iterable = adminUserMapper.findAll(ldapQuery);
        if (iterable == null) {
            return Collections.emptyList();
        }
        return Lists.newArrayList(iterable);
    }

    @Override
    public String login(String username, String password) {
        AdminUser user = getUser(username);
        if (user == null) {
            throw BizCodeEnum.USER_NOT_EXIST.newBizException();
        }
        if (!Objects.equals(loginProperties.getPassword(), password)) {
            throw BizCodeEnum.USERNAME_OR_PWD_ERROR.newBizException();
        }
        String token = getToken();
        loginCache.setToken(username, token);
        return token;
    }

    @Override
    public void logout(String username) {
        loginCache.removeToken(username);
    }

    @Override
    public void validateLogin(String username, String token) {
        String tokenFromRepository = loginCache.getToken(username);
        if (StringUtils.isEmpty(tokenFromRepository)) {
            throw BizCodeEnum.LOGIN_TIMEOUT.newBizException();
        }
        if (!Objects.equals(tokenFromRepository, token)) {
            throw BizCodeEnum.LOGIN_TIMEOUT.newBizException();
        }
    }

    private String getToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
