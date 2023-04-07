package com.shanzhu.platform.mapper;

import com.shanzhu.platform.entity.Column;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shanzhu.platform.entity.dto.IdAndPageReqDTO;
import com.shanzhu.platform.entity.vo.NewsInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qy
 * @since 2023-03-21
 */
@Mapper
public interface ColumnMapper extends BaseMapper<Column> {

    List<NewsInfoVo> selectNewsListByColumnId(@Param("reqDTO") IdAndPageReqDTO reqDTO);

    Integer selectNewsTotalByColumnId(@Param("reqDTO") IdAndPageReqDTO reqDTO);
}
