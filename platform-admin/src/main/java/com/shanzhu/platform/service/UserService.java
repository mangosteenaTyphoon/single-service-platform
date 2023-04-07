package com.shanzhu.platform.service;

import com.shanzhu.platform.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qy.news.entity.dto.*;
import com.shanzhu.platform.entity.dto.*;
import com.shanzhu.platform.result.R;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qy
 * @since 2023-03-10
 */
public interface UserService extends IService<User> {


    /**
     * 登录认证逻辑处理
     *
     * @param reqDTO
     * @return
     */
    Map<String, Object> loginHandle(UserAuthInfoReqDTO reqDTO);


    List<String> queryUserIdByRole(UserIdReqDTO reqDTO);

    /**
     * 注册处理逻辑
     *
     * @param reqDTO
     * @return
     */
    int saveUser(User reqDTO,String code);

    boolean updateUserStatus(UserStatusDTO reqDTO);

    R queryUserListByWordAndPage(UserQueryDTO reqDTO, Long current, Long limit);

    boolean deleteUser(UserIdReqDTO reqDTO);

    Map<String, Object> loginHandleByPhone(UserAuthInfoByPhoneReqDTO reqDTO);
}
