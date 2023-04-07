package com.shanzhu.platform.service;

import com.shanzhu.platform.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shanzhu.platform.entity.dto.NewsIdReqDTO;
import com.shanzhu.platform.entity.dto.IdAndPageReqDTO;
import com.shanzhu.platform.result.R;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qy
 * @since 2023-03-14
 */
public interface TagService extends IService<Tag> {

    boolean saveTag(Tag tag);

    boolean updateTag(Tag tag);

    List<Tag> listTagByNewsId(NewsIdReqDTO reqDTO);

    R listNewsByTagId(IdAndPageReqDTO reqDTO);
}
