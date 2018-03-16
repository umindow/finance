package com.supervise.core.data.out;

import com.supervise.config.role.DataType;
import com.supervise.controller.vo.DataSet;
import com.supervise.core.data.translate.GenericDataTranslate;
import com.supervise.dao.mysql.entity.FeeAndRefundEntity;
import com.supervise.dao.mysql.entity.UserEntity;
import com.supervise.dao.mysql.mapper.FeeAndRefundMapper;
import com.supervise.dao.mysql.middleDao.FeeAndRefundDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xishui.hb on 2018/3/5 上午10:08.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@Service
public class FeeAndRefundOutport extends AbstractDataOutport{
    @Autowired
    private FeeAndRefundMapper feeAndRefundMapper;

    @Autowired
    private FeeAndRefundDao feeAndRefundDao;
    @Override
    public DataSet dataSet(String date, DataType dataType, UserEntity userEntity) {
//        Example example = new Example(FeeAndRefundEntity.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("batchDate",date);
//        List<FeeAndRefundEntity> entities = feeAndRefundMapper.selectByExample(example);

        List<FeeAndRefundEntity>  entities = feeAndRefundDao.queryFeeAndRefundFormMiddleDB(date);
        return new GenericDataTranslate<FeeAndRefundEntity>().translate(entities,dataType.getDataLevel(),userEntity);
    }
}
