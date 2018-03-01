package com.supervise.config.mail;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * ClassName: MailConf <br/>
 * Description: Mail configuration <br/>
 * @version 1.0 <br/>
 */
@Getter
@Setter
@Configuration
@PropertySource(value = "classpath:/mail.properties")
public class MailConf {

    /**
     * 使用的协议
     */
    @Value("${mail.transport.protocol}")
    private String protocol;

    /**
     * 发件人的邮箱的 SMTP 服务器地址
     */
    @Value("${mail.smtp.host}")
    private String smtphostFrom;

    /**
     * 需要请求认证
     */
    @Value("${mail.smtp.auth}")
    private String auth;

    /**
     * 发送方账号
     */
    @Value("${mail.from}")
    private String fromAccount;

    /**
     * 接收方账号
     */
    @Value("${mail.to}")
    private String toAccount;

    /**
     * 发送方账号密码
     */
    @Value("${mail.frommAccount.password}")
    private String password;

}
