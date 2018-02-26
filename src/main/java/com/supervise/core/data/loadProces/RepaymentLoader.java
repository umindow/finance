package com.supervise.core.data.loadProces;

import com.supervise.core.data.spi.GenericDataProcessorHandler;
import com.supervise.dao.mysql.entity.RepaymentEntity;
import com.supervise.dao.mysql.middleDao.RepaymentDao;
import com.supervise.dao.mysql.viewDao.ViewRepaymentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xishui.hb on 2018/1/31 上午10:09.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@Service
public class RepaymentLoader extends GenericDataProcessorHandler<List<RepaymentEntity>,String>{
    @Autowired
    private ViewRepaymentDao viewRepaymentDao;

    @Autowired
    private RepaymentDao repaymentDao;

    @Override
    public void afterData(List<RepaymentEntity> dataRes) {
        //插入数据库
        for(RepaymentEntity repaymentEntity : dataRes){
            repaymentDao.insertRepaymentToMiddleDB(repaymentEntity);
        }

    }
    //涉及多个数据源的load，所以需要需要做reduce操作
    @Override
    public List<RepaymentEntity> loadedData(String  batchDate) {
        List<RepaymentEntity> repaymentEntityList =  viewRepaymentDao.queryRepaymentFromView(batchDate);
        return repaymentEntityList;
    }

    @Override
    public void processor(List<RepaymentEntity> data) {
        //数据清洗，如果通过，可以父类实现或者抽离
    }
}
