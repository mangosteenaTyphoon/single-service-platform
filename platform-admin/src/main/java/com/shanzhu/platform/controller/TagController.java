package com.shanzhu.platform.controller;


import com.shanzhu.platform.entity.Tag;
import com.shanzhu.platform.entity.dto.NewsIdReqDTO;
import com.shanzhu.platform.entity.dto.IdAndPageReqDTO;
import com.shanzhu.platform.result.R;
import com.shanzhu.platform.service.TagService;
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
 * @since 2023-03-14
 */
@RestController
@RequestMapping("/tag")
@Api(tags = {"标签管理"}, description = "标签的一系列业务")
public class TagController {

    @Autowired
    private TagService tagService;

    /*
    * @Author 山竹
    * @Description //TODO 新增标签
    * @Date 10:31 2023/3/21
    * @Param : Tag tag
    * @return R
    **/
    @ApiOperation(value = "新增标签", notes = "")
    @PostMapping("saveTag")
    public R save(@RequestBody Tag tag) {
        return tagService.saveTag(tag) ? R.ok(): R.error() ;
    }
    
    /*
    * @Author 山竹
    * @Description //TODO 修改标签
    * @Date 10:40 2023/3/21
    * @Param : Tag tag
    * @return R
    **/
    @ApiOperation(value = "修改标签", notes = "")
    @PostMapping("updateTag")
    public R update(@RequestBody Tag tag) {
        return tagService.updateTag(tag) ? R.ok(): R.error();
    }
    /*
    * @Author 山竹
    * @Description //TODO 获取全部的标签
    * @Date 10:57 2023/3/21
    * @Param : null
    * @return R
    **/
    @ApiOperation(value = "获取全部的标签", notes = "")
    @GetMapping("listTag")
    public R listTag() {
        return R.ok().data("list",tagService.list(null));
    }




  /*
  * @Author 山竹
  * @Description //TODO 根据新闻ID获取全部的新闻标签
  * @Date 10:57 2023/3/21
  * @Param : NewsIdReqDTO reqDTO
  * @return
  **/
    @ApiOperation(value = "根据新闻ID获取全部的新闻标签", notes = "")
    @PostMapping("listTagByNewsId")
    public R listTagByNewsId(@RequestBody NewsIdReqDTO reqDTO) {
        return R.ok().data("list", tagService.listTagByNewsId(reqDTO));
    }
    
    
    /*
    * @Author 山竹
    * @Description //TODO 根据标签Id获取全部的新闻
    * @Date 11:25 2023/3/21
    * @Param : TagIdReqDTO
    * @return R
    **/
    @ApiOperation(value = "根据标签Id获取全部的新闻", notes = "")
    @PostMapping("listNewsByTagId")
    public R listNewsByTagId(@RequestBody IdAndPageReqDTO reqDTO) {
        reqDTO.setCurrent(reqDTO.getCurrent() - 1);
        return tagService.listNewsByTagId(reqDTO);
    }




}

