package com.supervise.config.mysql.druid;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@MapperScan(basePackages = {"com.supervise.dao.mysql.mapper"}, sqlSessionFactoryRef = "sqlSessionFactorys")
//@ComponentScan(value = {"com.supervise.dao.mysql"})
public class DataSourceConfiguration {

    @Value("${druid.type}")
    private Class<? extends DataSource> dataSourceType;

    @Bean(name = "masterDataSource")
    @ConfigurationProperties(prefix = "druid.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    @Bean(name = "slaveDataSource")
    @ConfigurationProperties(prefix = "druid.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    @Bean("readWriteSplitRoutingDataSource")
    @Primary
    @DependsOn({"masterDataSource", "slaveDataSource"})
    public ReadWriteSplitRoutingDataSource dynamicDataSource() {
        ReadWriteSplitRoutingDataSource proxy = new ReadWriteSplitRoutingDataSource();
        Map<Object, Object> targetDataResources = new HashMap<Object, Object>();
        targetDataResources.put(DbContextHolder.DbType.MASTER, masterDataSource());
        targetDataResources.put(DbContextHolder.DbType.SLAVE, slaveDataSource());
        proxy.setDefaultTargetDataSource(masterDataSource());
        proxy.setTargetDataSources(targetDataResources);
        return proxy;
    }

    @Bean
    @Primary
    public SqlSessionFactory sqlSessionFactorys() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
        return sqlSessionFactory;
    }

    @Bean
    @Primary
    DataSourceTransactionManager dataSourceTransactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dynamicDataSource());
        return dataSourceTransactionManager;
    }
    
    @Bean
    @Primary
    public SqlSessionTemplate sqlSessionTemplate1() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactorys()); // 使用上面配置的Factory
        return template;
    }
}
