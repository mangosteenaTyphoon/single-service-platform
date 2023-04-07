package com.shanzhu.platform.controller;


import com.qy.news.entity.dto.*;
import com.shanzhu.platform.entity.dto.*;
import com.shanzhu.platform.result.R;
import com.shanzhu.platform.service.NewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author qy
 * @since 2023-03-14
 */
@RestController
@RequestMapping("/news")
@Api(tags = {"新闻管理"}, description = "新闻管理的各种接口")
public class NewsController {

    @Autowired
    private NewsService newsService;

    /*
     * @Author 山竹
     * @Description //TODO saveNews
     * @Date 11:08 2023/3/14
     * @Param : NewsContentReqDTO reqDTO
     * @return R
     **/
    @ApiOperation("新建新闻")
    @PostMapping("saveNews")
    public R saveNews(@RequestBody NewsContentReqDTO reqDTO) {
        return newsService.saveNews(reqDTO) ? R.ok() : R.error();
    }


    /*
     * @Author 山竹
     * @Description //TODO 改变新闻状态
     * @Date 11:51 2023/3/14
     * @Param : reqDTO
     * @return R
     **/
    @ApiOperation("改变新闻状态")
    @PostMapping("changeStatus")
    public R changeStatus(@RequestBody NewsStatusReqDTO reqDTO) {
        return newsService.changeStatus(reqDTO) ? R.ok() : R.error();
    }

    /*
     * @Author 山竹
     * @Description //TODO 根据关键字查询新闻并分页
     * @Date 9:08 2023/3/15
     * @Param : NewsQueryReqDTO
     * @return R
     **/
    @ApiOperation("根据关键字查询新闻并分页")
    @PostMapping("queryNewsPageListByWord")
    public R queryNewsPageList(@RequestBody NewsQueryReqDTO reqDTO) {
        reqDTO.setCurrent(reqDTO.getCurrent() - 1);
        return newsService.queryNewsPageListByWord(reqDTO);
    }


    /*
     * @Author 山竹
     * @Description //TODO 根据新闻id获取新闻
     * @Date 15:56 2023/3/16
     * @Param : NewsIdReqDTO
     * @return R
     **/
    @ApiOperation("根据新闻id获取新闻")
    @PostMapping("getNewsById")
    public R getNewsById(@RequestBody NewsIdReqDTO reqDTO) {
        return R.ok().data("news", newsService.getNewsById(reqDTO));
    }


    /*
     * @Author 山竹
     * @Description //TODO 根据用户id获取新闻列表并分页
     * @Date 16:48 2023/3/16
     * @Param
     * @return
     **/
    @ApiOperation("根据用户id获取新闻列表并分页")
    @GetMapping("getNewsListByUserId/{userId}/{current}/{limit}/{status}")
    public R getNewsListByUserId(@PathVariable String userId, @PathVariable long current,
                                 @PathVariable long limit, @PathVariable Integer status) {
        return newsService.getNewsListByUserId(userId, current - 1, limit, status);
    }


    /*
    * @Author 山竹
    * @Description //TODO 修改文章信息
    * @Date 11:36 2023/3/20
    * @Param : NewsInfoChange reqDTO
    * @return R
    **/
    @ApiOperation("修改文章信息")
    @PostMapping("saveNewsInfo")
    public R saveNewsInfo(@RequestBody NewsInfoChange reqDTO) {
        return newsService.updateNewsInfoById(reqDTO) ? R.ok(): R.error();
    }



}

