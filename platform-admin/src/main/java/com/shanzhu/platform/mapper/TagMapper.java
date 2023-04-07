package com.shanzhu.platform.mapper;

import com.shanzhu.platform.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shanzhu.platform.entity.dto.IdAndPageReqDTO;
import com.shanzhu.platform.entity.vo.NewsInfoVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qy
 * @since 2023-03-14
 */
@Repository
public interface TagMapper extends BaseMapper<Tag> {

    List<Tag> selectListByNewsId(@Param("id") String id);

    List<NewsInfoVo> listNewsByTagId(@Param("reqDTO") IdAndPageReqDTO reqDTO);

    Integer listNewsTotalByTagId(@Param("reqDTO") IdAndPageReqDTO reqDTO);
}
