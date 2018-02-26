package com.supervise.core.data.loadProces;

import com.supervise.core.data.spi.GenericDataProcessorHandler;
import com.supervise.dao.mysql.entity.BusinessDataEntity;
import com.supervise.dao.mysql.middleDao.BusinessDataDao;
import com.supervise.dao.mysql.viewDao.ViewBusinessDataDao;
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
public class BusinessDataLoader extends GenericDataProcessorHandler<List<BusinessDataEntity>,String>{
    @Autowired
    private ViewBusinessDataDao viewBusinessDataDao;

    @Autowired
    private BusinessDataDao businessDataDao;

    @Override
    public void afterData(List<BusinessDataEntity> dataRes) {
        //插入数据库
        for(BusinessDataEntity businessDataEntity : dataRes){
            businessDataDao.insertBusinessDataToMiddleDB(businessDataEntity);
        }

    }
    //涉及多个数据源的load，
    @Override
    public List<BusinessDataEntity> loadedData(String  batchDate) {
        List<BusinessDataEntity> businessDataEntityList =  viewBusinessDataDao.queryBusinessDataFromView(batchDate);
        return businessDataEntityList;
    }

    @Override
    public void processor(List<BusinessDataEntity> data) {
        //数据清洗，如果通过，可以父类实现或者抽离
    }
}
