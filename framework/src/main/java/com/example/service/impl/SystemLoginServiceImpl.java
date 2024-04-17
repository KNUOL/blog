package com.example.service.impl;

import com.example.pojo.LoginUser;
import com.example.pojo.User;
import com.example.service.BlogLoginService;
import com.example.service.LoginService;
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
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class SystemLoginServiceImpl implements LoginService {

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
        redisCache.setCacheObject("login:"+userId,loginUser);

        //把token和user info返回
        Map<String,String>map=new HashMap<>();
        map.put("token",jwt);

        return ResponseResult.okResult(map);
    }

}
