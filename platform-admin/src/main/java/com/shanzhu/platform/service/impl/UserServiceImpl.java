package com.shanzhu.platform.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanzhu.platform.constant.AuthConst;
import com.shanzhu.platform.constant.UserConst;
import com.shanzhu.platform.entity.User;
import com.shanzhu.platform.entity.UserRole;
import com.qy.news.entity.dto.*;
import com.shanzhu.platform.entity.dto.*;
import com.shanzhu.platform.exceptionhandler.MyException;
import com.shanzhu.platform.mapper.UserMapper;
import com.shanzhu.platform.mapper.UserRoleMapper;
import com.shanzhu.platform.redis.service.RedisService;
import com.shanzhu.platform.result.R;
import com.shanzhu.platform.service.UserService;
import com.shanzhu.platform.utils.JbcryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qy
 * @since 2023-03-10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RedisService redisService;


    @Override
    public Map<String, Object> loginHandle(UserAuthInfoReqDTO reqDTO) {
        Map<String, Object> returnMap = new HashMap<>();
        Integer type = reqDTO.getType();
        String account = reqDTO.getAccount();
        String password = reqDTO.getPassword();
        User user = userMapper.selectUserAuthInfo(account, type);
        if ( user != null && !StrUtil.isEmptyIfStr(user.getId()) ) {
            if ( !JbcryptUtil.checkPwd(password, user.getPassword()) ) {
                returnMap.put(AuthConst.FLAG, AuthConst.FLAG_ONE_VAL);
            } else {
                returnMap.put(AuthConst.ID, user.getId());
                returnMap.put(AuthConst.USER, user);
                returnMap.put(AuthConst.FLAG, AuthConst.FLAG_TWO_VAL);
                returnMap.put("token", StpUtil.getTokenValue());
                System.out.println("token:" + StpUtil.getTokenValue());
            }
        } else {
            returnMap.put(AuthConst.FLAG, AuthConst.FLAG_ZERO_VAL);
        }

        return returnMap;
    }

    @Override
    public List<String> queryUserIdByRole(UserIdReqDTO reqDTO) {
        return userMapper.selectUserIdByRole(reqDTO.getId());
    }

    //    这里-1 代表注册失败 0代表有信息输入不全 1代表有账号、邮箱、手机号码重复 2 代表成功
    @Override
    public int saveUser(User user,String code) {
        if ( StrUtil.isEmpty(user.getUserName()) || StrUtil.isEmpty(user.getPhone())
                || StrUtil.isEmpty(user.getNickName()) || StrUtil.isEmpty(user.getEmail())
                || StrUtil.isEmpty(user.getPassword()) ) throw new MyException(20001,"用户信息不全");
        if ( checkUserByUserNameAndEmailAndPhone(user) ) throw new MyException(20001,"账号或者手机号码或邮箱重复");
        String codeCatch = redisService.getCacheObject(user.getPhone() + "REGISTER");
        if(!codeCatch.equals(code)) throw new MyException(20001,"验证码不正确");
        user.setPassword(JbcryptUtil.bcryptPwd(user.getPassword()));
        user.setId(IdUtil.simpleUUID());
        user.setStatus(UserConst.USER_STATUS_NORMAL);
        if(1!=userMapper.insert(user)) throw new MyException(20001,"出现异常");
        //用戶在注册后自动绑定用户身份

        UserRole userRole = new UserRole();
        userRole.setRoleId("1f8c72aa97b249b39c005f6efc7419b4"); //这里注意 用户的角色id在数据库不能更改
        userRole.setUserId(user.getId());
        return userRoleMapper.insert(userRole);

    }

    @Override
    public boolean updateUserStatus(UserStatusDTO reqDTO) {
        if ( reqDTO.getId() != null ) {
            User user = new User();
            user.setId(reqDTO.getId());
            user.setStatus(reqDTO.getStatus());
            return userMapper.updateById(user) == 1;
        }
        return false;
    }

    @Override
    public R queryUserListByWordAndPage(UserQueryDTO reqDTO, Long current, Long limit) {
        //防止为空
        if ( current == null ) current = 1L;
        if ( limit == null ) limit = 10L;
        Page<User> userPage = new Page<>(current, limit);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if ( !StrUtil.isEmpty(reqDTO.getUserName()) ) {
            wrapper.like("user_name", reqDTO.getUserName());
        }
        if ( !StrUtil.isEmpty(reqDTO.getNickName()) ) {
            wrapper.like("nick_name", reqDTO.getNickName());
        }
        if ( !StrUtil.isEmpty(reqDTO.getStartDate()) ) {
            wrapper.ge("gmt_create", reqDTO.getStartDate());
        }
        if ( !StrUtil.isEmpty(reqDTO.getEndDate()) ) {
            wrapper.le("gmt_create", reqDTO.getEndDate());
        }
        //这里拼接上 不等于删除用户
        wrapper.ne("status", UserConst.USER_STATUS_DEL);
        wrapper.orderByDesc("gmt_create");
        //调用方法
        userMapper.selectPage(userPage, wrapper);
        List<User> userList = userPage.getRecords();
        if ( userList != null && userList.size() > 0 ) {
            for (User user : userList) {
                user.setPassword(null);
            }
        }

        long total = userPage.getTotal();
        return R.ok().data("data", userList).data("total", total);

    }

    @Override
    public boolean deleteUser(UserIdReqDTO reqDTO) {
        if ( StrUtil.isEmpty(reqDTO.getId()) ) return false;
        User user = new User();
        user.setId(reqDTO.getId());
        user.setStatus(UserConst.USER_STATUS_DEL);
        return userMapper.updateById(user) == 1;
    }
    /*
    手机号码登录
     */
    @Override
    public Map<String, Object> loginHandleByPhone(UserAuthInfoByPhoneReqDTO reqDTO) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getPhone,reqDTO.getPhone()));
        if(user ==null) throw new MyException(20001,"该账户尚未注册");
        String code=redisService.getCacheObject(reqDTO.getPhone()+"LOGIN");
        if (!reqDTO.getCode().equals(code)) throw new MyException(20001,"登录失败");
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put(AuthConst.ID, user.getId());
        returnMap.put(AuthConst.USER, user);
        returnMap.put(AuthConst.FLAG, AuthConst.FLAG_TWO_VAL);
        returnMap.put("token", StpUtil.getTokenValue());
        System.out.println("token:" + StpUtil.getTokenValue());
        return returnMap;
    }


    /*
     * 根据用户账号、邮箱、电话进行查重
     */
    public boolean checkUserByUserNameAndEmailAndPhone(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", user.getUserName()).or().eq("email", user.getEmail()).or().eq("phone", user.getPhone());
        return userMapper.selectCount(wrapper) != 0;
    }
}
