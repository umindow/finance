package com.supervise.core.data.out;

import com.supervise.config.role.DataType;
import com.supervise.controller.vo.DataSet;
import com.supervise.core.data.translate.GenericDataTranslate;
import com.supervise.dao.mysql.entity.RecourseEntity;
import com.supervise.dao.mysql.entity.RepaymentEntity;
import com.supervise.dao.mysql.entity.UserEntity;
import com.supervise.dao.mysql.mapper.RecourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by xishui.hb on 2018/3/5 上午10:09.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@Service
public class RecourseOutport extends AbstractDataOutport{
    @Autowired
    private RecourseMapper recourseMapper;
    @Override
    public DataSet dataSet(String date, DataType dataType,UserEntity userEntity) {
        Example example = new Example(RecourseEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("batchDate",date);
        List<RecourseEntity> entities = recourseMapper.selectByExample(example);
        return CollectionUtils.isEmpty(entities) ? null: new GenericDataTranslate<RecourseEntity>().translate(entities,dataType.getDataLevel(),userEntity);
    }
}
