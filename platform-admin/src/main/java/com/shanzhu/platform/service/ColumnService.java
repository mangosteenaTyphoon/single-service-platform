package com.shanzhu.platform.service;

import com.shanzhu.platform.entity.Column;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shanzhu.platform.entity.dto.ColumnInfoReqDTO;
import com.shanzhu.platform.entity.dto.IdAndPageReqDTO;
import com.shanzhu.platform.entity.dto.ColumnStatusReqDTO;
import com.shanzhu.platform.result.R;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qy
 * @since 2023-03-21
 */
public interface ColumnService extends IService<Column> {

    boolean saveColumn(Column reqDTO);

    boolean updateColumnStatus(ColumnStatusReqDTO reqDTO);

    R getNewsListByColumnId(IdAndPageReqDTO reqDTO);

    boolean updateColumn(ColumnInfoReqDTO reqDTO);
}
