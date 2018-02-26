package com.supervise.dao.mysql.provider;


import org.springframework.util.StringUtils;

import com.supervise.common.Constants;
import com.supervise.config.mysql.base.BasicProvider;
import com.supervise.config.mysql.base.QueryOperator;
/**
 * ClassName: RepaymentProvider <br/>
 * Description:  <br/>
 * Date:  <br/>
 * @version 1.0 <br/>
 */
public class RepaymentProvider extends BasicProvider {

  
    /**
     * 按照指定条件查询退款信息
     * @param batchDate 查询批次
     * @return SQL
     */
    public String queryRepaymentView(String batchDate){
        String selectSql = "SELECT * FROM "+Constants.REPAYMENT_VIEW;

        String whereSql = createWhereSql4batchdate(batchDate);

        return selectSql+whereSql;
    }

 

    private String createWhereSql4batchdate(String batchDate){
    	StringBuffer whereSql = new StringBuffer();
        if(!StringUtils.isEmpty(batchDate)){
        	whereSql.append(Constants.SPACE);
            whereSql.append(Constants.CONNECTOR_WHERE);
        	whereSql.append(Constants.SPACE);
        	//大于等于当前天
        	whereSql.append("batch_date");
        	whereSql.append(Constants.SPACE);
        	whereSql.append(QueryOperator.GREATER_EQUAL.getOperator());
        	whereSql.append(Constants.SPACE);
        	whereSql.append(Constants.DATE_FORMATE);
        	whereSql.append(Constants.SPACE);
        	whereSql.append(Constants.LEFT_PARENTHESIS);
        	whereSql.append(Constants.SINGLE_QUOTE);
        	whereSql.append(batchDate);
        	whereSql.append(Constants.SPACE);
        	whereSql.append(Constants.DAY_MIN_DATE);
        	whereSql.append(Constants.SINGLE_QUOTE);
        	whereSql.append(Constants.COMMA);
        	whereSql.append(Constants.SINGLE_QUOTE);
        	whereSql.append(Constants.MYSQL_YYYY_MM_DD_HH_MM_SS);
        	whereSql.append(Constants.SINGLE_QUOTE);
        	whereSql.append(Constants.RIGHT_PARENTHESIS);
        	whereSql.append(Constants.SPACE);
        	//小于等于次日
        	whereSql.append(Constants.CONNECTOR_AND);
        	whereSql.append(Constants.SPACE);
        	whereSql.append("batch_date");
        	whereSql.append(Constants.SPACE);
        	whereSql.append(QueryOperator.LESS_EQUAL.getOperator());
        	whereSql.append(Constants.SPACE);
        	whereSql.append(Constants.DATE_FORMATE);
        	whereSql.append(Constants.SPACE);
        	whereSql.append(Constants.LEFT_PARENTHESIS);
        	whereSql.append(Constants.SINGLE_QUOTE);
        	whereSql.append(batchDate);
        	whereSql.append(Constants.SPACE);
        	whereSql.append(Constants.DAY_MAX_DATE);
        	whereSql.append(Constants.SINGLE_QUOTE);
        	whereSql.append(Constants.COMMA);
        	whereSql.append(Constants.SINGLE_QUOTE);
        	whereSql.append(Constants.MYSQL_YYYY_MM_DD_HH_MM_SS);
        	whereSql.append(Constants.SINGLE_QUOTE);
        	whereSql.append(Constants.RIGHT_PARENTHESIS);
        	whereSql.append(Constants.SPACE);
        }
        
        return whereSql.toString();
    }
}