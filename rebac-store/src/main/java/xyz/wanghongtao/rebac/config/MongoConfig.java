package xyz.wanghongtao.rebac.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class MongoConfig {
    @Value("${spring.data.mongodb.uri}")
    private String mongodbUri;

    @Bean
    @ConditionalOnProperty(name = "spring.data.mongodb.uri")
    public MongoClient mongoClient() {
        try {
            return MongoClients.create(mongodbUri);
        } catch (Exception e) {
            // 连接失败时，打印异常信息
            log.error("连接mongo异常");
            log.error(e.getMessage());
            return null;
        }
    }
}
