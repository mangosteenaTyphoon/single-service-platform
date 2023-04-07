package com.shanzhu.platform.mapper;

import com.shanzhu.platform.entity.News;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shanzhu.platform.entity.dto.NewsQueryReqDTO;
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
public interface NewsMapper extends BaseMapper<News> {


    List<NewsInfoVo> selectNewsPageListByWord(@Param("reqDTO") NewsQueryReqDTO reqDTO);

    Integer selectNewsPageListByWordTotal(@Param("reqDTO")NewsQueryReqDTO reqDTO);

    NewsInfoVo selectNewsById(@Param("id") String id);

    List<NewsInfoVo> selectNewsByPageListByUserId(@Param("userId") String userId, @Param("current") long current
            , @Param("limit") long limit ,@Param("status") Integer status);

    Integer selectNewsByPageListByUserIdTotal(@Param("userId") String userId, @Param("current") long current
            , @Param("limit") long limit ,@Param("status") Integer status);
}
