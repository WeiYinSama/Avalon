package net.avalon.alipay.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Weiyin
 * @Create: 2023/4/27 - 7:33
 */
@Configuration
public class AliPayConfig {

    @Value("${alipay.privateKey}")
    private String PRIVATE_KEY;

    @Value("${alipay.alipayPublicKey}")
    private String ALIPAY_PUBLIC_KEY;

    @Value("${alipay.alipayGateway}")
    private String ALIPAY_GATEWAY;

    @Value("${alipay.appId}")
    private String ALIPAY_APPID;

    @Bean
    public AlipayClient alipayClient() throws AlipayApiException {
        AlipayConfig alipayConfig = new AlipayConfig();
        //支付宝网关（固定）。
        alipayConfig.setServerUrl(ALIPAY_GATEWAY);
        //APPID 即创建应用后生成。
        alipayConfig.setAppId(ALIPAY_APPID);
        //开发者私钥，由开发者自己生成。
        alipayConfig.setPrivateKey(PRIVATE_KEY);
        //支付宝公钥，由支付宝生成。
        alipayConfig.setAlipayPublicKey(ALIPAY_PUBLIC_KEY);
        //构造client
        return new DefaultAlipayClient(alipayConfig);
    }
}
