package com.shanzhu.platform.controller;


import com.shanzhu.platform.entity.Content;
import com.shanzhu.platform.entity.dto.NewsIdReqDTO;
import com.shanzhu.platform.result.R;
import com.shanzhu.platform.service.ContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qy
 * @since 2023-03-15
 */
@RestController
@RequestMapping("/content")
@Api(tags = {"新闻内容管理"}, description = "新闻的内容进行管理")
public class ContentController {

    @Autowired
    private ContentService contentService;


    /*
    * @Author 山竹
    * @Description //TODO 根据新闻id查找具体内容
    * @Date 14:48 2023/3/20
    * @Param : NewsIdReqDTO
    * @return R
    **/
    @ApiOperation("根据新闻id查找具体内容")
    @PostMapping("getContentByNewsId")
    public R getContentByNewsId(@RequestBody NewsIdReqDTO reqDTO) {
        return R.ok().data("content",contentService.getContentByNewsId(reqDTO));
    }

    /*
    * @Author 山竹
    * @Description //TODO 修改新闻内容
    * @Date 15:18 2023/3/20
    * @Param : Content
    * @return R
    **/
    @ApiOperation("修改新闻内容")
    @PostMapping("updateContent")
    public R updateContentByNewsId(@RequestBody Content content) {
        return contentService.updateContentByNewsId(content) ? R.ok() : R.error();
    }












}

