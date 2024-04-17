package com.example.service;

import com.example.dto.TagListDto;
import com.example.pojo.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.vo.PageVo;
import com.example.vo.ResponseResult;

/**
* @author 81486
* @description 针对表【sg_tag(标签)】的数据库操作Service
* @createDate 2023-10-30 13:04:49
*/
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);
}
