package xyz.wanghongtao.rebac.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wanghongtao
 * @data 2023/7/29 15:58
 */
@Data
@Component
@ConfigurationProperties(prefix = "neo4j")
public class Neo4jConfigProperties {
    String uri;

    String username;

    String password;
}
