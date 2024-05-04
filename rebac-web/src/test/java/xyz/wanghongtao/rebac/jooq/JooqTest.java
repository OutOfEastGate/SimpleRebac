package xyz.wanghongtao.rebac.jooq;

import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.BuiltInDataType;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.wanghongtao.rebac.object.dataObject.UserDo;
import xyz.wanghongtao.rebac.repository.jooq.Config;

import java.lang.reflect.Field;
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

  private DSLContext context() throws SQLException {
    String url = "jdbc:mysql://localhost:3306/SimpleRebac";
    String username = "root";
    String password = "19460";
    Connection connection = DriverManager.getConnection(url, username, password);
    return DSL.using(connection, SQLDialect.MYSQL);
  }

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

  @Test
  public void testJooqCreateDatabase() throws SQLException {
    String url = "jdbc:mysql://114.116.9.68:3306/SimpleRebac";
    String username = "root";
    String password = "19460";
    context().createDatabaseIfNotExists("testDatabase").execute();
  }

  @Test
  public void testJooqCreateUserTable() throws SQLException {
    context().createTableIfNotExists("rebac_user")
      .column(DSL.field("id", SQLDataType.BIGINT.identity(true)))
      .column("username", SQLDataType.VARCHAR)
      .column("password", SQLDataType.VARCHAR)
      .constraint(DSL.constraint().primaryKey("id"))
      .execute();
  }

  @Test
  public void testJooqCreateKeyTable() throws SQLException {
    context().createTableIfNotExists("rebac_key")
      .column(DSL.field("id", SQLDataType.BIGINT.identity(true)))
      .column("app_name", SQLDataType.VARCHAR)
      .column("app_icon", SQLDataType.VARCHAR)
      .column("app_key", SQLDataType.VARCHAR)
      .column("secret_key", SQLDataType.VARCHAR)
      .constraint(DSL.constraint().primaryKey("id"))
      .execute();
  }

  @Test
  public void testJooqCreateModelTable() throws SQLException {
    context().createTableIfNotExists("rebac_model")
      .column(DSL.field("id", SQLDataType.BIGINT.identity(true)))
      .column("store_id", SQLDataType.BIGINT)
      .column("name", SQLDataType.VARCHAR)
      .column("description", SQLDataType.VARCHAR)
      .column("policy_id", SQLDataType.VARCHAR)
      .constraint(DSL.constraint().primaryKey("id"))
      .execute();
  }

  @Test
  public void testJooqCreateRelationTable() throws SQLException {
    context().createTableIfNotExists("rebac_relation")
      .column(DSL.field("id", SQLDataType.BIGINT.identity(true)))
      .column("model_id", SQLDataType.BIGINT)
      .column("object_type", SQLDataType.VARCHAR)
      .column("object", SQLDataType.VARCHAR)
      .column("relation", SQLDataType.VARCHAR)
      .column("is_penetrate", SQLDataType.TINYINT)
      .column("subject_type", SQLDataType.VARCHAR)
      .column("subject", SQLDataType.VARCHAR)
      .column("triple", SQLDataType.VARCHAR)
      .constraint(DSL.constraint().primaryKey("id"))
      .execute();
  }

  @Test
  public void testJooqCreateStoreTable() throws SQLException {
    context().createTableIfNotExists("rebac_store")
      .column(DSL.field("id", SQLDataType.BIGINT.identity(true)))
      .column("name", SQLDataType.VARCHAR)
      .column("description", SQLDataType.VARCHAR)
      .column("app_key", SQLDataType.VARCHAR)
      .constraint(DSL.constraint().primaryKey("id"))
      .execute();
  }
}
