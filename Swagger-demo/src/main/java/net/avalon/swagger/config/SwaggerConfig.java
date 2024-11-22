package net.avalon.swagger.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Heda
 * @Create: 2024/11/22
 */
@Configuration
public class SwaggerConfig {


    @Bean
    public OpenAPI openApi(){
        return new OpenAPI()
                .info(new Info().title("Avalon")
                        .description("WeiYin の Eden")
                        .version("v1.0"));
    }
}
