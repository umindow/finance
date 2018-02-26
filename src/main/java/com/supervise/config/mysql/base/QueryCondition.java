package com.supervise.config.mysql.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.springframework.util.StringUtils;

import com.supervise.common.CommonUtils;
import com.supervise.common.Constants;
import com.supervise.common.DateUtils;

/**
 * ClassName: QueryCondition <br/>
 * Description: 查询条件对象 <br/>
 * Date: 2016/9/6 15:35 <br/>
 * 
 *
 * 
 * @version 1.0 <br/>
 */

public class QueryCondition {

    /**
     * 属性名
     */
    private List<String> columnList;

    /**
     * 查询条件的操作符列表
     */
    private List<QueryOperator> queryOperatorList;

    /**
     * 值，符合sql要求的字符串
     */
    private List<String> valueList;

    
    public List<String> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<String> columnList) {
		this.columnList = columnList;
	}

	public List<QueryOperator> getQueryOperatorList() {
		return queryOperatorList;
	}

	public void setQueryOperatorList(List<QueryOperator> queryOperatorList) {
		this.queryOperatorList = queryOperatorList;
	}

	public List<String> getValueList() {
		return valueList;
	}

	public void setValueList(List<String> valueList) {
		this.valueList = valueList;
	}

	/**
     * 默认构造函数
     */
    public QueryCondition(){
        columnList = new ArrayList();
        queryOperatorList = new ArrayList();
        valueList = new ArrayList();
    }

    /**
     * Sets condition equal.
     * 按照"="查询
     * value不能为null，当value为String时若为""则按照""进行查询
     * @param column 列名
     * @param value        the value
     */
    public void setConditionEqual(String column, Object value) {
        setCondition(column, QueryOperator.EQUAL, value);
    }

    /**
     * Sets condition equal.
     * 将values中的每一个值都按照"="查询
     * value不能为null，当value为String时若为""则按照""进行查询
     * @param column 列名
     * @param values        the value list
     */
    public void setConditionEqualWithCollection(String column,
                                                Collection values, boolean... isUpper) {
        if(!CommonUtils.collectionIsEmpty(values)) {
        	for(Object value :values){
        		 setCondition(column, QueryOperator.EQUAL, value);
        	}
        }
    }

    /**
     * Sets condition not equal.
     * 按照"<>"查询
     * value不能为null，当value为String时若为""则按照""进行查询
     * @param column 列名
     * @param value        the value
     */
    public void setConditionNotEqual(String column, Object value) {
        setCondition(column, QueryOperator.NOT_EQUAL, value);
    }

    /**
     * Sets condition greater equal.
     * 按照大于等于查询，支持Date,String,Integer,Long类型
     * value不能为null，当value为String时若为""则按照""进行查询
     * @param column 列名
     * @param value        the value
     */
    public void setConditionGreaterEqual(String column, Object value) {
        setCondition(column, QueryOperator.GREATER_EQUAL, value);
    }

    /**
     * Sets condition greater than.
     * 按照大于查询，支持Date,String,Integer,Long类型
     * value不能为null，当value为String时若为""则按照""进行查询
     * @param column 列名
     * @param value        the value
     */
    public void setConditionGreaterThan(String column, Object value) {
        setCondition(column, QueryOperator.GREATER_THAN, value);
    }

    /**
     * Sets condition less equal.
     * 按照小于等于查询，支持Date,String,Integer,Long类型
     * value不能为null，当value为String时若为""则按照""进行查询
     * @param column 列名
     * @param value        the value
     */
    public void setConditionLessEqual(String column, Object value) {
        setCondition(column, QueryOperator.LESS_EQUAL, value);
    }

    /**
     * Sets condition less than.
     * 按照小于查询，支持Date,String,Integer,Long类型
     * value不能为null，当value为String时若为""则按照""进行查询
     * @param column 列名
     * @param value        the value
     */
    public void setConditionLessThan(String column, Object value) {
        setCondition(column, QueryOperator.LESS_THAN, value);
    }

    /**
     * Sets condition left include.
     * 按照like value%查询，支持String类型
     * value不能为null，当value为String时若为""则按照""进行查询
     * @param column 列名
     * @param value        the value
     */
    public void setConditionLeftInclude(String column, String value) {
        setCondition(column, QueryOperator.LEFT_INCLUDE, value + "%");
    }

    /**
     * Sets condition right include.
     * 按照like %value查询，支持String类型
     * value不能为null，当value为String时若为""则按照""进行查询
     * @param column 列名
     * @param value        the value
     */
    public void setConditionRightInclude(String column, String value) {
        setCondition(column, QueryOperator.RIGHT_INCLUDE, "%" + value);
    }

    /**
     * Sets condition include.
     * 按照like %value%查询，支持String类型
     * value不能为null，当value为String时若为""则按照""进行查询
     * @param column 列名
     * @param value        the value
     */
    public void setConditionInclude(String column, String value) {
        setCondition(column, QueryOperator.INCLUDE, "%" + value + "%");
    }

    /**
     * Sets condition is null.
     * 按照is null 查询
     * @param column 列名
     */
    public void setConditionIsNull(String column) {
        setCondition(column, QueryOperator.IS_NULL,
                QueryOperator.IS_NULL);
    }

    /**
     * Sets condition is not null.
     * 按照is not null 查询
     * @param column 列名
     */
    public void setConditionIsNotNull(String column) {
        setCondition(column, QueryOperator.IS_NOT_NULL,
                QueryOperator.IS_NOT_NULL);
    }

    /**
     * Set condition in.
     * 按照in查询，支持String,Integer,Long类型
     * value不能为null，当value为String时若为""则按照""进行查询
     * @param column 列名
     * @param inList       the in list
     */
    public void setConditionIn(String column, Object inList){
        setCondition(column, QueryOperator.IN, inList);
    }

    /**
     * Set condition not in.
     * 按照not in查询，支持String,Integer,Long类型
     * value不能为null，当value为String时若为""则按照""进行查询
     * @param column 列名
     * @param notInList    the not in list
     */
    public void setConditionNotIn(String column, Object notInList){
        setCondition(column, QueryOperator.NOT_IN, notInList);
    }

    /**
     * Sets condition.
     * 若属性名为空，则该条件不设置，不报错
     * 若校验失败则抛出FlightException，在上层会被包装为相应的数据库异常
     * 这里只抛成运行时异常
     * @param column  列名
     * @param queryOperator the query operator
     * @param value         the value
     */
    private void setCondition(String column, QueryOperator queryOperator, Object value) {
        if(check(column, queryOperator, value)) {
            //add property
            this.columnList.add(column);
            //add value and operator
            this.valueList.add(convertValue(value));
            //add value operator
            this.queryOperatorList.add(queryOperator);
        }else{
            
        }
    }

    /**
     * 校验输入项是否合法
     * 1）属性名不能为空（不能为null，空，trim不为空）
     * 2）value不能为null
     *       注如果value为"",则将""作为查询条件
     * 3）value是集合时，操作符只能是IN/NOT_IN，且集合不能为空
     * @param column 列名
     * @param queryOperator 查询操作符
     * @param value 值
     * @return true:校验通过，false:校验不通过
     */
    private boolean check(String column, QueryOperator queryOperator, Object value){
        boolean isLegal = true;
        //属性名不能为空, value不能为null
        if (!StringUtils.hasText(column) || value == null){
            isLegal = false;
        }else if((value instanceof Collection) &&
                !(QueryOperator.IN.equals(queryOperator) ||
                        QueryOperator.NOT_IN.equals(queryOperator))){
            isLegal = false;
        }else if(value instanceof  Collection && ((Collection)value).isEmpty()){
            isLegal = false;
        }
        return isLegal;
    }

    /**
     * 转换value为sql中的值部分，后续直接将value拼进sql语句即可
     * 1)如果value是QueryOperator，将value置为""，
     * 适用于IS_NULL、IS_NOT_NULL
     * 2)如果value是String则，将value用单引号括起来
     * 3)如果value是集合，则将集合中的值以逗号分隔连接起来，并将其用小括号括住
     * 如果集合的泛型类型是String，则每个值需要以单引号括起来
     * @param value 查询条件中的值对象
     * @return sql中的值部分
     */
    private String convertValue(Object value){
        StringBuffer valueBuffer = new StringBuffer();
        if(value instanceof QueryOperator){
            valueBuffer.append("");
        } else if(value instanceof String){
            valueBuffer.append(getValueWithString((String)value));
        } else if(value instanceof Date){
            valueBuffer.append(getValueWithDate((Date) value));
        }else if(value instanceof Collection){
            valueBuffer.append(Constants.LEFT_PARENTHESIS).
                    append(getValue4Collection((Collection)value)).
                    append(Constants.RIGHT_PARENTHESIS);
        }else{
            valueBuffer.append(value);
        }
        return valueBuffer.toString();
    }

    /**
     * 对集合获取值的字符串，集合中的值以逗号分隔连接起来
     * 对于非String类型，格式为1,2
     * 对String类型，格式为'a','b'
     * @param collection 集合
     * @return 集合的值转为sql中的值的字符串
     */
    private String getValue4Collection(Collection<?> collection){
        StringBuffer valueBuffer = new StringBuffer();
        Iterator it = collection.iterator();
        while (it.hasNext()){
            if(!StringUtils.isEmpty(valueBuffer.toString())){
                valueBuffer.append(Constants.COMMA);
            }
            Object value = it.next();
            if(value instanceof String){
                valueBuffer.append(getValueWithString((String)value));
            }else {
                valueBuffer.append(value);
            }
        }
        return valueBuffer.toString();
    }

    /**
     * 对于String的value，将value格式化为'value'
     * @param value 值
     * @return 格式化后的值
     */
    private String getValueWithString(String value){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(Constants.SINGLE_QUOTE).
                append(value).
                append(Constants.SINGLE_QUOTE);
        return stringBuffer.toString();
    }

    /**
     * 对于Date的value，将value格式化为to_date('2017-01-01,'yyyy-MM-dd')
     * 目前不支持其它日期格式
     * TODO：xjxu允许自定义日期格式
     * @param value 值
     * @return 格式化后的值
     */
    private String getValueWithDate(Date value){
        String date = DateUtils.formatDate(
                value, Constants.YYYY_MM_DD, Locale.ENGLISH);
        StringBuffer dateBuffer = new StringBuffer();
        dateBuffer.append("to_date").
                append(Constants.LEFT_PARENTHESIS).
                append(Constants.SINGLE_QUOTE).
                append(date).
                append(Constants.SINGLE_QUOTE).
                append(Constants.COMMA).
                append(Constants.SINGLE_QUOTE).
                append(Constants.YYYY_MM_DD).
                append(Constants.SINGLE_QUOTE).
                append(Constants.RIGHT_PARENTHESIS);
        return dateBuffer.toString();
    }
}

