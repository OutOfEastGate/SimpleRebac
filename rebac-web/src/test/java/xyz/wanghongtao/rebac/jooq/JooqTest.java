package xyz.wanghongtao.rebac.jooq;

import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.wanghongtao.rebac.repository.jooq.Config;

import java.sql.*;

/**
 * @author wanghongtao
 * @data 2023/9/4 20:08
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JooqTest {
  @Autowired
  Config jooqConfig;

  @Test
  public void testJooq() {
    String url = "jdbc:mysql://114.116.9.68:3306/SimpleRebac";
    String username = "root";
    String password = "19460";
    try(Connection connection = DriverManager.getConnection(url, username, password)) {
      DSLContext using = DSL.using(connection, SQLDialect.MYSQL);
      Result<Record> rebacKey = using.select().from("rebac_key").fetch();
      System.out.println(rebacKey);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
