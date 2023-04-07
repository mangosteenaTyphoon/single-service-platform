package com.shanzhu.platform.service;

import com.shanzhu.platform.entity.Subscribe;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shanzhu.platform.entity.dto.IdAndPageReqDTO;
import com.shanzhu.platform.result.R;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qy
 * @since 2023-03-22
 */
public interface SubscribeService extends IService<Subscribe> {

    boolean saveSubscribe(Subscribe subscribe);

    boolean isSubscribe(Subscribe subscribe);

    boolean deleteSubscribe(Subscribe subscribe);

    R getSubscribeColumnByUserId(IdAndPageReqDTO reqDTO);

    Integer getSubscribeColumnCountByUserId(String userId);

    R getSubscribeUserByUserId(IdAndPageReqDTO reqDTO);

    Integer getSubscribeUserCountByUserId(String userId);

    R getFansByUserId(IdAndPageReqDTO reqDTO);

    Integer getFansCountByUserId(String userId);

    Integer getColumnFansCountByColumnId(IdAndPageReqDTO reqDTO);

    R getFansIdsByColumnId(IdAndPageReqDTO reqDTO);
}
