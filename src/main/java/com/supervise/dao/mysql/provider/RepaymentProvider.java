package com.supervise.dao.mysql.provider;


import com.supervise.common.Constants;
import com.supervise.config.mysql.base.BasicProvider;
import com.supervise.config.mysql.base.QueryCondition;

/**
 * ClassName: RepaymentProvider <br/>
 * Description:  <br/>
 * Date:  <br/>
 * @version 1.0 <br/>
 */
public class RepaymentProvider extends BasicProvider {

  
    /**
     * 按照指定条件查询还款信息
     * @param batchDate 查询批次
     * @return SQL
     */
    public String queryRepaymentView(String batchDate){
        String selectSql = "SELECT * FROM "+Constants.REPAYMENT_VIEW;

        String whereSql = createWhereSql4batchdate(batchDate);

        return selectSql+whereSql;
    }

    /**
     * 按照指定条件从中间表中查询还款信息
     * @param batchDate 查询批次
     * @return SQL
     */
    public String queryRepaymentFormMiddleDB(String batchDate){

        String selectSql = "SELECT * FROM "+Constants.FINANCE_REPAYMENT_INFO;

        String whereSql = createWhereSqlByBatchDate(batchDate);

        return selectSql+whereSql;
    }

    /**
     * 按照指定条件从中间表中查询还款信息
     * @param queryCondition 查询条件
     * @return SQL
     */
    public String queryRepaymentByConditions(QueryCondition queryCondition){

        String selectSql = "SELECT * FROM "+Constants.FINANCE_REPAYMENT_INFO;

        String whereSql = createWhereSql(queryCondition, null);

        return selectSql+whereSql;
    }
}