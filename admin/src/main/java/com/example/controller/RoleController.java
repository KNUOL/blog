package com.example.controller;

import com.example.service.RoleService;
import com.example.service.UserService;
import com.example.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @GetMapping("/list")
    public ResponseResult list(Integer pageNum,Integer pageSize,String roleName,String status){
        return roleService.listPage(pageNum,pageSize,roleName,status);
    }
}
