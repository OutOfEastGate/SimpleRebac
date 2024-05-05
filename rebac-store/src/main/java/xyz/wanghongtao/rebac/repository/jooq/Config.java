package xyz.wanghongtao.rebac.repository.jooq;

import lombok.extern.slf4j.Slf4j;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import xyz.wanghongtao.rebac.config.MockDataSource;
import xyz.wanghongtao.rebac.object.config.RuntimeConfigParam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author wanghongtao
 * @date 2023/9/4 19:59
 */
@Slf4j
@Component
public class Config {
  @Value("${spring.datasource.url}")
  String datasourceUrl;
  private final RuntimeConfigParam runtimeConfigParam;


  @Value("${spring.datasource.username}")
  String username;

  @Value("${spring.datasource.password}")
  String password;

  public Config(RuntimeConfigParam runtimeConfigParam) {
    this.runtimeConfigParam = runtimeConfigParam;
  }


//  @Bean
//  public DSLContext getContext() {
//    if(runtimeConfigParam.getMockDatabase()) {
//      return DSL.using((new MockDataSource()).getConnection());
//    }
//    String url = "jdbc:mysql://" + runtimeConfigParam.getWifiMachine() +":3306/SimpleRebac";
//    Connection connection = null;
//    try {
//      connection = DriverManager.getConnection(url, username, password);
//    } catch (SQLException e) {
//      log.error("jooq连接异常");
//    }
//    DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);
//    if (runtimeConfigParam.getCreateDatabase()) {
//     dslContext.createTableIfNotExists("rebac_user")
//        .column(DSL.field("id", SQLDataType.BIGINT.identity(true)))
//        .column("username", SQLDataType.VARCHAR)
//        .column("password", SQLDataType.VARCHAR)
//        .constraint(DSL.constraint().primaryKey("id"))
//        .execute();
//      dslContext.createTableIfNotExists("rebac_key")
//        .column(DSL.field("id", SQLDataType.BIGINT.identity(true)))
//        .column("app_name", SQLDataType.VARCHAR)
//        .column("app_icon", SQLDataType.VARCHAR)
//        .column("app_key", SQLDataType.VARCHAR)
//        .column("secret_key", SQLDataType.VARCHAR)
//        .constraint(DSL.constraint().primaryKey("id"))
//        .execute();
//      dslContext.createTableIfNotExists("rebac_model")
//        .column(DSL.field("id", SQLDataType.BIGINT.identity(true)))
//        .column("store_id", SQLDataType.BIGINT)
//        .column("name", SQLDataType.VARCHAR)
//        .column("description", SQLDataType.VARCHAR)
//        .column("policy_id", SQLDataType.VARCHAR)
//        .constraint(DSL.constraint().primaryKey("id"))
//        .execute();
//      dslContext.createTableIfNotExists("rebac_relation")
//        .column(DSL.field("id", SQLDataType.BIGINT.identity(true)))
//        .column("model_id", SQLDataType.BIGINT)
//        .column("object_type", SQLDataType.VARCHAR)
//        .column("object", SQLDataType.VARCHAR)
//        .column("relation", SQLDataType.VARCHAR)
//        .column("is_penetrate", SQLDataType.TINYINT)
//        .column("subject_type", SQLDataType.VARCHAR)
//        .column("subject", SQLDataType.VARCHAR)
//        .column("triple", SQLDataType.VARCHAR)
//        .constraint(DSL.constraint().primaryKey("id"))
//        .execute();
//      dslContext.createTableIfNotExists("rebac_store")
//        .column(DSL.field("id", SQLDataType.BIGINT.identity(true)))
//        .column("name", SQLDataType.VARCHAR)
//        .column("description", SQLDataType.VARCHAR)
//        .column("app_key", SQLDataType.VARCHAR)
//        .constraint(DSL.constraint().primaryKey("id"))
//        .execute();
//    }
//    return dslContext;
//  }

}
