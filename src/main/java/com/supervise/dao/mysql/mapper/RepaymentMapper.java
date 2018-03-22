package com.supervise.dao.mysql.mapper;

import com.supervise.config.mysql.base.BaseMapper;
import com.supervise.config.mysql.base.QueryCondition;
import com.supervise.dao.mysql.entity.RepaymentEntity;
import com.supervise.dao.mysql.provider.RepaymentProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * Created by xishui.hb on 2018/2/6 上午10:32.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public interface RepaymentMapper extends BaseMapper<RepaymentEntity>{

    /**
     * 按照指定条件从中间库查询还款信息
     * @param batchDate 批次
     * @return List<RepaymentEntity>
     */
    @Results({
            @Result(column = "org_id", property = "orgId"),
            @Result(column = "proj_id", property = "projId"),
            @Result(column = "contract_id", property = "contractId"),
            @Result(column = "repay_date", property = "repayDate"),
            @Result(column = "principal", property = "principal"),
            @Result(column = "interest", property = "interest"),
            @Result(column = "punish_money", property = "punishMoney"),
            @Result(column = "batch_date", property = "batchDate"),
            @Result(column = "create_date", property = "createDate"),
            @Result(column = "update_date", property = "updateDate"),
            @Result(column = "sendStatus", property = "sendStatus")
    })
    @SelectProvider(type = RepaymentProvider.class, method = "queryRepaymentFormMiddleDB")
    List<RepaymentEntity> queryRepaymentFormMiddleDB(String batchDate);


    /**
     * 按照指定条件从中间库查询还款信息
     * @param queryCondition 查询条件
     * @return List<RepaymentEntity>
     */
    @Results({
            @Result(column = "org_id", property = "orgId"),
            @Result(column = "proj_id", property = "projId"),
            @Result(column = "contract_id", property = "contractId"),
            @Result(column = "repay_date", property = "repayDate"),
            @Result(column = "principal", property = "principal"),
            @Result(column = "interest", property = "interest"),
            @Result(column = "punish_money", property = "punishMoney"),
            @Result(column = "batch_date", property = "batchDate"),
            @Result(column = "create_date", property = "createDate"),
            @Result(column = "update_date", property = "updateDate"),
            @Result(column = "sendStatus", property = "sendStatus")
    })
    @SelectProvider(type = RepaymentProvider.class, method = "queryRepaymentByConditions")
    List<RepaymentEntity> queryRepaymentByConditions(QueryCondition queryCondition);
}
