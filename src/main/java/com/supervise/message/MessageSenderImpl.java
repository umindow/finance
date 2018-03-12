package com.supervise.message;

import cn.emay.ResultModel;
import cn.emay.eucp.inter.http.v1.dto.request.ReportRequest;
import cn.emay.eucp.inter.http.v1.dto.request.SmsBatchOnlyRequest;
import cn.emay.eucp.inter.http.v1.dto.request.SmsSingleRequest;
import cn.emay.eucp.inter.http.v1.dto.response.ReportResponse;
import cn.emay.eucp.inter.http.v1.dto.response.SmsResponse;
import cn.emay.util.AES;
import cn.emay.util.GZIPUtils;
import cn.emay.util.JsonHelper;
import cn.emay.util.http.*;
import com.supervise.common.Constants;
import com.supervise.config.message.MessageConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by WANGHANG on 2018/2/28.
 */
@Component("messageSenderImpl")
public class MessageSenderImpl implements MessageSender {

    @Autowired
    private MessageConf messageConf;

    private final Logger logger = LoggerFactory.getLogger(MessageSenderImpl.class);

    @Override
    public  void sendSMSData(String dataType,String batchDate) {
        logger.info("Message send Start ");
        // appId
        String appId = messageConf.getAppId();
        // 密钥
        String secretKey = messageConf.getSecretKey();
        // 接口地址
        String host = messageConf.getHost();//bjmtn.b2m.cn,shmtn.b2m.cn
        // 加密算法
        String algorithm = messageConf.getAlgorithm();// "AES/ECB/PKCS5Padding"
        // 编码
        String encode = messageConf.getEncode();
        // 是否压缩
        boolean isGizp = messageConf.isGizp();
        // 扩展码
       // String extendCode = "";

        String customSmsId = "";//自定义ID

        String phones = messageConf.getPhone();

        String sign ="【"+ messageConf.getSign()+"】";//放到发送内容前，作为签名，不然发送会报“签名错误”

        String content = createSendContent(dataType, batchDate);//构建短信内容

        String [] phoneList = phones.split(Constants.COMMA);

        // 发送单条短信
        setBatchOnlySms(appId, secretKey, host, algorithm, sign + content, customSmsId, phoneList, isGizp, encode);
        // 获取状态报告
        getReport(appId, secretKey, host, algorithm, isGizp, encode);
    }
        /**
         * 发送单条短信
         * @param isGzip 是否压缩
         */
        private  void setSingleSms(String appId,String secretKey,String host,String algorithm,String content, String customSmsId, String extendCode, String mobile,boolean isGzip,String encode) {
            logger.info("=============begin setSingleSms==================");
            SmsSingleRequest pamars = new SmsSingleRequest();
            pamars.setContent(content);
            pamars.setCustomSmsId(customSmsId);
            pamars.setExtendedCode(extendCode);
            pamars.setMobile(mobile);
            ResultModel result = request(appId,secretKey,algorithm,pamars, Constants.MESSAGE_HTTP + host + Constants.MESSAGE_HTTP_SENDSINGLESMS,isGzip,encode);
            logger.info("result code :" + result.getCode());
            if("SUCCESS".equals(result.getCode())){
                SmsResponse response = JsonHelper.fromJson(SmsResponse.class, result.getResult());
                if (response != null) {
                    logger.info("data : " + response.getMobile() + "," + response.getSmsId() + "," + response.getCustomSmsId());
                }
            }
            logger.info("=============end setSingleSms==================");
        }

    /**
     * 获取状态报告
     * @param isGzip 是否压缩
     */
    private  void getReport(String appId,String secretKey,String host,String algorithm,boolean isGzip,String encode) {
        logger.info("=============begin getReport==================");
        ReportRequest pamars = new ReportRequest();
        ResultModel result = request(appId,secretKey,algorithm,pamars, Constants.MESSAGE_HTTP + host + Constants.MESSAGE_HTTP_REPORT,isGzip,encode);
        logger.info("result code :" + result.getCode());
        if("SUCCESS".equals(result.getCode())){
            ReportResponse[] response = JsonHelper.fromJson(ReportResponse[].class, result.getResult());
            if (response != null) {
                for (ReportResponse d : response) {
                    logger.info("result data : " + d.getExtendedCode() + "," + d.getMobile() + "," + d.getCustomSmsId() + "," + d.getSmsId() + "," + d.getState() + "," + d.getDesc()
                            + "," + d.getSubmitTime() + "," + d.getReceiveTime());
                    if(!"成功".equalsIgnoreCase(d.getDesc())){
                        logger.error(d.getMobile()+"：短信发送失败");
                    }
                }
            }
        }
        logger.info("=============end getReport==================");
    }

