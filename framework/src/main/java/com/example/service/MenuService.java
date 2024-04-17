package com.example.service;

import com.example.pojo.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 81486
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service
* @createDate 2023-10-27 13:35:02
*/
public interface MenuService extends IService<Menu> {

    List<String> selectRoleByUserId(Long id);

    List<Menu> selectRouterMenuByUserId(Long userId);
}
