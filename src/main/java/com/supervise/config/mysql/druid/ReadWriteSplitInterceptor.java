package com.supervise.config.mysql.druid;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;


/**
 */
@Aspect
@Component
public class ReadWriteSplitInterceptor implements Ordered {
    public static final Logger logger = LoggerFactory.getLogger(ReadWriteSplitInterceptor.class);

    @Before("execution(* tk.mybatis.mapper.common..*.delete*(..)) " +
            "|| execution(* tk.mybatis.mapper.common..*.insert*(..)) " +
            "|| execution(* tk.mybatis.mapper.common..*.update*(..))" +
            "|| execution(* com.supervise.dao.mysql.mapper.*.update*(..))")
    public void setWriteDataSourceType() {
        DbContextHolder.clearDbType();
        DbContextHolder.setDbType(DbContextHolder.DbType.MASTER);
        //logger.info("the current datasource is master");
    }

    @Before(" execution(* tk.mybatis.mapper.common..*.find*(..)) " +
            "||  execution(* tk.mybatis.mapper.common..*.select*(..)) " +
            "||  execution(* com.supervise.dao.mysql.mapper.*.select*(..)) " +
            "|| execution(* com.supervise.dao.mysql.mapper.*.find*(..)) " +
            "|| execution(* com.supervise.dao.mysql.mapper.*.get*(..))")
    public void setReadDataSourceType() {
        DbContextHolder.clearDbType();
        DbContextHolder.setDbType(DbContextHolder.DbType.SLAVE);
        //logger.info("the current datasource is slave");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
