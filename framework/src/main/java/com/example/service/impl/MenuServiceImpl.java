package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.constants.SystemConstants;
import com.example.pojo.Menu;
import com.example.service.MenuService;
import com.example.mapper.MenuMapper;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author 81486
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service实现
* @createDate 2023-10-27 13:35:02
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{

    @Override
    public List<String> selectRoleByUserId(Long id) {
        //如果是管理员，返回所有权限
//        if (SecurityUtils.isAdmin()){
            LambdaQueryWrapper<Menu> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.in(Menu::getMenuType, SystemConstants.MENU, SystemConstants.BUTTON);
            queryWrapper.eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
            List<Menu> menus = list(queryWrapper);
            List<String> perms = menus.stream().map(Menu::getPerms).collect(Collectors.toList());
            return perms;
       // }
        //否则返回所具有的权限

    }

    @Override
    public List<Menu> selectRouterMenuByUserId(Long userId) {
        List<Menu>menus=null;
//        if (SecurityUtils.isAdmin()){
//             menus=baseMapper.selectAllRouterMenu();
//        }
//        else{
//            menus= baseMapper.selectRouterMenuTreeByUserId(userId);
//        }
        //构建tree
        List<Menu> menuTree=buildMenuTree(menus,0L);
        return menuTree;
    }

    private List<Menu> buildMenuTree(List<Menu> menus, Long parentId) {
        List<Menu> menuTree = menus.stream().filter(menu -> menu.getParentId().equals(parentId)).map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
        return menuTree;

    }

    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> childrenList = menus.stream().filter(m -> m.getParentId().equals(menu.getId()))
                .map(m -> m.setChildren(getChildren(m, menus)))
                .collect(Collectors.toList());
        return childrenList;

    }
}




