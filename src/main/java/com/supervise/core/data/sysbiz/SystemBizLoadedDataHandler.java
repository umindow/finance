package com.supervise.core.data.sysbiz;

import com.supervise.core.data.spi.DataConfig;
import com.supervise.core.data.spi.GenericDataProcessorHandler;
import com.supervise.core.data.spi.QueryMain;
import com.supervise.dao.mysql.entity.SystemBizEntity;
import com.supervise.dao.mysql.mapper.SystemBizMapper;
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
public class SystemBizLoadedDataHandler extends GenericDataProcessorHandler<SystemBizEntity,List<DataConfig<SystemBizEntity>>>{
    @Autowired
    private SystemBizMapper systemBizMapper;
    @Override
    public void afterData(SystemBizEntity dataParam) {
        //插入数据库
        systemBizMapper.insert(dataParam);
    }
    //涉及多个数据源的load，所以需要需要做reduce操作
    @Override
    public SystemBizEntity loadedData(List<DataConfig<SystemBizEntity>> configParam) {
        final SystemBizEntity systemBizEntity = new SystemBizEntity();
        for (final DataConfig<SystemBizEntity> dataConfig : configParam){
            new SystemBizReduce().reduce(systemBizEntity, QueryMain.newQuery(dataConfig).query());
        }
        return systemBizEntity;
    }

    @Override
    public void processor(SystemBizEntity data) {
        //数据清洗，如果通过，可以父类实现或者抽离
    }
}
