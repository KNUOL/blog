package com.example.mapper;

import com.example.pojo.Link;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 81486
* @description 针对表【sg_link(友链)】的数据库操作Mapper
* @createDate 2023-10-24 15:14:25
* @Entity com.example.pojo.Link
*/
@Mapper
public interface LinkMapper extends BaseMapper<Link> {

}




