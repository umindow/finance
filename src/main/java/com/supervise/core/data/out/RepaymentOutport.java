package com.supervise.core.data.out;

import com.supervise.config.role.DataType;
import com.supervise.controller.vo.DataSet;
import com.supervise.core.data.translate.GenericDataTranslate;
import com.supervise.dao.mysql.entity.RepaymentEntity;
import com.supervise.dao.mysql.entity.UserEntity;
import com.supervise.dao.mysql.mapper.RepaymentMapper;
import com.supervise.dao.mysql.middleDao.RepaymentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xishui.hb on 2018/3/5 上午10:10.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@Service
public class RepaymentOutport extends AbstractDataOutport{
    @Autowired
    private RepaymentMapper repaymentMapper;

    @Autowired
    private RepaymentDao  repaymentDao;
    @Override
    public DataSet dataSet(String date, DataType dataType, UserEntity userEntity) {
//        Example example = new Example(RepaymentEntity.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("batchDate",date);
       //  = repaymentMapper.selectByExample(example);

        List<RepaymentEntity>  repaymentEntities = repaymentDao.queryRepaymentFormMiddleDB(date);
        return  new GenericDataTranslate<RepaymentEntity>().translate(repaymentEntities,dataType.getDataLevel(),userEntity);
    }
}
