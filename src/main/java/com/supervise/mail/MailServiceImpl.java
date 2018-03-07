package com.supervise.mail;

import com.supervise.common.Constants;
import com.supervise.config.mail.MailConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
/**
 * Created by WANGHANG on 2018/2/28.
 */
@Component("mailServiceImpl")
public class MailServiceImpl implements MailService {

    @Autowired
    private MailConf mailConf;

    private final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    // 发件人的 邮箱 和 密码（替换为自己的邮箱和密码）
    // PS: 某些邮箱服务器为了增加邮箱本身密码的安全性，给 SMTP 客户端设置了独立密码（有的邮箱称为“授权码”）,
    //     对于开启了独立密码的邮箱, 这里的邮箱密码必需使用这个独立密码（授权码）。
//    public static String myEmailAccount = "cqforeye@126.com";
//    public static String myEmailPassword = "wanghang521";

    // 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般(只是一般, 绝非绝对)格式为: smtp.xxx.com
    // 网易163邮箱的 SMTP 服务器地址为: smtp.163.com
//    public static String myEmailSMTPHost = "smtp.126.com";

    // 收件人邮箱（替换为自己知道的有效邮箱）
//    public static String receiveMailAccount = "cqforeye@126.com";

    @Override
    public  void sendEmailData(String dataType,String batchDate){

        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();                    // 参数配置
        props.setProperty(Constants.MAIL_TRANSPORT_PROTOCOL, mailConf.getTransportProtocol());   // 使用的协议（JavaMail规范要求）
        props.setProperty(Constants.MAIL_SMTP_HOST, mailConf.getSmtpHost());   // 发件人的邮箱的 SMTP 服务器地址
        // 发送服务器需要身份验证
        props.setProperty(Constants.MAIL_SMTP_AUTH, mailConf.getSmtpAuth());

// 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getInstance(props);
        session.setDebug(false);                                 // 设置为debug模式, 可以查看详细的发送 log

        // 3. 创建一封邮件
        Transport transport = null;
        try{
            MimeMessage message = createMimeMessage(session, mailConf.getFrom(), mailConf.getTo(),dataType,batchDate);
            //4. 根据 Session 获取邮件传输对象
            transport = session.getTransport();
            // 5. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
            transport.connect(mailConf.getFrom(), mailConf.getFromAccountPassword());
            // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(message, message.getAllRecipients());
            logger.error("Mail send success at :"+ new SimpleDateFormat("yyyy-MM-dd-HH").format(new Date()));
        }
        catch (Exception e){
            logger.error("Mail send fail at :"+ new SimpleDateFormat("yyyy-MM-dd-HH").format(new Date()));
            logger.error("Mail exception :"+ e.getMessage());
        }finally {
            // 7. 关闭连接
            if(null!=transport){
                try{
                    transport.close();
                }
                catch (MessagingException e){
                    logger.error("Mail connect close fail at :"+ new SimpleDateFormat("yyyy-MM-dd-HH").format(new Date()));
                    logger.error("MessagingException :"+ e.getMessage());
                }

            }
        }
    }

    /**
     * 创建一封只包含文本的简单邮件
     *
     * @param session 和服务器交互的会话
     * @param sendMail 发件人邮箱
     * @param receiveMail 收件人邮箱
     * @return
     * @throws Exception
     */
    private  MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail,String dateType,String batchDate) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);

        // 2. From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
        message.setFrom(new InternetAddress(sendMail, "", "UTF-8"));

        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "", "UTF-8"));

        // 4. Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）
        message.setSubject("Warning Test", "UTF-8");

        // 5. Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
       String content = createSendContent(dateType,batchDate);
        message.setContent(content, "text/html;charset=UTF-8");

        // 6. 设置发件时间
        message.setSentDate(new Date());

        // 7. 保存设置
        message.saveChanges();

        return message;
    }

    /**
     * 构建邮件内容
     * @param dataType
     * @param batchDate
     * @return
     */
    private String createSendContent(String dataType,String batchDate){
        StringBuffer str = new StringBuffer("");
        str.append("用户 您好！");
        str.append("<br>");
        str.append("数据发送异常！");
        str.append("<br>");
        str.append("数据类型为： ");
        str.append(dataType);
        str.append("<br>");
        str.append("发送批次为： ");
        str.append(batchDate);
        str.append("<br>");
        return str.toString();
    }

}
