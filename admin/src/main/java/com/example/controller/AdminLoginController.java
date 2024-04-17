package com.example.controller;

import com.example.enums.AppHttpCodeEnum;
import com.example.exception.SystemException;
import com.example.pojo.LoginUser;
import com.example.pojo.Menu;
import com.example.pojo.User;
import com.example.service.LoginService;
import com.example.service.MenuService;
import com.example.service.RoleService;
import com.example.utils.BeanCopyUtils;
import com.example.utils.RedisCache;
import com.example.utils.SecurityUtils;
import com.example.vo.AdminUserInfoVo;
import com.example.vo.ResponseResult;
import com.example.vo.RoutesVo;
import com.example.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminLoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RedisCache redisCache;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            //必须传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
       return loginService.login(user);
    }
    @GetMapping("/getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo(){
        //获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据用户id查询权限信息
       List<String>  perms= menuService.selectRoleByUserId(loginUser.getUser().getId());
        //根据用户id查询角色
       List<String> roleKeyList= roleService.selectRoleKeyByUserId(loginUser.getUser().getId());
       //获取用户信息
        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        //封装返回
        AdminUserInfoVo adminUserInfoVo=new AdminUserInfoVo(perms,roleKeyList,userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }

    @GetMapping("/getRouters")
    public ResponseResult<RoutesVo> getRouters(){
        Long userId=SecurityUtils.getUserId();
        //查询menu 结果是tree的形式
        List<Menu> menus=menuService.selectRouterMenuByUserId(userId);
        //封装数据返回
        return ResponseResult.okResult(new RoutesVo(menus));
    }

    @PostMapping("/user/logout")
    public ResponseResult logout(){
        //获取当前登录的用户id
        Long userId = SecurityUtils.getUserId();
        //删除redis中的数据
        redisCache.deleteObject("login:"+userId);
        return ResponseResult.okResult();
    }


}
