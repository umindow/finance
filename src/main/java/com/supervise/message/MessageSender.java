package com.supervise.message;

/**
 * Created by xishui.hb on 2018/1/30 下午6:26.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public interface MessageSender {

    /**
     * 发送短信
     * @param  dataType 数据类型
     * @param  batchDate 发送批次
     *    return
     */
    public void sendSMSData(String dataType,String batchDate);

}
