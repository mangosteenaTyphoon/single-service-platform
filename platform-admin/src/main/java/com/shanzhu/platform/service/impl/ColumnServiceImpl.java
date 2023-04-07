package com.shanzhu.platform.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.shanzhu.platform.constant.ColumnConst;
import com.shanzhu.platform.entity.Column;
import com.shanzhu.platform.entity.dto.ColumnInfoReqDTO;
import com.shanzhu.platform.entity.dto.IdAndPageReqDTO;
import com.shanzhu.platform.entity.dto.ColumnStatusReqDTO;
import com.shanzhu.platform.entity.vo.NewsInfoVo;
import com.shanzhu.platform.exceptionhandler.MyException;
import com.shanzhu.platform.mapper.ColumnMapper;
import com.shanzhu.platform.result.R;
import com.shanzhu.platform.service.ColumnService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qy
 * @since 2023-03-21
 */
@Service
public class ColumnServiceImpl extends ServiceImpl<ColumnMapper, Column> implements ColumnService {

    @Autowired
    private ColumnMapper columnMapper;

    /*
    新增分栏
     */
    @Override
    public boolean saveColumn(Column reqDTO)
    {
       if( StrUtil.isEmpty(reqDTO.getColumnName()) || StrUtil.isEmpty(reqDTO.getColumnDescribe())
          || StrUtil.isEmpty(reqDTO.getUserId())) throw new MyException(20001,"未输入有效信息");
       reqDTO.setId(IdUtil.simpleUUID());
       reqDTO.setStatus(ColumnConst.COLUMN_ENABLE);
       return columnMapper.insert(reqDTO) > 0;
    }
    /*
    修改分栏状态
     */
    @Override
    public boolean updateColumnStatus(ColumnStatusReqDTO reqDTO) {
        if (StrUtil.isEmpty(reqDTO.getId()) || StrUtil.isEmpty(reqDTO.getStatus().toString()))
            throw new MyException(20001, "未输入有效信息");
        Column column = new Column();
        BeanUtils.copyProperties(reqDTO,column);
        return columnMapper.updateById(column) > 0;
    }

    /*
    根据分栏id获取新闻并分页
     */
    @Override
    public R getNewsListByColumnId(IdAndPageReqDTO reqDTO) {
        List<NewsInfoVo> newsInfoVos = columnMapper.selectNewsListByColumnId(reqDTO);
        Integer total = columnMapper.selectNewsTotalByColumnId(reqDTO);
        return R.ok().data("columns",newsInfoVos).data("total",total);
    }

    @Override
    public boolean updateColumn(ColumnInfoReqDTO reqDTO) {
        if(StrUtil.isEmpty(reqDTO.getId())) throw new MyException(20001,"请输入有效信息");
        Column column = new Column();
        BeanUtils.copyProperties(reqDTO, column);
        return columnMapper.updateById(column) > 0;

    }
}
