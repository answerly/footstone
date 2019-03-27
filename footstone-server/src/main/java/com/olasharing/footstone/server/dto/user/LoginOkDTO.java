package com.olasharing.footstone.server.dto.user;

import lombok.Data;

/**
 * LoginOkDTO
 *
 * @author GW00168835
 */
@Data
public class LoginOkDTO {

    private String token;

    private String username;
}
