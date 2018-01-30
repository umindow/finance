package com.supervise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

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
public class SuperviseFinanceApplication extends SpringBootServletInitializer {

    /**
     * 支持外部J2EE容器启动
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SuperviseFinanceApplication.class);
    }

    public static void main(String[] args) {

        SpringApplication.run(SuperviseFinanceApplication.class, args);
    }
}
