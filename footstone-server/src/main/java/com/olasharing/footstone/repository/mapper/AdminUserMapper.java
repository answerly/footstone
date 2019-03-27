package com.olasharing.footstone.repository.mapper;

import com.olasharing.footstone.repository.domain.AdminUser;
import org.springframework.data.ldap.repository.LdapRepository;

/**
 * AdminUserMapper
 *
 * @author liuyan
 * @date 2019-03-07
 */
public interface AdminUserMapper extends LdapRepository<AdminUser> {

}
