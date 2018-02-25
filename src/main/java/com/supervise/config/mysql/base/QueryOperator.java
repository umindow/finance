package com.supervise.config.mysql.base;

/**
 * ClassName: QueryOperator <br/>
 * Description: 查询操作符 <br/>
 * Date: 2016/9/6 15:37 <br/>
 * 
 *
 * 
 * @version 1.0 <br/>
 */
public enum QueryOperator implements IDbOperator {
    /**
     * Equal query operator.
     */
    EQUAL("="),

    /**
     * Not equal query operator.
     */
    NOT_EQUAL("<>"),

    /**
     * Greater than query operator.
     * used on Date,String,Integer,Long
     */
    GREATER_THAN(">"),
    /**
     * Greater equal query operator.
     * used on Date,String,Integer,Long
     */
    GREATER_EQUAL(">="),
    /**
     * Less than query operator.
     * used on Date,String,Integer,Long
     */
    LESS_THAN("<"),
    /**
     * Less equal query operator.
     * used on Date,String,Integer,Long
     */
    LESS_EQUAL("<="),
    /**
     * Include query operator.
     * query on %value%
     * only used on String
     */
    INCLUDE("like"),
    /**
     * Left include query operator.
     * query on value%
     * only used on String
     */
    LEFT_INCLUDE("like"),
    /**
     * Right include query operator.
     * query on %value
     * only used on String
     */
    RIGHT_INCLUDE("like"),
    /**
     * Isnull query operator.
     */
    IS_NULL("IS NULL"),
    /**
     * Isnotnull query operator.
     */
    IS_NOT_NULL("IS NOT NULL"),
    /**
     * In query operator.
     * only used on String,Integer,Long
     * value need Collection
     */
    IN("IN"),
    /**
     * NotIn query operator.
     * only used on String,Integer,Long
     * value need Collection
     */
    NOT_IN("NOT IN");

    private String operator;

    /**
     * Instantiates a new Order operator.
     *
     * @param operator the operator
     */
    QueryOperator(String operator){
        this.operator = operator;
    }

    @Override
    public String getOperator() {
        return this.operator;
    }
}
