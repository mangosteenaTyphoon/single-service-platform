package com.shanzhu.platform.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanzhu.platform.constant.NewsConst;
import com.shanzhu.platform.entity.Content;
import com.shanzhu.platform.entity.News;
import com.qy.news.entity.dto.*;
import com.shanzhu.platform.entity.dto.*;
import com.shanzhu.platform.entity.vo.NewsInfoVo;
import com.shanzhu.platform.mapper.NewsMapper;
import com.shanzhu.platform.result.R;
import com.shanzhu.platform.service.ContentService;
import com.shanzhu.platform.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qy
 * @since 2023-03-14
 */
@Slf4j
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private ContentService contentService;


    /*
     * @Author 山竹
     * @Description //TODO 新增新闻
     * @Date 10:21 2023/3/20
     * @Param : news
     * @return boolean
     **/
    @Override
    public boolean saveNews(NewsContentReqDTO news) {
        if ( StrUtil.isEmpty(news.getTitle()) || StrUtil.isEmpty(news.getCover())
                || StrUtil.isEmpty(news.getSummary()) || StrUtil.isEmpty(news.getNewsContent())
                || StrUtil.isEmpty(news.getUserId()) ) return false;
        String newsId = IdUtil.simpleUUID();
        news.setStatus(NewsConst.NEWS_NO_PUBLIC);
        news.setIsBanner(NewsConst.NEWS_NO_BANNER);
        news.setId(newsId);
        news.setGoods(0);

        if ( 1 == newsMapper.insert(news) ) {
            String contentId = IdUtil.simpleUUID();
            Content content = new Content();
            content.setId(contentId);
            content.setNewsId(newsId);
            content.setNewsContent(news.getNewsContent());
            return contentService.save(content);
        }
        return false;
    }

    /*
     * @Author 山竹
     * @Description //TODO 修改状态
     * @Date 11:01 2023/3/20
     * @Param : reqDTO
     * @return boolean
     **/
    @Override
    public boolean changeStatus(NewsStatusReqDTO reqDTO) {
        if ( StrUtil.isEmpty(reqDTO.getId()) ) return false;
        News news = new News();
        news.setId(reqDTO.getId());
        news.setStatus(reqDTO.getStatus());
        return newsMapper.updateById(news) == 1;

    }

    /*
     * 这里对缓存做一下说明 先改变 再删除 改变即改变mysql 删除即删除redis
     * 查询 先查 redis 如果未击中 则查询数据库
     * 本系统只对 文章信息进行缓存 如果文章信息改变
     * 下面对以上流程做一个详细的说明
     * 在增加 文章信息的时候 先增加 然后删除 （这里文章信息将以文章id+用户id来进存储到redis中）
     * 查询第一遍的时候 先从数据库中查询 之后则冲redis中查询 如果文章信息有改变 则删除缓存
     */

    @Override
    public R queryNewsPageListByWord(NewsQueryReqDTO reqDTO) {
        List<NewsInfoVo> newsInfoVos = newsMapper.selectNewsPageListByWord(reqDTO);
        Integer total = newsMapper.selectNewsPageListByWordTotal(reqDTO);
        return R.ok().data("list", newsInfoVos).data("total", total);


    }

    /*
    获取文章根据id
     */
    @Override
    public NewsInfoVo getNewsById(NewsIdReqDTO reqDTO) {
        NewsInfoVo newsInfoVo = newsMapper.selectNewsById(reqDTO.getId());
        return newsInfoVo;
    }

    /*
    获取新闻列表通过用户id
     */
    @Override
    public R getNewsListByUserId(String userId, long current, long limit, Integer status) {
        return R.ok().data("list", newsMapper.selectNewsByPageListByUserId(userId, current, limit, status))
                .data("total", newsMapper.selectNewsByPageListByUserIdTotal(userId, current, limit, status));
    }

    /*
    修改新闻内容
     */
    @Override
    public boolean updateNewsInfoById(NewsInfoChange reqDTO) {
        String newsId = reqDTO.getId();
        if ( !StrUtil.isEmpty(newsId) ) {
            News newNews = new News();
            BeanUtils.copyProperties(reqDTO, newNews);
            Content newContent = new Content();
            return 1 == newsMapper.updateById(newNews);
        }
        log.error("修改新闻内容失败，请求参数：reqDTO:{}", reqDTO);
        return false;
    }


}
