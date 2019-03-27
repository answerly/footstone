package com.olasharing.footstone.server.dto.user;

import lombok.Data;

import java.util.List;

/**
 * UserDTO
 *
 * @author GW00168835
 */
@Data
public class UserDTO {

    private String username;

    private String displayName;

    private List<String> roles;
}
