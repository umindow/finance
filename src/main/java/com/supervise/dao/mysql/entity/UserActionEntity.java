package com.supervise.dao.mysql.entity;

import com.supervise.config.mysql.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by xishui on 2018/1/30 下午1:58.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@Data
@Table(name = "finance_user_action")
public class UserActionEntity extends BaseEntity{
    @Column(name = "user_id")
    private long userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "action_time")
    private Date actionTime;
    @Column(name = "action_type")
    private String actionType;
    @Column(name = "action_content")
    private String actionContent;
}
