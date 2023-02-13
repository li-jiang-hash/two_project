package com.aaa.project.config;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Data
@Component
public class AlipayConfig implements ApplicationRunner {

    /**
     * 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
     */
    @Value("${appId}")
    private String appId;

    /**
     * 商户私钥，您的PKCS8格式RSA2私钥
     */
    @Value("${privateKey}")
    private String privateKey;

    /**
     * 支付宝公钥,
     */
    @Value("${publicKey}")
    private String publicKey;

    /**
     * 服务器异步通知页面路径需http://格式的完整路径，不能加?id=123这类自定义参数
     */
    @Value("${notifyUrl}")
    private String notifyUrl;

    /**
     * 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数
     */
    @Value("${returnUrl}")
    private String returnUrl;

    /**
     * 签名方式
     */
    @Value("${signType}")
    private String signType;

    /**
     * 字符编码格式
     */
    @Value("${charset}")
    private String charset;

    /**
     * 支付宝网关
     */
    @Value("${gateway}")
    private String gatewayUrl;

    /**
     * 支付宝网关
     */
    @Value("${logPath}")
    private String logPath;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //初始化支付宝SDK
        Factory.setOptions(getOptions());
    }

    private Config getOptions() {
        //这里省略了一些不必要的配置，可参考文档的说明
        Config config = new Config();
        config.protocol = "https";
        config.gatewayHost = this.gatewayUrl;
        config.signType = "RSA2";
        config.appId = this.appId;

        // 为避免私钥随源码泄露，推荐从文件中读取私钥字符串而不是写入源码中
        config.merchantPrivateKey = this.privateKey;

        //注：如果采用非证书模式，则无需赋值上面的三个证书路径，改为赋值如下的支付宝公钥字符串即可
        config.alipayPublicKey = this.publicKey;

        //可设置异步通知接收服务地址（可选）
        config.notifyUrl = notifyUrl;

        return config;
    }
}
