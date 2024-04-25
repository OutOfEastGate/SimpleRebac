package xyz.wanghongtao.rebac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

/**
 * @author wanghongtao
 * @data 2023/7/16 15:53
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, MongoAutoConfiguration.class})
public class WebStarter {
    public static void main(String[] args) {
        SpringApplication.run(WebStarter.class,args);
    }
}
