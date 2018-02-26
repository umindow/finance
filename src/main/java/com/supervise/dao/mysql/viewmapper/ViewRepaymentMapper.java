package com.supervise.dao.mysql.viewmapper;

import java.util.List;

import com.supervise.config.mysql.base.BaseMapper;
import com.supervise.dao.mysql.entity.BankCreditEntity;
import com.supervise.dao.mysql.entity.RepaymentEntity;

import org.apache.ibatis.annotations.*;

import com.supervise.dao.mysql.provider.*;

/**
 * Created by xishui.hb on 2018/2/6 上午10:32.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@Mapper
public interface ViewRepaymentMapper extends BaseMapper<RepaymentEntity>{
	
	/**
     * 按照指定条件查询原始报文信息
     * @param queryOrgMsgRequest 查询原始报文信息请求参数封装类
     * @param displayRule 排序条件
     * @return List<FlightSchInfo>
     */
    @Results({
            @Result(column = "org_id", property = "orgId"),
            @Result(column = "proj_id", property = "projId"),
            @Result(column = "contract_id", property = "contractId"),
            @Result(column = "repay_date", property = "repayDate"),
            @Result(column = "principal", property = "principal"),
            @Result(column = "interest", property = "interest"),
            @Result(column = "punish_money", property = "punishMoney"),
            @Result(column = "batch_date", property = "batchDate")
    })
    @SelectProvider(type = RepaymentProvider.class, method = "queryRepaymentView")
    List<RepaymentEntity> queryRepaymentView(String batchDate);
}
