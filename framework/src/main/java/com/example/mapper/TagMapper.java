package com.example.mapper;

import com.example.pojo.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 81486
* @description 针对表【sg_tag(标签)】的数据库操作Mapper
* @createDate 2023-10-30 13:04:49
* @Entity com.example.pojo.Tag
*/
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

}




