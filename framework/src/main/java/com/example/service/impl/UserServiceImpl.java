package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.enums.AppHttpCodeEnum;
import com.example.exception.SystemException;
import com.example.pojo.User;
import com.example.service.UserService;
import com.example.mapper.UserMapper;
import com.example.utils.BeanCopyUtils;
import com.example.utils.SecurityUtils;
import com.example.vo.PageVo;
import com.example.vo.ResponseResult;
import com.example.vo.UserInfoVo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author 81486
 * @description 针对表【sys_user(用户表)】的数据库操作Service实现
 * @createDate 2023-10-24 15:26:37
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Override
    public ResponseResult userInfo() {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //查信息
        User user = getById(userId);
        //封装称UserInfoVo
        UserInfoVo vo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }


    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult register(User user) {
        //对数据进行非空判断
        if (!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getPassword())) {
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        //对数据进行重复判断
        if (userNameExist(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (nikeNameExist(user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        if (emailExist(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }

        //对密码加密后存数据库
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        save(user);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult systemList(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(userName), User::getUserName, userName)
                .like(StringUtils.hasText(phonenumber), User::getPhonenumber, phonenumber)
                .eq(StringUtils.hasText(status), User::getStatus, status);
        Page<User> page = new Page<>(pageNum, pageSize);
        page(page,wrapper);
        PageVo pageVo=new PageVo(page.getRecords(),page.getTotal());
        return ResponseResult.okResult(pageVo);

    }


    private boolean emailExist(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        return count(queryWrapper) > 0;
    }

    private boolean nikeNameExist(String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName, nickName);
        return count(queryWrapper) > 0;
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, userName);
        return count(queryWrapper) > 0;
    }
}




