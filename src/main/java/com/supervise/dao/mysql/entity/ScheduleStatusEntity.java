package com.supervise.dao.mysql.entity;

import com.supervise.config.mysql.base.BaseEntity;
import lombok.Data;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by xishui.hb on 2018/2/1 下午4:23.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@Data
@Table(name = "finance_schedule_status")
public class ScheduleStatusEntity extends BaseEntity{
    @Column(name = "dup_key",unique = true)
    private String dupkey;
    @Column(name = "schedule_name")
    private String scheduleName;
    @Column(name = "schedule_status")
    private int scheduleStatus;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;


    public enum ScheduleStatus{
        INIT(1,"初始化"),
        COMPLETE(3,"完成"),
        FAILED(2,"失败");
        @Getter
        private int status;
        @Getter
        private String desc;

        ScheduleStatus(int status, String desc) {
            this.status = status;
            this.desc = desc;
        }
    }
}
