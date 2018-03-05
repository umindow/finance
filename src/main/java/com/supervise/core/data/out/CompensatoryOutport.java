package com.supervise.core.data.out;

import com.supervise.config.role.DataType;
import com.supervise.controller.vo.DataSet;
import com.supervise.core.data.translate.GenericDataTranslate;
import com.supervise.dao.mysql.entity.CompensatoryEntity;
import com.supervise.dao.mysql.entity.FeeAndRefundEntity;
import com.supervise.dao.mysql.entity.UserEntity;
import com.supervise.dao.mysql.mapper.CompensatoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by xishui.hb on 2018/3/5 上午10:07.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@Service
public class CompensatoryOutport extends AbstractDataOutport{
    @Autowired
    private CompensatoryMapper compensatoryMapper;
    @Override
    public DataSet dataSet(String date, DataType dataType, UserEntity userEntity) {
        Example example = new Example(CompensatoryEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("batchDate",date);
        List<CompensatoryEntity> entities = compensatoryMapper.selectByExample(example);
        return CollectionUtils.isEmpty(entities) ? null: new GenericDataTranslate<CompensatoryEntity>().translate(entities,dataType.getDataLevel(),userEntity);

    }
}
