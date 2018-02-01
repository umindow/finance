package com.supervise.dao.mysql.entity;

import com.supervise.config.mysql.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by xishui.hb on 2018/2/1 下午4:28.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@Data
@Table(name = "finance_task_status")
public class TaskStatusEntity extends BaseEntity{
    @Column(name = "data_type")
    private int dataType;//发送的数据类型 6大类
    @Column(name = "data_name")
    private String dataName;//不必须填写，数据类型的中文描述
    @Column(name = "create_time")
    private Date createTime;//发送开始时间(发送不管成功与否的记录时间)
    @Column(name = "result")
    private String result;//发送成功,发送失败
}
