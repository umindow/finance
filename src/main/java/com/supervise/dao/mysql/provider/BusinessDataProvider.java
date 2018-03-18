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
public class BusinessDataProvider extends BasicProvider {

	private final Logger logger = LoggerFactory.getLogger(BusinessDataProvider.class);
	/**
	 * 按照指定条件从视图中查询业务数据信息
	 * @param batchDate 查询批次以及状态
	 * @return SQL
	 */
	public String queryBusinessDataView(String batchDate,String projStatus){

		String selectSql = "SELECT * FROM "+Constants.BUSINESSDATA_VIEW;

		String whereSql = createWhereSql4batchdate(batchDate,projStatus);
//		System.out.println("whereSql:"+whereSql);
		logger.info("queryBusinessDataView whereSql :"+whereSql);
		return selectSql+whereSql;
	}

	/**
	 * 按照指定条件从中间库中查询业务数据信息
	 * @param batchDate 查询批次
	 * @return SQL
	 */
	public String queryBusinessDataFormMiddleDB(String batchDate){
		String selectSql = "SELECT * FROM "+Constants.FINANCE_BUSINESS_DATA_INFO;

		String whereSql = createWhereSqlByBatchDate(batchDate);

//		System.out.println("whereSql:"+whereSql);
		return selectSql+whereSql;
	}

	/**
	 * 按照指定条件从中间表中查询银行授信信息
	 * @param queryCondition 查询条件
	 * @return SQL
	 */
	public String queryBusinessDataByConditions(QueryCondition queryCondition){

		String selectSql = "SELECT * FROM "+Constants.FINANCE_BUSINESS_DATA_INFO;

		String whereSql = createWhereSql(queryCondition, null);

//		System.out.println("whereSql:"+whereSql);

		return selectSql+whereSql;
	}

}