    /**
     * 公共请求方法
     */
    private ResultModel request(String appId,String secretKey,String algorithm,Object content, String url,boolean isGzip,String encode) {
        System.out.println(url);
        Map<String, String> headers = new HashMap<String, String>();
        EmayHttpRequestBytes request = null;
        try {
            headers.put("appId", appId);
            headers.put("encode", encode);
            String requestJson = JsonHelper.toJsonString(content);
            logger.info("result json: " + requestJson);
            byte[] bytes = requestJson.getBytes(encode);
            logger.info("request data size : " + bytes.length);
            if (isGzip) {
                headers.put("gzip", "on");
                bytes = GZIPUtils.compress(bytes);
                logger.info("request data size [com]: " + bytes.length);
            }
            byte[] parambytes = AES.encrypt(bytes, secretKey.getBytes(), algorithm);
            logger.info("request data size [en] : " + parambytes.length);
            request = new EmayHttpRequestBytes(url, encode, "POST", headers, null, parambytes);
        } catch (Exception e) {
            logger.error("加密异常");
            e.printStackTrace();
        }
        EmayHttpClient client = new EmayHttpClient();
        String code = null;
        String result = null;
        try {
            EmayHttpResponseBytes res = client.service(request, new EmayHttpResponseBytesPraser());
            if(res == null){
                logger.error("请求接口异常");
                return new ResultModel(code, result);
            }
            if (res.getResultCode().equals(EmayHttpResultCode.SUCCESS)) {
                if (res.getHttpCode() == 200) {
                    code = res.getHeaders().get("result");
                    if (code.equals("SUCCESS")) {
                        byte[] data = res.getResultBytes();
                        logger.info("response data size [en and com] : " + data.length);
                        data = AES.decrypt(data, secretKey.getBytes(), algorithm);
                        if (isGzip) {
                            data = GZIPUtils.decompress(data);
                        }
                        logger.info("response data size : " + data.length);
                        result = new String(data, encode);
                        System.out.println("response json: " + result);
                    }
                } else {
                    logger.error("请求接口异常,请求码:" + res.getHttpCode());
                }
            } else {
                logger.error("请求接口网络异常:" + res.getResultCode().getCode());
            }
        } catch (Exception e) {
            logger.error("解析失败");
            e.printStackTrace();
        }
        ResultModel re = new ResultModel(code, result);
        return re;
    }

    /**
     * 发送批次短信
     * @param isGzip 是否压缩
     */
    private void setBatchOnlySms(String appId,String secretKey,String host,String algorithm,String content, String extendCode, String[] mobiles,boolean isGzip,String encode) {
        System.out.println("=============begin setBatchOnlySms==================");
        SmsBatchOnlyRequest pamars = new SmsBatchOnlyRequest();
        pamars.setMobiles(mobiles);
        pamars.setExtendedCode(extendCode);
        pamars.setContent(content);
        ResultModel result = request(appId,secretKey,algorithm,pamars, Constants.MESSAGE_HTTP + host + Constants.MESSAGE_HTTP_SENDBATCHSMS,isGzip,encode);
        System.out.println("result code :" + result.getCode());
        if("SUCCESS".equals(result.getCode())){
            SmsResponse[] response = JsonHelper.fromJson(SmsResponse[].class, result.getResult());
            if (response != null) {
                for (SmsResponse d : response) {
                    System.out.println("data:" + d.getMobile() + "," + d.getSmsId() + "," + d.getCustomSmsId());
                }
            }
        }
        System.out.println("=============end setBatchOnlySms==================");
    }

    /**
     * 构建内容
     * @param dataType
     * @param batchDate
     * @return
     */
    private String createSendContent(String dataType,String batchDate){
        StringBuffer str = new StringBuffer("");
        str.append("用户 您好！");
        str.append("数据发送异常！");
        str.append("数据类型为： ");
        str.append(dataType);
        str.append(",发送批次为： ");
        str.append(batchDate);
        return str.toString();
    }

}
