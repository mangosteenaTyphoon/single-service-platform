package com.shanzhu.platform.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanzhu.platform.entity.Tag;
import com.shanzhu.platform.entity.dto.NewsIdReqDTO;
import com.shanzhu.platform.entity.dto.IdAndPageReqDTO;
import com.shanzhu.platform.entity.vo.NewsInfoVo;
import com.shanzhu.platform.exceptionhandler.MyException;
import com.shanzhu.platform.mapper.TagMapper;
import com.shanzhu.platform.result.R;
import com.shanzhu.platform.service.TagService;
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
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private TagMapper tagMapper;
    /*
        新增标签 如果已经有了一样的标签则不能进行添加
     */
    @Override
    public boolean saveTag(Tag tag) {
        if ( StrUtil.isEmpty(tag.getTagName()) ) throw new MyException(20001, "未输入有效数据");
        Tag tag1 = tagMapper.selectOne(new QueryWrapper<Tag>().eq("tag_name", tag.getTagName()));
        if ( tag1 != null ) throw new MyException(20001, "已经存在相同的标签");
        tag.setId(IdUtil.simpleUUID());
        return tagMapper.insert(tag) > 0;

    }
    /*
    不能修改相同名称的标签
     */
    @Override
    public boolean updateTag(Tag tag) {
        if ( StrUtil.isEmpty(tag.getId()) || StrUtil.isEmpty(tag.getTagName()))
            throw new MyException(20001, "未输入有效数据");
        Tag newTag = tagMapper.selectOne(new QueryWrapper<Tag>().eq("tag_name", tag.getTagName()));
        if (newTag != null && !newTag.getId().equals(tag.getId()))
            throw new MyException(20001, "已经存在相同的标签");
        return tagMapper.updateById(tag) > 0;
    }
    /*
    根据新闻id获取新闻标签
     */
    @Override
    public List<Tag> listTagByNewsId(NewsIdReqDTO reqDTO) {
        if(StrUtil.isEmpty(reqDTO.getId())) throw new MyException(20001,"未输入有效数据");
        List<Tag> tags = tagMapper.selectListByNewsId(reqDTO.getId());
        return tags;
    }

    @Override
    public R listNewsByTagId(IdAndPageReqDTO reqDTO) {
        if (StrUtil.isEmpty(reqDTO.getId()))  throw new MyException(20001, "未输入有效数据");
        List<NewsInfoVo> newsInfoVos = tagMapper.listNewsByTagId(reqDTO);
        Integer total = tagMapper.listNewsTotalByTagId(reqDTO);
        return R.ok().data("list",newsInfoVos).data("total",total);
    }
}
