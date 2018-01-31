package com.supervise.core.data.spi;

import com.alibaba.fastjson.JSON;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * Created by xishui.hb on 2018/1/31 上午9:27.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public class QueryMain<D> {

    private final Logger logger = LoggerFactory.getLogger(QueryMain.class);
    @Setter
    private DataConfig<D> dataConfig;

    public static <D> QueryMain<D> newQuery(DataConfig<D> dDataConfig) {
        QueryMain<D> queryMain = new QueryMain<D>();
        if(null == dDataConfig){
            throw  new NullPointerException("Create QueryMain Fail, DataConfig is null.");
        }
        if (null == dDataConfig.getDataMapper() || null == dDataConfig.getJdbcDriver() || null == dDataConfig.getMysqlJdbcUrl()
                || null == dDataConfig.getUserName() || null == dDataConfig.getPassword() || null == dDataConfig.getSql()) {
            throw new NullPointerException("Create QueryMain Fail,Param has Null Filed,Param:" + JSON.toJSONString(dDataConfig));
        }
        queryMain.setDataConfig(dDataConfig);
        return queryMain;
    }

    public D query() {
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName(dataConfig.getJdbcDriver());//加载驱动
            Properties properties = new Properties();
            properties.setProperty("user", dataConfig.getUserName());
            properties.setProperty("password", dataConfig.getPassword());
            connection = DriverManager.getConnection(dataConfig.getMysqlJdbcUrl(), properties);
            if (null == connection) {
                throw new NullPointerException("获取数据库链接Connection失败,Param:" + JSON.toJSONString(dataConfig));
            }
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(dataConfig.getSql());
            if (null == resultSet) {
                throw new NullPointerException("查询数据失败,Param:" + JSON.toJSONString(dataConfig));
            }
            return dataConfig.getDataMapper().mapper(resultSet);
        } catch (Exception e) {
            logger.error("QueryMain query Err.", e.getMessage());
        } finally {
            try {
                if (null != statement) {
                    statement.cancel();
                }
                if (null != connection) {
                    connection.close();
                }
            } catch (Exception e) {
                logger.error("关闭数据库链接失败,Param:" + JSON.toJSONString(dataConfig));
            }
        }
        return null;
    }

}
