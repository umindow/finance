package com.supervise.dao.mysql.entity;

import com.supervise.config.mysql.base.BaseEntity;
import lombok.Data;

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

@Data
public class ViewBankCreditEntity extends BaseEntity {
    /**
     * 银行授信记录标示ID
     **/
    private String primaryId;
    /**
     * 机构编号（金融办分配的公司编号）,默认为：渝061001L
     **/
    private String orgId;
    /**
     * 银行编号
     **/
     private String bankId;
    /**
     * 主办行编码
     **/
     private String mainBankId;
    /**
     * 授信类型：1 综合授信 2 单笔单议
     **/
    private String creditTypeId;
    /**
     * 授信额度
     **/
    private BigDecimal creditMoney;
    /**
     * 授信余额
     **/
    private BigDecimal leaveMoney;
    /**
     * 放大倍数
     **/
    private BigDecimal blowupMulpitle;
    /**
     * 初始保证金额
     **/
    private BigDecimal bailMoney;
    /**
     * 保证金比例（%）
     **/
    private BigDecimal bailScale;
    /**
     * 授信开始日期
     **/
    private Date creditStartDate;
    /**
     * 授信结束日期
     **/
    private Date creditEndDate;
    /**
     * 单笔限额
     **/
    private BigDecimal singleMoneyLimit;
    /**
     * 是否循环授信:1 是 2 否
     **/
    private String isForCredit;
    /**
     * 状态：1 使用 2 解除
     **/
    private String creditStatus;
    /**
     * 项目偏好
     **/
    private String itemLean;
    /**
     * 备注
     **/
    private String remark;
    /**
     * 批次（当前传输日期，格式yyyyMMdd）
     **/
    private Date batchDate;


}
