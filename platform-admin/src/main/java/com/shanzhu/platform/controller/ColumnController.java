package com.shanzhu.platform.controller;


import com.shanzhu.platform.entity.Column;
import com.shanzhu.platform.entity.dto.ColumnInfoReqDTO;
import com.shanzhu.platform.entity.dto.IdAndPageReqDTO;
import com.shanzhu.platform.entity.dto.ColumnStatusReqDTO;
import com.shanzhu.platform.result.R;
import com.shanzhu.platform.service.ColumnService;
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
 * @since 2023-03-21
 */
@RestController
@RequestMapping("/column")
@Api(tags = {"栏目管理"}, description = "管理栏目相关")
public class ColumnController {

    @Autowired
    private ColumnService columnService;

    /*
    * @Author 山竹
    * @Description //TODO 新增一个分栏
    * @Date 15:39 2023/3/21
    * @Param : Column
    * @return R
    **/
    @PostMapping("/saveColumn")
    @ApiOperation(value = "新增一个分栏", notes = "新增一个分栏")
    public R saveColumn(@RequestBody Column reqDTO){
        return columnService.saveColumn(reqDTO) ? R.ok() :R.error();
    }


    /*
    * @Author 山竹
    * @Description //TODO 修改分栏状态
    * @Date 16:07 2023/30/21
    * @Param : ColumnStatusReqDTO
    * @return R
    **/
    @PostMapping("/updateColumnStatus")
    @ApiOperation(value = "修改分栏状态", notes = "修改分栏状态")
    public R updateStatus(@RequestBody ColumnStatusReqDTO reqDTO) {
        return columnService.updateColumnStatus(reqDTO) ? R.ok(): R.error();
    }

    /*
     * @Author 山竹
     * @Description //TODO 查询所有分栏
     * @Date 16:18 2023/3/21
     * @Param : null
     * @return R
     **/
    @ApiOperation("查询所有分栏")
    @GetMapping("getAllColumn")
    public R getAllColumn() {
        return R.ok().data("columns", columnService.list(null));
    }
    
    /*
    * @Author 山竹
    * @Description //TODO 查询分栏下的所有新闻
    * @Date 16:28 2023/3/21
    * @Param : IdAndPageReqDTO
    * @return R
    **/
    @ApiOperation("查询分栏下的所有新闻")
    @PostMapping("getArticleListByColumnId")
    public R getArticleList(@RequestBody IdAndPageReqDTO reqDTO) {
        reqDTO.setCurrent(reqDTO.getCurrent()-1);
        return columnService.getNewsListByColumnId(reqDTO);
    }
    
    /*
    * @Author 山竹
    * @Description //TODO 修改分栏内容
    * @Date 16:47 2023/3/21
    * @Param : ColumnInfoReqDTO
    * @return R
    **/
    @PostMapping("/updateColumn")
    @ApiOperation(value = "修改分栏内容", notes = "修改分栏内容")
    public R updateColumn(@RequestBody ColumnInfoReqDTO reqDTO) {
        return columnService.updateColumn(reqDTO) ? R.ok(): R.error();
    }




}

