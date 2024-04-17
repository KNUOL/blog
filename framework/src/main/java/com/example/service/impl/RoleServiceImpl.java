package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.constants.SystemConstants;
import com.example.pojo.Role;
import com.example.service.RoleService;
import com.example.mapper.RoleMapper;
import com.example.vo.PageVo;
import com.example.vo.ResponseResult;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 81486
* @description 针对表【sys_role(角色信息表)】的数据库操作Service实现
* @createDate 2023-10-27 13:38:45
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否是管理员
        if (id== 1L){
            List<String> roleKeys=new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        //否则查询角色

        return getBaseMapper().selectKeyByUserId(id);
    }

    @Override
    public ResponseResult listPage(Integer pageNum, Integer pageSize, String roleName, String status) {

        LambdaQueryWrapper<Role> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasText(status),Role::getStatus, status)
                .like(StringUtils.hasText(roleName),Role::getRoleName, roleName);
        Page<Role> page=new Page<>();
        page(page,wrapper);
        List<Role> collect = page.getRecords().stream().sorted(Comparator.comparing(Role::getRoleSort)).collect(Collectors.toList());
        PageVo pageVo=new PageVo(collect,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }
}




