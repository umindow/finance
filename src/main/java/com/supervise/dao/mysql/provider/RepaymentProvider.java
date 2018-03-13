package com.supervise.dao.mysql.provider;


import com.supervise.common.Constants;
import com.supervise.config.mysql.base.BasicProvider;
import com.supervise.config.mysql.base.QueryCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName: RepaymentProvider <br/>
 * Description:  <br/>
 * Date:  <br/>
 * @version 1.0 <br/>
 */
public class RepaymentProvider extends BasicProvider {

    private final Logger logger = LoggerFactory.getLogger(RepaymentProvider.class);
    /**
     * 按照指定条件查询还款信息
     * @param batchDate 查询批次
     * @return SQL
     */
    public String queryRepaymentView(String batchDate){
        String selectSql = "SELECT * FROM "+Constants.REPAYMENT_VIEW;

        String whereSql = createWhereSql4batchdate(batchDate);
        logger.info("queryRepaymentView whereSql :"+whereSql);
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