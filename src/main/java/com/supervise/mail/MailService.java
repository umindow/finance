package com.supervise.mail;

/**
 * Created by WANGHANG on 2018/2/28.
 */
public interface MailService {
    /**
     * 发送邮件
     * @param  dataType 数据类型
     * @param  batchDate 发送批次
     * return
     */
    public void sendEmailData(String dataType,String batchDate);

}
