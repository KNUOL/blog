package com.example.service;

import com.example.pojo.User;
import com.example.vo.ResponseResult;
import org.springframework.stereotype.Service;


public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
