package com.example.mapper;

import com.example.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 81486
* @description 针对表【sys_role(角色信息表)】的数据库操作Mapper
* @createDate 2023-10-27 13:38:45
* @Entity com.example.pojo.Role
*/
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectKeyByUserId(Long userId);
}




