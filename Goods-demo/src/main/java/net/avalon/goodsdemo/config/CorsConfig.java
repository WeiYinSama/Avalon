package net.avalon.goodsdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Description:
 *
 * @Author: Weiyin
 * @Create: 2023/2/23 - 16:49
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        /**
         * addMapping：配置可以被跨域的路径，可以任意配置，可以具体到直接请求路径。
         * allowedOrigins：允许所有的请求域名访问我们的跨域资源，可以固定单条或者多条内容，如：“http://www.aaa.com”，只有该域名可以访问我们的跨域资源。
         * allowedMethods：允许所有的请求方法访问该跨域资源服务器，如：POST、GET、PUT、DELETE等。
         * allowedHeaders：允许所有的请求header访问，可以自定义设置任意请求头信息。
         * maxAge: 配置客户端可以缓存来自预处理请求的响应的秒数。默认设置为1800秒(30分钟)。
         */
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS", "HEAD")
                .maxAge(3600);
    }
}
