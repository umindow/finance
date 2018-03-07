package com.supervise.config.mail;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * ClassName: MailConf <br/>
 * Description: Mail configuration <br/>
 * @version 1.0 <br/>
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "mail")
public class MailConf {

    /**
     * 使用的协议
     */
    private String transportProtocol;

    /**
     * 发件人的邮箱的 SMTP 服务器地址
     */
    private String smtpHost;

    /**
     * 需要请求认证
     */
    private String smtpAuth;

    /**
     * 发送方账号
     */
    private String from;

    /**
     * 接收方账号
     */
    private String to;

    /**
     * 发送方账号密码
     */
    private String fromAccountPassword;

}
