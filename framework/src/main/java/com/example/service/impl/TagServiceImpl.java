package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dto.TagListDto;
import com.example.pojo.Tag;
import com.example.service.TagService;
import com.example.mapper.TagMapper;
import com.example.vo.PageVo;
import com.example.vo.ResponseResult;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
* @author 81486
* @description 针对表【sg_tag(标签)】的数据库操作Service实现
* @createDate 2023-10-30 13:04:49
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

    @Override
    public ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        LambdaQueryWrapper<Tag> wrapper=new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(tagListDto.getName()),Tag::getName,tagListDto.getName());
        wrapper.like(StringUtils.hasText(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());
        Page<Tag> page=new Page<>(pageNum,pageSize);

        page(page,wrapper);
        PageVo pageVo=new PageVo(page.getRecords(),page.getTotal());
        return ResponseResult.okResult(pageVo);
    }
}




