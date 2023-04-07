package com.shanzhu.platform.service;

import com.shanzhu.platform.entity.Content;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shanzhu.platform.entity.dto.NewsIdReqDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qy
 * @since 2023-03-15
 */
public interface ContentService extends IService<Content> {

    Content getContentByNewsId(NewsIdReqDTO reqDTO);

    boolean updateContentByNewsId(Content content);
}
