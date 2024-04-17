package com.example.service;

import com.example.pojo.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.vo.ResponseResult;

import java.util.List;

/**
* @author 81486
* @description 针对表【sys_role(角色信息表)】的数据库操作Service
* @createDate 2023-10-27 13:38:45
*/
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult listPage(Integer pageNum, Integer pageSize, String roleName, String status);
}
