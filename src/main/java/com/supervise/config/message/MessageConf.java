package com.supervise.config.message;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * ClassName: MailConf <br/>
 * Description: Mail configuration <br/>
 *
 * @version 1.0 <br/>
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "message")
@Component
public class MessageConf {
    /**
     * 注册号ID
     */
    private String appId;
    /**
     * 密钥
     */
    private String secretKey;
    /**
     * 亿美短信平台地址
     */
    private String host;
    /**
     * 加密算法
     */
    private String algorithm;


    /**
     * 编码
     */
    private String encode;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 签名
     */
    private String sign;

    /**
     * 是否压缩
     */
    private boolean isGizp;

}
