package com.supervise;

import com.supervise.schedule.QuartzScheduleInitizing;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

/**
 * Created by xishui on 2018/1/30 上午9:35.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@SpringBootApplication
@Import(value = {MybatisAutoConfiguration.class})
public class SuperviseFinanceApplication extends SpringBootServletInitializer {

    /**
     * 支持外部J2EE容器启动
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SuperviseFinanceApplication.class);
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context =  SpringApplication.run(SuperviseFinanceApplication.class, args);
        //启动定时任务加载.
        QuartzScheduleInitizing initizing = context.getBean(QuartzScheduleInitizing.class);
        if(null != initizing){
            initizing.initDbSchedule();
        }
    }
}
