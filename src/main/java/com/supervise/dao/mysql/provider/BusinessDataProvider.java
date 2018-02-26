package com.supervise.dao.mysql.provider;


import com.supervise.common.Constants;
import com.supervise.config.mysql.base.BasicProvider;

/**
 * ClassName: RepaymentProvider <br/>
 * Description:  <br/>
 * Date:  <br/>
 * @version 1.0 <br/>
 */
public class BusinessDataProvider extends BasicProvider {


	/**
	 * 按照指定条件从视图中查询业务数据信息
	 * @param batchDate 查询批次
	 * @return SQL
	 */
	public String queryBusinessDataView(String batchDate){
		String selectSql = "SELECT * FROM "+Constants.BUSINESSDATA_VIEW;

		String whereSql = createWhereSql4batchdate(batchDate);

		return selectSql+whereSql;
	}

	/**
	 * 按照指定条件从中间库中查询业务数据信息
	 * @param batchDate 查询批次
	 * @return SQL
	 */
	public String queryBusinessDataFormMiddleDB(String batchDate){
		String selectSql = "SELECT * FROM "+Constants.FINANCE_BUSINESS_DATA_INFO;

		String whereSql = createWhereSql4batchdate(batchDate);

		return selectSql+whereSql;
	}

}