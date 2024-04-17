package com.example.mapper;

import com.example.pojo.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 81486
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Mapper
* @createDate 2023-10-27 13:35:02
* @Entity com.example.pojo.Menu
*/
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Long userId);

    List<Menu> selectAllRouterMenu();

    List<Menu> selectRouterMenuTreeByUserId(Long userId);
}




