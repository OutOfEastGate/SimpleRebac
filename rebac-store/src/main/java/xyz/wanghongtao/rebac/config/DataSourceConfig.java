package xyz.wanghongtao.rebac.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class DataSourceConfig {
    @Value("${spring.datasource.url}")
    private String datasourceUrl;
    @Value("${spring.datasource.username}")
    private String datasourceUsername;
    @Value("${spring.datasource.password}")
    private String datasourcePassword;
    @Value("${wht.back.mockDatabase}")
    private Boolean mockDatabase;

    @Bean
    @ConditionalOnProperty(name = "spring.datasource.url")
    public DataSource dataSource() {
        if(mockDatabase) {
          log.info("启用模拟数据库");
          return new MockDataSource();
        }
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(datasourceUrl);
        hikariConfig.setUsername(datasourceUsername);
        hikariConfig.setPassword(datasourcePassword);

        try {
            return new HikariDataSource(hikariConfig);
        } catch (Exception e) {
            // 连接失败时，打印异常信息
            log.error("连接mysql异常");
            log.error(e.getMessage());
            return new MockDataSource();
        }
    }
}
