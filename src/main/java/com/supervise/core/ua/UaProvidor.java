package com.supervise.core.ua;

import com.supervise.dao.mysql.entity.UserActionEntity;
import com.supervise.dao.mysql.mapper.UserActionMapper;
import com.supervise.support.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xishui.hb on 2018/1/30 下午6:04.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public enum UaProvidor {
    INSTANCE;
    private final Logger logger = LoggerFactory.getLogger(UaProvidor.class);

    /**
     * 添加行为日志数据,最好是要包含用户信息的
     * @param userActionEntity
     */
    public void addUa(UserActionEntity userActionEntity){
        try {
            SpringContextHolder.getBean(UserActionMapper.class).insert(userActionEntity);
        }catch (Exception e){
            logger.error("add UserAction Exception,"+ e);
        }
    }
}
