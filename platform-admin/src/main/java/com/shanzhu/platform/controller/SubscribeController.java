package com.shanzhu.platform.controller;


import com.shanzhu.platform.entity.Subscribe;
import com.shanzhu.platform.entity.dto.IdAndPageReqDTO;
import com.shanzhu.platform.result.R;
import com.shanzhu.platform.service.SubscribeService;
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
 * @since 2023-03-22
 */
@RestController
@RequestMapping("/subscribe")
@Api(tags = {"关注关系管理"}, description = "用户关注 栏目关注")
public class SubscribeController {

    @Autowired
    private SubscribeService subscribeService;

    /*
    * @Author 山竹
    * @Description //TODO 新增关注
    * @Date 9:58 2023/3/22
    * @Param : Subscribe
    * @return R
    **/
    @PostMapping("/saveSubscribe")
    @ApiOperation(value = "新增关注", notes = "新增关注")
    public R saveSubscribe(@RequestBody Subscribe subscribe) {
        return subscribeService.saveSubscribe(subscribe)?R.ok() : R.error();
    }

    /*
    * @Author 山竹
    * @Description //TODO 判断是否已经关注
    * @Date 10:21 2023/3/22
    * @Param : Subscribe
    * @return R
    **/
    @PostMapping("/isSubscribe")
    @ApiOperation(value = "判断是否已经关注", notes = "判断是否已经关注 返回true是关注了 false 未关注")
    public R isSubscribe(@RequestBody Subscribe subscribe) {
        return  R.ok().data("isSubscribe",subscribeService.isSubscribe(subscribe));
    }


    /*
    * @Author 山竹
    * @Description //TODO 取消关注
    * @Date 10:27 2023/3/22
    * @Param : Subscribe
    * @return R
    **/
    @PostMapping("/deleteSubscribe")
    @ApiOperation(value = "取消关注", notes = "取消关注")
    public R deleteSubscribe(@RequestBody Subscribe subscribe) {
        return subscribeService.deleteSubscribe(subscribe) ? R.ok(): R.error();
    }
    
    /*
    * @Author 山竹
    * @Description //TODO 根据用户id查询所有关注栏目  栏目id
    * @Date 10:34 2023/3/22
    * @Param : IdAndPageReqDTO
    * @return R
    **/
    @PostMapping("/getSubscribeColumnByUserId")
    @ApiOperation(value = "根据用户id查询所有关注栏目", notes = "查找该用户关注了多少栏目")
    public R getSubscribeColumnByUserId(@RequestBody IdAndPageReqDTO reqDTO) {
        return subscribeService.getSubscribeColumnByUserId(reqDTO);
    }

    /*
    * @Author 山竹
    * @Description //TODO 根据用户id获取所有关注栏目数量
    * @Date 10:57 2023/3/22
    * @Param : userId
    * @return R
    **/
    @GetMapping("/getSubscribeColumnCountByUserId/{userId}")
    @ApiOperation(value = "根据用户id获取所有关注栏目数量", notes = "查找这个用户关注的栏目数量")
    public R getSubscribeColumnCountByUserId(@PathVariable String userId ) {
        return R.ok().data("SubscribeColumnCount", subscribeService.getSubscribeColumnCountByUserId(userId));
    }

    /*
    * @Author 山竹
    * @Description //TODO 根据用户id获取所有关注用户
    * @Date 10:57 2023/3/22
    * @Param : IdAndPageReqDTO
    * @return R
    **/
    @PostMapping("/getSubscribeUserByUserId")
    @ApiOperation(value = "根据用户id获取所有关注用户", notes = "查找这个用户关注的用户id")
    public R getSubscribeUserByUserId(@RequestBody IdAndPageReqDTO reqDTO) {
        return subscribeService.getSubscribeUserByUserId(reqDTO);
    }

    /*
    * @Author 山竹
    * @Description //TODO 根据用户id获取所有关注用户数量
    * @Date 10:58 2023/3/22
    * @Param : userId
    * @return
    **/
    @GetMapping("/getSubscribeUserCountByUserId/{userId}")
    @ApiOperation(value = "根据用户id获取次用户关注其他用户数量", notes = "查找这个用户关注的用户数量")
    public R getSubscribeUserCountByUserId(@PathVariable String userId) {
        return R.ok().data("SubscribeUserCount", subscribeService.getSubscribeUserCountByUserId(userId));
    }

    /*
    * @Author 山竹
    * @Description //TODO 根据用户id获取所有关注此用户的用户数量
    * @Date 10:58 2023/3/22
    * @Param : userId
    * @return
    **/
    @GetMapping("/getFansCountByUserId/{userId}")
    @ApiOperation(value = "根据用户id获取所有关注此用户的用户数量", notes = "获取粉丝数量")
    public R getFansCountByUserId(@PathVariable String userId) {
        return R.ok().data("fansCount", subscribeService.getFansCountByUserId(userId));
    }


    /*
     * @Author 山竹
     * @Description //TODO 根据用户id获取关注此用户的用户
     * @Date 11:00 2023/3/22
     * @Param : IdAndPageReqDTO
     * @return R
     **/
    @PostMapping("/getFansByUserId")
    @ApiOperation(value = "根据用户id获取关注此用户的用户", notes = "获取该用户的粉丝")
    public R getFansByUserId(@RequestBody IdAndPageReqDTO reqDTO) {
        return subscribeService.getFansByUserId(reqDTO);
    }

    /*
    * @Author 山竹
    * @Description //TODO 根据栏目id获取所有关注数量
    * @Date 10:59 2023/3/22
    * @Param : IdAndPageReqDTO
    * @return
    **/
    @PostMapping("/getColumnFansCountByColumnId")
    @ApiOperation(value = "根据栏目id获取所有关注数量", notes = "获取该栏目的粉丝数量")
    public R getColumnFansCountByColumnId(@RequestBody IdAndPageReqDTO reqDTO) {
        return R.ok().data("columnFansCount",subscribeService.getColumnFansCountByColumnId(reqDTO));
    }


    /*
    * @Author 山竹
    * @Description //TODO 根据栏目id获取所有粉丝的id
    * @Date 10:59 2023/3/22
    * @Param : null
    * @return
    **/
    @PostMapping("/getFansIdsByColumnId")
    @ApiOperation(value = "根据栏目id获取所有粉丝的id", notes = "获取该栏目的粉丝的id")
    public R getFansIdsByColumnId(@RequestBody IdAndPageReqDTO reqDTO) {
        return subscribeService.getFansIdsByColumnId(reqDTO);
    }




}

