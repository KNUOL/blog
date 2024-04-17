package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.dto.TagListDto;
import com.example.pojo.Tag;
import com.example.service.TagService;
import com.example.utils.BeanCopyUtils;
import com.example.vo.PageVo;
import com.example.vo.ResponseResult;
import com.example.vo.TagAdminVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/content/tag")
public class AdminTagController {
    @Autowired
    private TagService tagService;


    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto){

        return  tagService.pageTagList(pageNum, pageSize, tagListDto);
    }

    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        LambdaQueryWrapper<Tag> wrapper=new LambdaQueryWrapper<>();
        wrapper.select(Tag::getId,Tag::getName);
        List<Tag> list = tagService.list(wrapper);
        List<TagAdminVo> vos = BeanCopyUtils.copyBeanList(list, TagAdminVo.class);
        return ResponseResult.okResult(vos);
    }

}
