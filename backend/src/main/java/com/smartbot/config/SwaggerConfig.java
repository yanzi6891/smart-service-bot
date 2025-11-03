package com.smartbot.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger/Knife4j配置
 *
 * @author SmartBot Team
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("smart-service-bot API文档")
                        .version("v1.0.0")
                        .description("企业级智能AI客服系统接口文档")
                        .contact(new Contact()
                                .name("SmartBot Team")
                                .email("support@smartservicebot.com")
                                .url("https://github.com/yourusername/smart-service-bot"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
}
