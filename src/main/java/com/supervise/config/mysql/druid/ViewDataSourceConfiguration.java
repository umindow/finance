package com.supervise.config.mysql.druid;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(basePackages = {"com.supervise.dao.mysql.viewmapper"}, sqlSessionFactoryRef = "sqlSessionFactorysforView")
//@ComponentScan(value = {"com.supervise.dao.mysql"})
public class ViewDataSourceConfiguration {

    @Value("${druid.view.type}")
    private Class<? extends DataSource> dataSourceType;

    @Bean(name = "viewDataSource")
    @ConfigurationProperties(prefix = "druid.view")
    public DataSource viewDataSource() {
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactorysforView() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(viewDataSource());
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
        return sqlSessionFactory;
    }

//    @Bean
//    DataSourceTransactionManager dataSourceTransactionManager() {
//        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(viewDataSource());
//        return dataSourceTransactionManager;
//    }
    
    @Bean
    public SqlSessionTemplate sqlSessionViewTemplate() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactorysforView()); // 使用上面配置的Factory
        return template;
    }
}
