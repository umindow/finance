package com.supervise.core.data.sysbiz;

import com.supervise.core.data.spi.DataMapper;
import com.supervise.dao.mysql.entity.SystemBizEntity;

import java.sql.ResultSet;

/**
 * Created by xishui.hb on 2018/1/31 上午10:07.
 *  系统业务数据的查询解析类
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public class SystemBizDataMapper implements DataMapper<SystemBizEntity>{
    @Override
    public SystemBizEntity mapper(ResultSet resultSet) {
        return null;
    }
}
