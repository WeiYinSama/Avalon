package net.avalon.feign.config;

import feign.Logger;
import feign.Retryer;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Heda
 * @Create: 2024/11/26
 */
@Configuration
public class FeignConfig {


    /**
     * 配置 OpenFeign 的重试策略
     * @return
     */
//    @Bean
    public Retryer retryer(){
        /**
         * period：初始重试间隔，单位 ms
         * maxPeriod：最大重试间隔，单位 s
         * maxAttempts：重试次数
         */
        return new Retryer.Default(100,1,3);

        // 默认不重试
//        return Retryer.NEVER_RETRY;
    }

    /**
     * 配置 OpenFeign 的日志内容
     * @return
     */
//    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
