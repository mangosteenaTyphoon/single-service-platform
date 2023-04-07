package com.shanzhu.platform.service;

import com.shanzhu.platform.entity.News;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qy.news.entity.dto.*;
import com.shanzhu.platform.entity.dto.*;
import com.shanzhu.platform.entity.vo.NewsInfoVo;
import com.shanzhu.platform.result.R;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qy
 * @since 2023-03-14
 */
public interface NewsService extends IService<News> {

    boolean saveNews(NewsContentReqDTO news);

    boolean changeStatus(NewsStatusReqDTO reqDTO);

    R queryNewsPageListByWord(NewsQueryReqDTO reqDTO);

    NewsInfoVo getNewsById(NewsIdReqDTO reqDTO);

    R getNewsListByUserId(String userId, long current, long limit ,Integer status);

    boolean updateNewsInfoById(NewsInfoChange reqDTO);
}
