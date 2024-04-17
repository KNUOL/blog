package com.example.filter;

import com.alibaba.fastjson.JSON;
import com.example.enums.AppHttpCodeEnum;
import com.example.pojo.LoginUser;
import com.example.utils.JwtUtil;
import com.example.utils.RedisCache;
import com.example.utils.WebUtils;
import com.example.vo.ResponseResult;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取请求头中的token

        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)){
            //说明接口不需要登录，直接放行
            filterChain.doFilter(request,response);
            return;
        }
        //解析获取userid
        Claims claims=null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            //token超时  不合法token
            //告诉前端需要重新登录
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        String userId = claims.getSubject();

        //从redis中获取用户信息
        LoginUser loginUser =redisCache.getCacheObject("login:" + userId);


        if (Objects.isNull(loginUser)){
            //登录过期
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        //存入上下文
        UsernamePasswordAuthenticationToken authenticationToken= UsernamePasswordAuthenticationToken.authenticated(loginUser,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request,response);
    }
}
