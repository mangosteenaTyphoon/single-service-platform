package com.shanzhu.platform.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.shanzhu.platform.constant.AuthConst;
import com.shanzhu.platform.entity.User;
import com.shanzhu.platform.entity.dto.UserAuthInfoByPhoneReqDTO;
import com.shanzhu.platform.entity.dto.UserAuthInfoReqDTO;
import com.shanzhu.platform.entity.dto.UserIdReqDTO;
import com.shanzhu.platform.result.R;
import com.shanzhu.platform.result.ResultCode;
import com.shanzhu.platform.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("auth")
@Api(tags = {"认证管理"}, description = "拥有认证、鉴权等")
public class AuthController {

    @Autowired
    private UserService userService;


    /*
     * @Author 山竹
     * @Description //TODO 用户登录
     * @Date 21:37 2023/3/12
     * @Param : reqDTO
     * @return result.com.shanzhu.platform.R
     **/
    @PostMapping("/loginByAccount")
    @ApiOperation("使用账号登录")
    public R login(@RequestBody UserAuthInfoReqDTO reqDTO) {
        if ( StrUtil.isEmpty(reqDTO.getAccount()) || StrUtil.isEmpty(reqDTO.getPassword()) || reqDTO.getType() == null ) {
            return R.error().code(ResultCode.ILLEGAL_PARAMETER_ERROR.getCode()).message(ResultCode.ILLEGAL_PARAMETER_ERROR.getMsg());
        }
        Map<String, Object> resultMap = userService.loginHandle(reqDTO);
        String flag = resultMap.get(AuthConst.FLAG).toString();
        if ( AuthConst.FLAG_ZERO_VAL.equals(flag) ) {
            return R.error().code(ResultCode.USER_NO_EXIST_ERROR.getCode()).message(ResultCode.USER_NO_EXIST_ERROR.getMsg());
        }
        if ( AuthConst.FLAG_ONE_VAL.equals(flag) ) {
            return R.error().code(ResultCode.USER_OR_PASSWD_ERROR.getCode()).message(ResultCode.USER_OR_PASSWD_ERROR.getMsg());
        }
        if ( AuthConst.FLAG_TWO_VAL.equals(flag) ) {
            String ID = resultMap.get(AuthConst.ID).toString();
            StpUtil.login(ID);
            return R.ok().message("登录成功").data(resultMap);
        }
        return R.ok().message("登录成功");

    }

    /*
     * @Author 山竹
     * @Description //TODO 手机号码登录
     * @Date 15:42 2023/3/23
     * @Param : null
     * @return
     **/
    @PostMapping("/loginByPhone")
    @ApiOperation("使用手机号码登录")
    public R phoneLogin(@RequestBody UserAuthInfoByPhoneReqDTO reqDTO) {
        if ( StrUtil.isEmpty(reqDTO.getPhone()) || StrUtil.isEmpty(reqDTO.getCode()) ) {
            return R.error().code(ResultCode.ILLEGAL_PARAMETER_ERROR.getCode()).message(ResultCode.ILLEGAL_PARAMETER_ERROR.getMsg());
        }
        Map<String, Object> resultMap = userService.loginHandleByPhone(reqDTO);
        String ID = resultMap.get(AuthConst.ID).toString();
        StpUtil.login(ID);
        return R.ok().message("登录成功").data(resultMap);
    }

    /**
     * @return
     * @Author 山竹
     * @Description //TODO SaveOrUpdateUser
     * @Date 15:25 2023/3/13
     * @Param : SysUser reqDTO
     **/
    @PostMapping("save/{code}")
    @ApiOperation("用户注册")
    public R SaveOrUpdateUser(@RequestBody User reqDTO, @PathVariable String code) {
        return 1 == userService.saveUser(reqDTO, code) ? R.ok() : R.error();
    }

    /*
     * @Author 山竹
     * @Description //TODO 登录状态
     * @Date 10:18 2023/3/13
     * @Param : null
     * @return
     **/
    @PostMapping("/isLogin")
    @ApiOperation("登录状态")
    @SaCheckLogin
    public R isLogin() {
        return R.ok().data("isLogin", StpUtil.isLogin());
    }

    /*
     * @Author 山竹
     * @Description //TODO 获取token信息
     * @Date 10:31 2023/3/13
     * @Param : null
     * @return
     **/
    @GetMapping("/getTokenInfo")
    @ApiOperation("获取token信息")
    public R getTokenInfo() {
        return R.ok().data("token", StpUtil.getTokenInfo());
    }

    /*
     * @Author 山竹
     * @Description //TODO 退出
     * @Date 10:33 2023/3/13
     * @Param : reqDTO
     * @return
     **/
    @PostMapping("/logout")
    @ApiOperation("退出")
    public R logout(@RequestBody UserIdReqDTO reqDTO) {
        StpUtil.logout(reqDTO.getId());
        return R.ok().message("注销成功~");
    }

    /*
     * @Author 山竹
     * @Description //TODO 获取用户对应的角色
     * @Date 10:37 2023/3/13
     * @Param : UserIdReqDTO reqDTO
     * @return
     **/
    @PostMapping("/getUserRole")
    @ApiOperation("获取用户对应的角色")
    public R getUserRole(@RequestBody UserIdReqDTO reqDTO) {
        return R.ok().data("userUserVo", userService.queryUserIdByRole(reqDTO));
    }









}



