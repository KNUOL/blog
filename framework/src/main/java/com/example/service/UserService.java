package com.example.service;

import com.example.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.vo.ResponseResult;

/**
* @author 81486
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2023-10-24 15:26:37
*/
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult systemList(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status);
}
