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
    /**
     * 6大类数据类型 ：1 银行授信  2 业务信息 3 还款信息 4 收退费信息 5 代偿信息 6 追偿信息
     */
    @Column(name = "data_type")
    private String  dataType;
    /**
     * 数据类型的名称,不必须填写，数据类型的中文描述
     */
    @Column(name = "data_name")
    private String dataName;

    /**
     *执行结果：0, 成功  -1 失败
     */
    @Column(name = "result")
    private String result;

    /**
     *执行类型：0 同步数据 1 上报数据
     */
    @Column(name = "op_type")
    private String opType;

    /**
     *备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 记录操作结束时间
     */
    @Column(name = "op_time")
    private Date opTime;

    /**
     * 创建日期
     */
    @Column(name = "create_time")
    private Date createTime;

}
