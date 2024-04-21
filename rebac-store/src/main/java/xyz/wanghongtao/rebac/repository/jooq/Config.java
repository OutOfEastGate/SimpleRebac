package xyz.wanghongtao.rebac.repository.jooq;

import lombok.extern.slf4j.Slf4j;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author wanghongtao
 * @data 2023/9/4 19:59
 */
@Slf4j
@Component
public class Config {
  @Value("${spring.datasource.url}")
  String datasourceUrl;
  @Value("${wht.back.wifiMachine}")
  String wifiMachine;

  String url = "jdbc:mysql://" + wifiMachine +":3306/SimpleRebac";

  @Value("${spring.datasource.username}")
  String username;

  @Value("${spring.datasource.password}")
  String password;


  @Bean
  public Connection getConnection() {
    String url = "jdbc:mysql://" + wifiMachine +":3306/SimpleRebac";
    String username = "root";
    String password = "19460";
    Connection connection;
    try {
      connection = DriverManager.getConnection(url, username, password);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return connection;
  }

  @Bean
  public DSLContext getContext(Connection connection) {
    DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);
    return dslContext;
  }

}
