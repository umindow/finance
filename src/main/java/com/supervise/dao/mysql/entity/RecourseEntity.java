package com.supervise.dao.mysql.entity;

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
    @DepRole(depTypes = {DepType.LAW_DEP},modify = false)
    private String orgId;
    /**
     * 项目编号
     **/
    @Column(name = "proj_id")
    @DepRole(depTypes = {DepType.LAW_DEP},modify = false)
    private String projId;
    /**
     * 合同编号
     **/
    @Column(name = "contract_id")
    @DepRole(depTypes = {DepType.LAW_DEP},modify = true)
    private String contractId;
    /**
     * 追偿类型：1 普通追偿 2 挽回损失
     **/
    @Column(name = "replevy_type")
    @DepRole(depTypes = {DepType.LAW_DEP},modify = true)
    private String replevyType;

    /**
     * 追偿日期
     **/
    @Column(name = "replevy_date")
    @DepRole(depTypes = {DepType.LAW_DEP},modify = true)
    private Date replevyDate;

    /**
     * 追偿金额
     **/
    @Column(name = "replevy_money")
    @DepRole(depTypes = {DepType.LAW_DEP},modify = true)
    private BigDecimal replevyMoney;

    
    /**
     * 批次（当前传输日期，格式yyyyMMdd）
     **/
    @Column(name = "batch_date")
    @DepRole(depTypes = {DepType.LAW_DEP},modify = true)
    private String batchDate;

}