package com.supervise.dao.mysql.entity;

import com.supervise.common.Constants;
import com.supervise.config.mysql.base.BaseEntity;
import com.supervise.config.role.DepRole;
import com.supervise.config.role.DepType;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by xishui.hb on 2018/2/6 上午10:03.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@Table(name = "finance_recourse_info")
@Data
public class RecourseEntity extends BaseEntity {
    
    /**
     * 机构编号（金融办分配的公司编号）,默认为：渝061001L
     **/
    @Column(name = "org_id")
    @DepRole(depTypes = {DepType.LAW_DEP},modify = false,fieldCnName = "机构编号",index = 1)
    private String orgId;
    /**
     * 项目编号
     **/
    @Column(name = "proj_id")
    @DepRole(depTypes = {DepType.LAW_DEP},modify = false,fieldCnName = "项目编号",index = 2)
    private String projId;
    /**
     * 合同编号
     **/
    @Column(name = "contract_id")
    @DepRole(depTypes = {DepType.LAW_DEP},modify = true,fieldCnName = "合同编号",index = 3)
    private String contractId;
    /**
     * 追偿类型：1 普通追偿 2 挽回损失
     **/
    @Column(name = "replevy_type")
    @DepRole(depTypes = {DepType.LAW_DEP},modify = true,fieldCnName = "追偿类型",index = 4)
    private String replevyType;

    /**
     * 追偿日期
     **/
    @Column(name = "replevy_date")
    @DepRole(depTypes = {DepType.LAW_DEP},modify = true,fieldCnName = "追偿日期",index = 5,isDate = true,dateFormat = Constants.YYYY_MM_DD)
    private Date replevyDate;

    /**
     * 追偿金额
     **/
    @Column(name = "replevy_money")
    @DepRole(depTypes = {DepType.LAW_DEP},modify = true,fieldCnName = "追偿金额",index = 6)
    private BigDecimal replevyMoney;

    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "update_date")
    private Date updateDate;

    /**
     * 批次（当前传输日期，格式yyyyMMdd）
     **/
    @Column(name = "batch_date")
    @DepRole(depTypes = {DepType.LAW_DEP},modify = true,fieldCnName = "批次",index = 7)
    private String batchDate;

    //发送状态，0:待发送 1 发送成功  -1 发送失败
    @Column(name = "sendStatus")
    private String sendStatus;


}
