package com.olasharing.footstone.server.dto.user;

import lombok.Data;

import java.util.List;

/**
 * MenuDTO
 *
 * @author GW00168835
 */
@Data
public class MenuDTO {

    private String text;

    private String iconCls;

    private String menuUrl;

    private List<MenuDTO> children;
}
