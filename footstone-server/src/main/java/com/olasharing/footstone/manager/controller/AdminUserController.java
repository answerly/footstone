package com.olasharing.footstone.manager.controller;

import com.olasharing.footstone.manager.AbstractController;
import com.olasharing.footstone.manager.annotation.Authorize;
import com.olasharing.footstone.manager.protocol.CommonResult;
import com.olasharing.footstone.repository.domain.AdminUser;
import com.olasharing.footstone.server.dto.user.LoginDTO;
import com.olasharing.footstone.server.dto.user.LoginOkDTO;
import com.olasharing.footstone.server.dto.user.MenuDTO;
import com.olasharing.footstone.server.dto.user.UserDTO;
import com.olasharing.footstone.server.service.AdminUserRoleService;
import com.olasharing.footstone.server.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * UserController
 *
 * @author liuyan
 * @date 2019-03-07
 */
@RestController
@RequestMapping("user")
public class AdminUserController extends AbstractController {

    @Autowired
    private AdminUserService userService;

    @Autowired
    private AdminUserRoleService userRoleService;

    @PostMapping("login")
    @Authorize(ignore = true)
    public CommonResult<LoginOkDTO> login(@RequestBody LoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        String token = userService.login(username, loginDTO.getPassword());
        LoginOkDTO loginOkDTO = new LoginOkDTO();
        loginOkDTO.setToken(token);
        loginOkDTO.setUsername(username);
        return CommonResult.dataSuccess(loginOkDTO);
    }

    @Authorize
    @PostMapping("logout")
    public CommonResult logout() {
        userService.logout(getUsername());
        return CommonResult.commonSuccess();
    }

    @Authorize
    @GetMapping("info")
    public CommonResult<UserDTO> getUser() {
        String username = getUsername();
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setRoles(Collections.emptyList());

        AdminUser user = userService.getUser(username);
        userDTO.setDisplayName(user.getDisplayName());

        List<String> userRoles = userRoleService.getUserMenuCodes(username);
        if (!CollectionUtils.isEmpty(userRoles)) {
            userDTO.setRoles(userRoles.stream().distinct().collect(Collectors.toList()));
        }
        return CommonResult.dataSuccess(userDTO);
    }

    @GetMapping("list")
    public CommonResult<List<AdminUser>> getUserList() {
        List<AdminUser> userList = userService.getUserList();
        return CommonResult.dataSuccess(userList);
    }

    @GetMapping("menus")
    public CommonResult<List<MenuDTO>> getUserMenus() {
        return CommonResult.dataSuccess(Collections.emptyList());
    }
}
