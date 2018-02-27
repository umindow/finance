package com.supervise.dao.mysql.provider;


import com.supervise.common.Constants;
import com.supervise.config.mysql.base.BasicProvider;
import com.supervise.config.mysql.base.QueryCondition;

/**
 * ClassName: FeeAndRefundProvider <br/>
 * Description:  <br/>
 * Date:  <br/>
 * @version 1.0 <br/>
 */
public class FeeAndRefundProvider extends BasicProvider {


    /**
     * 按照指定条件从中间表中查询缴费以及退款信息
     * @param batchDate 查询批次
     * @return SQL
     */
    public String queryFeeAndRefundFormMiddleDB(String batchDate){

        String selectSql = "SELECT * FROM "+Constants.FINANCE_FEE_AND_REFUND_INFO;

        String whereSql = createWhereSqlByBatchDate(batchDate);

        return selectSql+whereSql;
    }

    /**
     * 按照指定条件从中间表中查询收退费信息
     * @param queryCondition 查询条件
     * @return SQL
     */
    public String queryFeeAndRefundByConditions(QueryCondition queryCondition){

        String selectSql = "SELECT * FROM "+Constants.FINANCE_FEE_AND_REFUND_INFO;

        String whereSql = createWhereSql(queryCondition, null);

        return selectSql+whereSql;
    }
}