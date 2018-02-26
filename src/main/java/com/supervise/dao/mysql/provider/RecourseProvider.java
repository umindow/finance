package com.supervise.dao.mysql.provider;


import com.supervise.common.Constants;
import com.supervise.config.mysql.base.BasicProvider;

/**
 * ClassName: RepaymentProvider <br/>
 * Description:  <br/>
 * Date:  <br/>
 * @version 1.0 <br/>
 */
public class RecourseProvider extends BasicProvider {

  
    /**
     * 按照指定条件查询追偿信息
     * @param batchDate 查询批次
     * @return SQL
     */
    public String queryRecourseFormMiddleDB(String batchDate){
        String selectSql = "SELECT * FROM "+Constants.FINANCE_RECOURSE_INFO;

        String whereSql = createWhereSql4batchdate(batchDate);

        return selectSql+whereSql;
    }

}