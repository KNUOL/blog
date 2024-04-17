package com.example.service.impl;

import com.alibaba.fastjson.JSON;

import com.example.pojo.LoginUser;
import com.example.pojo.User;
import com.example.service.BlogLoginService;
import com.example.utils.BeanCopyUtils;
import com.example.utils.JwtUtil;

import com.example.utils.RedisCache;
import com.example.vo.BlogUserLoginVo;
import com.example.vo.ResponseResult;
import com.example.vo.UserInfoVo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {

    @Resource
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken= UsernamePasswordAuthenticationToken.unauthenticated(user.getUserName(),user.getPassword());

        Authentication authenticate = authenticationManager.authenticate(authenticationToken);//自动调用userdetailservice
        //判断是否认证通过
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }


        //获取userid生成token
        LoginUser loginUser= (LoginUser) authenticate.getPrincipal();

        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //吧用户信息存储redis
        redisCache.setCacheObject("bloglogin:"+userId,loginUser);

        //把token和user info返回
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo vo=new BlogUserLoginVo(jwt,userInfoVo);

        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult logout() {
        //获取token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //获取userId
        Long userId=loginUser.getUser().getId();
        //删除redis中用户信息
        redisCache.deleteObject("bloglogin:"+userId);

        return ResponseResult.okResult();
    }
}
