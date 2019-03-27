package com.olasharing.footstone.repository.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;

/**
 * ldap 用户
 *
 * @author liuyan
 * @date 2019-03-07
 */
@Data
@Entry(base = "ou=OlaSharing,DC=olafuwu,DC=com", objectClasses = {"OrganizationalPerson", "Person", "top"})
public class AdminUser {

    @Id
    @JsonIgnore
    private Name id;

    @Attribute(name = "sAMAccountName")
    private String username;

    @Attribute(name = "userPrincipalName")
    private String userPrincipalName;

    @Attribute(name = "displayName")
    private String displayName;

    @Attribute(name = "mail")
    private String mail;
}
