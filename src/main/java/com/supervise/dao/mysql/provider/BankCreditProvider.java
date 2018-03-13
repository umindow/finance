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
public class BankCreditProvider extends BasicProvider {
	private final Logger logger = LoggerFactory.getLogger(BankCreditProvider.class);

	/**
	 * 按照指定条件从VIEW中查询银行授信信息
	 * @param batchDate 查询批次
	 * @return SQL
	 */
	public String queryBankCreditView(String batchDate){
		String selectSql = "SELECT * FROM "+Constants.BANKCREDIT_VIEW;

		String whereSql = createWhereSql4batchdate(batchDate);
		logger.info("queryBankCreditView whereSql :"+whereSql);
		return selectSql+whereSql;
	}

	/**
	 * 按照指定条件从中间表中查询银行授信信息
	 * @param batchDate 查询批次
	 * @return SQL
	 */
	public String queryBankCreditFormMiddleDB(String batchDate){

		String selectSql = "SELECT * FROM "+Constants.FINANCE_BANK_CREDIT;

		String whereSql = createWhereSqlByBatchDate(batchDate);

		return selectSql+whereSql;
	}

	/**
	 * 按照指定条件从中间表中查询银行授信信息
	 * @param queryCondition 查询条件
	 * @return SQL
	 */
	public String queryBankCreditByConditions(QueryCondition queryCondition){

		String selectSql = "SELECT * FROM "+Constants.FINANCE_BANK_CREDIT;

		String whereSql = createWhereSql(queryCondition, null);

		return selectSql+whereSql;
	}
}