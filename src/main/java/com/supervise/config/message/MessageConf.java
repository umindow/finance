package com.supervise.config.message;

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
@PropertySource(value = "classpath:/message.properties")
public class MessageConf {

    /**
     * 注册号ID
     */
    @Value("${message.appId}")
    private String appId;

    /**
     * 密钥
     */
    @Value("${message.secretKey}")
    private String secretKey;

    /**
     * 亿美短信平台地址
     */
    @Value("${message.host}")
    private String host;

    /**
     * 加密算法
     */
    @Value("${message.algorithm}")
    private String algorithm;


    /**
     * 编码
     */
    @Value("${message.encode}")
    private String encode;

    /**
     * 电话号码
     */
    @Value("${message.phone}")
    private String phone;

    /**
     * 签名
     */
    @Value("${message.sign}")
    private String sign;

    /**
     * 是否压缩
     */
    @Value("${message.isGizp}")
    private boolean isGizp;

}
