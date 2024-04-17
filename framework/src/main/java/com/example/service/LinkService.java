package com.example.service;

import com.example.pojo.Link;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.vo.ResponseResult;

/**
* @author 81486
* @description 针对表【sg_link(友链)】的数据库操作Service
* @createDate 2023-10-24 15:14:25
*/
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    ResponseResult SystemList(Integer pageNum, Integer pageSize, String name, String status);
}
