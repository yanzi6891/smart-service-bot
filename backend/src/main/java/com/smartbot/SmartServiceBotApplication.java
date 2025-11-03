package com.smartbot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * smart-service-bot 应用启动类
 *
 * @author SmartBot Team
 * @version 1.0.0
 * @since 2024-01-01
 */
@SpringBootApplication
@EnableCaching
@EnableAsync
@MapperScan("com.smartbot.dao.mapper")
public class SmartServiceBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartServiceBotApplication.class, args);
        System.out.println("""

            ====================================================================================================
                 ____                       _   ____                  _          ____        _
                / ___| _ __ ___   __ _ _ __| |_/ ___|  ___ _ ____   _(_) ___ ___| __ )  ___ | |_
                \\___ \\| '_ ` _ \\ / _` | '__| __\\___ \\ / _ \\ '__\\ \\ / / |/ __/ _ \\  _ \\ / _ \\| __|
                 ___) | | | | | | (_| | |  | |_ ___) |  __/ |   \\ V /| | (_|  __/ |_) | (_) | |_
                |____/|_| |_| |_|\\__,_|_|   \\__|____/ \\___|_|    \\_/ |_|\\___\\___|____/ \\___/ \\__|

                :: smart-service-bot :: 企业级智能AI客服系统 :: v1.0.0 :: 启动成功 ::
                :: API文档地址: http://localhost:8080/doc.html ::
            ====================================================================================================
            """);
    }
}
