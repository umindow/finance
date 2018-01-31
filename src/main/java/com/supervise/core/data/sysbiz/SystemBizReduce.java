package com.supervise.core.data.sysbiz;

import com.supervise.core.data.spi.Reduce;
import com.supervise.dao.mysql.entity.SystemBizEntity;

/**
 * Created by xishui.hb on 2018/1/31 上午10:14.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public class SystemBizReduce implements Reduce<SystemBizEntity,SystemBizEntity>{
    @Override
    public void reduce(SystemBizEntity originalData, SystemBizEntity reduceData) {
        //遍历 reduceData，不为空的字段都设置到originalData中
    }
}
