package com.shanzhu.platform.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanzhu.platform.constant.SubscribeConst;
import com.shanzhu.platform.entity.Subscribe;
import com.shanzhu.platform.entity.dto.IdAndPageReqDTO;
import com.shanzhu.platform.exceptionhandler.MyException;
import com.shanzhu.platform.mapper.SubscribeMapper;
import com.shanzhu.platform.result.R;
import com.shanzhu.platform.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qy
 * @since 2023-03-22
 */
@Service
public class SubscribeServiceImpl extends ServiceImpl<SubscribeMapper, Subscribe> implements SubscribeService {


    @Autowired
    private SubscribeMapper subscribeMapper;

    /*
    新增关注 如果已经有此关注消息 则不能进行关注
     */
    @Override
    public boolean saveSubscribe(Subscribe subscribe) {
        if ( isSubscribe(subscribe) ) throw new MyException(20001, "已经关注过咯");
        subscribe.setId(IdUtil.simpleUUID());
        return subscribeMapper.insert(subscribe) > 0;
    }

    /*
    判断是否已经关注
     */
    @Override
    public boolean isSubscribe(Subscribe subscribe) {
        return isSubscribed(subscribe);
    }

    /*
    取消关注
     */
    @Override
    public boolean deleteSubscribe(Subscribe subscribe) {
        if (! isSubscribed(subscribe) ) throw new MyException(20001,"该关注关系尚未成立");
        LambdaQueryWrapper<Subscribe> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Subscribe::getBeSubscribeId, subscribe.getBeSubscribeId());
        wrapper.eq(Subscribe::getSubscribeId, subscribe.getSubscribeId());
        wrapper.eq(Subscribe::getSubscribeType,subscribe.getSubscribeType());
        return subscribeMapper.delete(wrapper) > 0 ;
    }

    /*
        根据用户id查询所有关注栏目  栏目id
     */
    @Override
    public R getSubscribeColumnByUserId(IdAndPageReqDTO reqDTO) {
        return getR(reqDTO, SubscribeConst.SUBSCRIBE_TYPE_COLUMN);
    }



    /*
    根据id查找用户关注了多少栏目
     */
    @Override
    public Integer getSubscribeColumnCountByUserId(String userId) {
        if (StrUtil.isEmpty(userId))  throw new MyException(20001, "未输入有效数据");
        LambdaQueryWrapper<Subscribe> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Subscribe::getSubscribeId, userId);
        wrapper.eq(Subscribe::getSubscribeType, SubscribeConst.SUBSCRIBE_TYPE_COLUMN);
        return subscribeMapper.selectCount(wrapper);
    }

    @Override
    public R getSubscribeUserByUserId(IdAndPageReqDTO reqDTO) {
        return getR(reqDTO,SubscribeConst.SUBSCRIBE_TYPE_USER);

    }
    /*
    根据用户id获取当前用户关注的用户总数量
     */
    @Override
    public Integer getSubscribeUserCountByUserId(String userId) {
        if (StrUtil.isEmpty(userId))  throw new MyException(20001, "未输入有效数据");
        LambdaQueryWrapper<Subscribe> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Subscribe::getSubscribeId, userId);
        wrapper.eq(Subscribe::getSubscribeType, SubscribeConst.SUBSCRIBE_TYPE_USER);
        return subscribeMapper.selectCount(wrapper);
    }
    /*
    根据用户id获取粉丝id
     */
    @Override
    public R getFansByUserId(IdAndPageReqDTO reqDTO) {
        if (StrUtil.isEmpty(reqDTO.getId())) throw new MyException(20001,"未输入有效参数");
        LambdaQueryWrapper<Subscribe> wrapper = new LambdaQueryWrapper<>();
        Page<Subscribe> page = new Page<>(reqDTO.getCurrent(), reqDTO.getLimit());
        wrapper.eq(Subscribe::getBeSubscribeId, reqDTO.getId());
        wrapper.eq(Subscribe::getSubscribeType, SubscribeConst.SUBSCRIBE_TYPE_USER);
        subscribeMapper.selectPage(page,wrapper);
        //获取用户id集合
        List<Subscribe> records = page.getRecords();
        long total = page.getTotal();
        ArrayList<String> beUserIds = new ArrayList<>();
        for (Subscribe subscribe: records) {
            beUserIds.add(subscribe.getSubscribeId());
        }


        return R.ok().data("beUserIds",beUserIds).data("total",total);
    }

    @Override
    public Integer getFansCountByUserId(String userId) {
        if (StrUtil.isEmpty(userId))  throw new MyException(20001, "未输入有效参数");
        LambdaQueryWrapper<Subscribe> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Subscribe::getBeSubscribeId, userId);
        wrapper.eq(Subscribe::getSubscribeType, SubscribeConst.SUBSCRIBE_TYPE_USER);
        return subscribeMapper.selectCount(wrapper);
    }
    /*
    获取该栏目的粉丝数量
     */
    @Override
    public Integer getColumnFansCountByColumnId(IdAndPageReqDTO reqDTO) {
        if (StrUtil.isEmpty(reqDTO.getId()))  throw new MyException(20001, "未输入有效参数");
        LambdaQueryWrapper<Subscribe> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Subscribe::getBeSubscribeId, reqDTO.getId());
        wrapper.eq(Subscribe::getSubscribeType, SubscribeConst.SUBSCRIBE_TYPE_COLUMN);
        return subscribeMapper.selectCount(wrapper);
    }

    @Override
    public R getFansIdsByColumnId(IdAndPageReqDTO reqDTO) {
        if (StrUtil.isEmpty(reqDTO.getId()))
            throw new MyException(20001, "未输入有效参数");
        LambdaQueryWrapper<Subscribe> wrapper = new LambdaQueryWrapper<>();
        Page<Subscribe> page = new Page<>(reqDTO.getCurrent(), reqDTO.getLimit());
        wrapper.eq(Subscribe::getBeSubscribeId, reqDTO.getId());
        wrapper.eq(Subscribe::getSubscribeType, SubscribeConst.SUBSCRIBE_TYPE_COLUMN);
        subscribeMapper.selectPage(page,wrapper);
        List<Subscribe> subscribeList = page.getRecords();
        ArrayList<String> columnFansIds = new ArrayList<>();
        long total = page.getTotal();
        for (Subscribe subscribe: subscribeList) {
            columnFansIds.add(subscribe.getSubscribeId());
        }
        return R.ok().data("columnFansIds",columnFansIds).data("total",total);
    }


    /*
    是否已经关注  返回true 就是已经关注 false 是没有关注
     */
    private Boolean isSubscribed(Subscribe subscribe) {
        if ( StrUtil.isEmpty(subscribe.getBeSubscribeId()) || StrUtil.isEmpty(subscribe.getSubscribeId())
           || StrUtil.isEmpty(subscribe.getSubscribeType()) )   throw new MyException(20001, "未输入有效信息");

        LambdaQueryWrapper<Subscribe> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Subscribe::getSubscribeId, subscribe.getSubscribeId());
        wrapper.eq(Subscribe::getBeSubscribeId, subscribe.getBeSubscribeId());
        wrapper.eq(Subscribe::getSubscribeType,subscribe.getSubscribeType());
        return subscribeMapper.selectOne(wrapper) != null;
    }


    /*
    提取出来的公共方法
     */
    private R getR(IdAndPageReqDTO reqDTO,String subscribeType ) {
        if ( StrUtil.isEmpty(reqDTO.getId()) ) throw new MyException(20001,"未输入有效数据");
        LambdaQueryWrapper<Subscribe> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Subscribe::getSubscribeId, reqDTO.getId());
        wrapper.eq(Subscribe::getSubscribeType, subscribeType);
        Page<Subscribe> page = new Page<>(reqDTO.getCurrent(), reqDTO.getLimit());
         subscribeMapper.selectPage(page, wrapper);
        //获取全部beSubScribeId
        List<Subscribe> records = page.getRecords();
        long total = page.getTotal();
        List<String> Ids = new ArrayList<>();
        for (Subscribe subscribe: records) {
            Ids.add(subscribe.getBeSubscribeId());
        }
        if(subscribeType.equals("SUBSCRIBE_TYPE_COLUMN"))  return R.ok().data("ColumnIds", Ids).data("total", total);
         return R.ok().data("UserIds", Ids).data("total", total);
    }
}
