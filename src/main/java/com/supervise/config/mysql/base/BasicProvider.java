package com.supervise.config.mysql.base;


import com.supervise.common.CommonUtils;
import com.supervise.common.Constants;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: BasicProvider <br/>
 * Description: 基础Provider类，以泛型的范式提供通用的增删改查方法 <br/>
 * Date: 2017/7/11 13:49 <br/>
 *
 *
 * @author 
 * @version 1.0 <br/>
 */
public class BasicProvider {

 

   

    /**
     * 创建SQL
     * 拼接headSql和where sql
     * @param headSql 可以是select语句，update语句等where之前的sql
     * @param whereSql where语句
     * @return 查询sql
     */
    protected String createSelectUpdateSql(String headSql, String whereSql){
        StringBuffer sql = new StringBuffer();
        sql.append(headSql);
        if(!StringUtils.isEmpty(whereSql)){
            sql.append(Constants.SPACE).append(whereSql);
        }
        return sql.toString();
    }

    /**
     * 创建SQL
     * 拼接headSql,where sql,order by sql
     * @param headSql 可以是select语句，update语句等where之前的sql
     * @param whereSql where语句
     * @param orderSql order by 语句
     * @return 完整的sql
     */
    protected String createSelectUpdateSql(String headSql, String whereSql, String orderSql){
        StringBuffer sql = new StringBuffer();
        sql.append(createSelectUpdateSql(headSql, whereSql));
        if(!StringUtils.isEmpty(orderSql)){
            sql.append(Constants.SPACE).append(orderSql);
        }
        return sql.toString();
    }

    /**
     * 创建where SQL
     * 多个condition之间通过and连接
     * @param andCondition 与条件，该condition内部的条件通过and连接
     * @param orCondition 或条件，该conditon内部的条件通过or连接
     * @return where语句，从"WHERE"开始
     */
    protected String createWhereSql(QueryCondition andCondition,
                                    QueryCondition... orCondition){
        StringBuffer whereSql = new StringBuffer();
        String sqlOnAndCondition = createWhereSqlByConnector(
        		Constants.CONNECTOR_AND, andCondition);
        String sqlOnOrCondition = createWhereSqlWithOrConditions(orCondition);
        if(!StringUtils.isEmpty(sqlOnAndCondition) &&
                !StringUtils.isEmpty(sqlOnOrCondition)){
            whereSql.append(Constants.CONNECTOR_WHERE).
                    append(Constants.SPACE).
                    append(sqlOnAndCondition).
                    append(Constants.SPACE).
                    append(Constants.CONNECTOR_AND).
                    append(Constants.SPACE).
                    append(sqlOnOrCondition);
        }else if(!StringUtils.isEmpty(sqlOnAndCondition)){
            whereSql.append(Constants.CONNECTOR_WHERE).
                    append(Constants.SPACE).
                    append(sqlOnAndCondition);
        }else if(!StringUtils.isEmpty(sqlOnOrCondition)){
            whereSql.append(Constants.CONNECTOR_WHERE).
                    append(Constants.SPACE).
                    append(sqlOnOrCondition);
        }else{
            //LOGGER.info("BasicProvider.createWhereSql, there is no condition");
        }
        return whereSql.toString();
    }

    /**
     * 按照指定的连接符，将指定condition中的所有条件连接起来
     * @param condition 条件
     * @param connector 连接符
     * @return 当前QueryCondition的SQL
     */
    private String createWhereSqlByConnector(String connector,
                                             QueryCondition condition){
        StringBuffer whereSql = new StringBuffer();
        if(condition != null &&
                !CommonUtils.collectionIsEmpty(condition.getColumnList())){
            List<String> columnList = condition.getColumnList();
            List<QueryOperator> queryOperatorList = condition.getQueryOperatorList();
            List<String> valueList = condition.getValueList();
            for(int i = 0; i < columnList.size(); ++i){
                if(StringUtils.isEmpty(whereSql.toString())){
                    whereSql.append(Constants.LEFT_PARENTHESIS);
                }else{
                    whereSql.append(Constants.SPACE).
                            append(connector).
                            append(Constants.SPACE);
                }
                whereSql.append(columnList.get(i)).
                        append(Constants.SPACE).
                        append(queryOperatorList.get(i).getOperator()).
                        append(Constants.SPACE).
                        append(valueList.get(i));
            }
        }
        if(!StringUtils.isEmpty(whereSql.toString())) {
            whereSql.append(Constants.RIGHT_PARENTHESIS);
        }
        return whereSql.toString();
    }
 
    /**
     * 对每一个QueryCondition创建or连接的条件
     * 对多个or条件组通过and连接
     * @param condition 可变长参数，每一个conditon是一组or连接的条件集
     * @return 所有or条件组通过and连接起来的sql
     */
    private String createWhereSqlWithOrConditions(QueryCondition... condition){
        List<String> orSqlList = createOrWhereSql(condition);
        return connectOrCondition(orSqlList);
    }
    
    
    /**
     * 对每一个QueryCondition创建or连接的条件
     * @param condition 可变长参数，每一个conditon是一组or连接的条件集
     * @return or连接的查询sql列表
     */
    private List<String> createOrWhereSql(QueryCondition... condition){
        List<String> orSqlList = new ArrayList();
        if(condition != null && condition.length > 0){
            for(int i = 0; i < condition.length; ++i){
                String orSql = createWhereSqlByConnector(
                        Constants.CONNECTOR_OR, condition[i]);
                if(!StringUtils.isEmpty(orSql)){
                    orSqlList.add(orSql);
                }
            }
        }
        return orSqlList;
    }
    
    /**
     * SQL：多个or条件组之间通过and连接
     * @param orSqlList or条件组
     * @return 多个or条件组之间通过and连接的sql
     */
    private String connectOrCondition(List<String> orSqlList){
        StringBuffer sqlOnOrCondition = new StringBuffer();
        if(!CommonUtils.collectionIsEmpty(orSqlList)){
            for(String orSql : orSqlList){
                if(!StringUtils.isEmpty(sqlOnOrCondition.toString())){
                    sqlOnOrCondition.append(Constants.SPACE).
                            append(Constants.CONNECTOR_AND).
                            append(Constants.SPACE);
                }
                sqlOnOrCondition.append(orSql);
            }
        }
        return sqlOnOrCondition.toString();
    }
    
    
}
