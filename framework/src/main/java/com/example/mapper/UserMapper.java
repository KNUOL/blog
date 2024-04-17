package com.example.mapper;

import com.example.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 81486
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2023-10-24 15:26:36
* @Entity com.example.pojo.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




