package com.shanzhu.platform.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanzhu.platform.entity.Content;
import com.shanzhu.platform.entity.dto.NewsIdReqDTO;
import com.shanzhu.platform.exceptionhandler.MyException;
import com.shanzhu.platform.mapper.ContentMapper;
import com.shanzhu.platform.redis.service.RedisService;
import com.shanzhu.platform.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qy
 * @since 2023-03-15
 */
@Service
public class ContentServiceImpl extends ServiceImpl<ContentMapper, Content> implements ContentService {


    @Autowired
    private ContentMapper contentMapper;

    @Autowired
    private RedisService redisService;

    /*
    根据新闻id获取新闻内容  这里先从redis中拿 如果未击中 则从 mysql 中拿
     */
    @Override
    public Content getContentByNewsId(NewsIdReqDTO reqDTO) {
        if ( StrUtil.isEmpty(reqDTO.getId()) ) throw new MyException(20001, "未输入有效新闻ID");
        Content cacheObject = redisService.getCacheObject("content_" + reqDTO.getId());
        if ( cacheObject == null ) {
            Content content = contentMapper.selectOne(new LambdaQueryWrapper<Content>().eq(Content::getNewsId, reqDTO.getId()));
            //缓存文章信息30分钟
            redisService.setCacheObject("content_" + reqDTO.getId(), content, 30, TimeUnit.MINUTES);
            return content;
        }
        return cacheObject;
    }

    /*
    根据新闻id修改新闻内容
     */
    @Override
    public boolean updateContentByNewsId(Content content) {
        if ( content == null && StrUtil.isEmpty(content.getId()) ) throw new MyException(20001, "未输入有效值");
        int update = contentMapper.update(content, new LambdaQueryWrapper<Content>().eq(Content::getNewsId, content.getNewsId()));
        redisService.deleteObject("content_" + content.getNewsId());
        return update > 0;
    }
}
