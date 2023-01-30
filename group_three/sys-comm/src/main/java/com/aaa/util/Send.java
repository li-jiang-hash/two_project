package com.yyl.util;


import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.tea.TeaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/*
 * @Author: 江Sir
 * @Date: 2023/1/5 11:47:41
 * @Description:
 * @Since version:  2022.10
 */
public class Send {
    public static com.aliyun.dysmsapi20170525.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                // 必填，您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 必填，您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new com.aliyun.dysmsapi20170525.Client(config);
    }

    public static Boolean SendCode(String AccessKey_ID,String AccessKey_Secret,String templateCode,String phone,String code) throws Exception {
        java.util.List<String> args = java.util.Arrays.asList();
        // 工程代码泄露可能会导致AccessKey泄露，并威胁账号下所有资源的安全性。以下代码示例仅供参考，建议使用更安全的 STS 方式，更多鉴权访问方式请参见：https://help.aliyun.com/document_detail/378657.html
        com.aliyun.dysmsapi20170525.Client client = Send.createClient(AccessKey_ID, AccessKey_Secret); //LTAI5tGB7EXHyP8uHBBCZhK6
        System.out.println("AccessKey_ID = " + AccessKey_ID);
        System.out.println("AccessKey_Secret = " + AccessKey_Secret);
        com.aliyun.dysmsapi20170525.models.SendSmsRequest sendSmsRequest = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
                .setSignName("李江的博客")
                .setTemplateCode(templateCode)
                .setPhoneNumbers(phone)
                .setTemplateParam("{\"code\":"+code+"}");
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        try {
            // 复制代码运行请自行打印 API 的返回值
            SendSmsResponse sendSmsResponse = client.sendSmsWithOptions(sendSmsRequest, runtime);
            System.out.println("123"+sendSmsResponse.getStatusCode());
            return sendSmsResponse.getStatusCode() == 200;
        } catch (TeaException error) {
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        }
        return false;
    }

//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;

    public static void main(String[] args) throws Exception {

//        redisTemplate.opsForValue().set("18749021423","552157",5, TimeUnit.SECONDS);
        Send.SendCode("", "", "", "", "552157");
    }
}
