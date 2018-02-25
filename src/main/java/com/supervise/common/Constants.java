package com.supervise.common;

/**
 * Created by xishui.hb on 2018/1/30 下午3:00.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public final class Constants {


    public final static class SessionContant{
        public static final String USER_SESSION_KEY = "user.session.key";
    }

    public static final Integer PAGE_SIZE = 10;
    
    /***************************分隔符常量 begin******************************/

	/**
	 * 反斜线标识
	 */
	public static final String BACK_SLASH="/";

	/**
	 * 行间隔标识
	 */
	public static final String LINE_FLAG = "-";

	//下划线
	public static final String UNDER_LINE = "_";

	//双斜杠标识
	public static final String DOUBLE_SLASH = "\\";

	/**
	 * 回车换行标识
	 */
	public static final String ENTER_NEW_LINE = "\r\n";

	/**
	 * 回车标识
	 */
	public static final String ENTER = "\r";

	/**
	 * 换行标识
	 */
	public static final String NEW_LINE = "\n";

	/**
	 * .分隔符
	 */
	public static final String DOT = ".";

	/**
	 * #分隔符
	 */
	public static final String POUND = "#";

	/**
	 * 分号分隔符
	 */
	public static final String SEMICOLON = ";";

	/**
	 * 冒号分隔符
	 */
	public static final String COLON = ":";

	/**
	 * 空格分隔符
	 */
	public static final String SPACE = " ";

	/**
	 * 逗号分隔符
	 */
	public static final String COMMA = ",";

	/**
	 * 等号
	 */
	public static final String EQUAL = "=";

	/**
	 * 左小括号
	 */
	public static final String LEFT_PARENTHESIS = "(";

	/**
	 * 右小括号
	 */
	public static final String RIGHT_PARENTHESIS = ")";

	/**
	 * 左大括号
	 */
	public static final String LEFT_BRACES = "{";

	/**
	 * 右大括号
	 */
	public static final String RIGHT_BRACES = "}";

	/**
	 * 单引号
	 */
	public static final String SINGLE_QUOTE = "'";

	/**
	 * 空字符
	 */
	public static final String NULLSTR = "";

	/**
	 * 空字符
	 */
	public static final String BLANKSTR = " ";

	/***************************分隔符常量 end********************************/
	
	 /*****************************SQL相关常量 begin**************************/
	 /**
     *时间转换函数
     */
    public static final String DATE_FORMATE = "DATE_FORMAT";

    /**
     * 连接符AND
     */
    public static final String CONNECTOR_AND = "AND";

    /**
     * 连接符OR
     */
    public static final String CONNECTOR_OR = "OR";

    /**
     * 连接符WHERE
     */
    public static final String CONNECTOR_WHERE = "WHERE";

    /**
     * 关键字ORDER BY
     */
    public static final String KEY_ORDER_BY = "ORDER BY";

    /**
     * 升序排序关键字
     */
    public static final String KEY_ASC = "ASC";

    /**
     * 降序排序关键字
     */
    public static final String KEY_DESC = "DESC";

    /**
     * 关键字INSERT INTO
     */
    public static final String KEY_INSERT_INTO = "INSERT INTO";

    /**
     * 关键字VALUES
     */
    public static final String KEY_VALUES = "VALUES";

    /**
     * 关键字REPLACE
     */
    public static final String KEY_REPLACE = "REPLACE";

    /**
     * NULLS
     */
    public static final String KEY_NULLS = "NULLS";

    /**
     * FIRST
     */
    public static final String KEY_FIRST = "FIRST";

    /**
     * LAST
     */
    public static final String KEY_LAST = "LAST";

    /**
     * nvl(value,0)-如果value为空则替换为0
     */
    public static final String KEY_NVL = "NVL";

    /**
     * length关键字，取字段长度
     */
    public static final String KEY_LENGTH = "LENGTH";

    /**
     * 字符串类型的jdbc type
     */
    public static final String JDBC_TYPE_VARCHAR = "VARCHAR";

    /**
     * Number类型的jdbc type
     */
    public static final String JDBC_TYPE_NUMBER = "BIGINT";

    /**
     * Date类型的jdbc type
     */
    public static final String JDBC_TYPE_DATE = "DATE";
    /*****************************SQL相关常量 end****************************/
    
    /***************************时间格式常量 start********************************/
    public static final String YYYYMMDDHHMMSSS = "yyyyMMddHHmmss:SSS";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String MYSQL_YYYY_MM_DD_HH_MM_SS = "%Y-%m-%d %H:%i:%s";
    public static final String DDMMMYY = "ddMMMyy";
    public static final String OPEN_DATE = "00XXX00";
    public static final String DAY_MIN_DATE = "00:00:00";
    public static final String DAY_MAX_DATE = "23:59:59";
    /*****************************时间格式常量 end****************************/
    
    
	/***************************数据库表以及视图常量 start********************************/
	public static final String FINANCE_REPAYMENT_INFO = "finance_repayment_info";
	public static final String REPAYMENT_VIEW = "repayment_info_view";
	/***************************数据库表以及视图常量 end********************************/
	
	
	/***************************定时任务常量 start********************************/
	public static final String SCH_DATA_LOADED_SCHEDULE = "data-loaded-schedule";
	public static final String SCH_BANK_TRUST_SCH = "bank_trust_sch";
	/***************************定时任务常量常量 end********************************/
}
