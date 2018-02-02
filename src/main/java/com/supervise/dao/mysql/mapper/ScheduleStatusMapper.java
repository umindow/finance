package com.supervise.dao.mysql.mapper;

import com.supervise.config.mysql.base.BaseMapper;
import com.supervise.dao.mysql.entity.ScheduleStatusEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

/**
 * Created by xishui.hb on 2018/2/1 下午4:26.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public interface ScheduleStatusMapper extends BaseMapper<ScheduleStatusEntity> {
    @Update("update finance_schedule_status set schedule_status =#{status},update_time=#{updateDate} where dup_key=#{dupKey}")
    void updateStatus(@Param("status") int status, @Param("dupKey") String dupKey, @Param("updateDate") Date updateDate);
}
