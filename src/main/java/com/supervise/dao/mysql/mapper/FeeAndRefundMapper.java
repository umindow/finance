package com.supervise.dao.mysql.mapper;

import com.supervise.config.mysql.base.BaseMapper;
import com.supervise.dao.mysql.entity.FeeAndRefundEntity;
import com.supervise.dao.mysql.provider.FeeAndRefundProvider;
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
public interface FeeAndRefundMapper extends BaseMapper<FeeAndRefundEntity>{

    /**
     * 按照指定条件从中间库查询缴费以及退款信息
     * @param batchDate 批次
     * @return List<FeeAndRefundEntity>
     */
    @Results({
            @Result(column = "org_id", property = "orgId"),
            @Result(column = "proj_id", property = "projId"),
            @Result(column = "contract_id", property = "contractId"),
            @Result(column = "charge_way", property = "chargeWay"),
            @Result(column = "charge_type", property = "chargeType"),
            @Result(column = "charge_time", property = "chargeTime"),
            @Result(column = "charge_money", property = "chargeMoney"),
            @Result(column = "batch_date", property = "batchDate")
    })
    @SelectProvider(type = FeeAndRefundProvider.class, method = "queryFeeAndRefundFormMiddleDB")
    List<FeeAndRefundEntity> queryFeeAndRefundFormMiddleDB(String batchDate);
}
