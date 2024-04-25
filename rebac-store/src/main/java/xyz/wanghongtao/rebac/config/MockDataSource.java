package xyz.wanghongtao.rebac.config;

import org.jooq.tools.jdbc.MockConnection;
import org.jooq.tools.jdbc.MockFileDatabase;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-04-24
 */
public class MockDataSource implements DataSource {
  @Override
  public Connection getConnection() throws SQLException {
    InputStream resourceAsStream = getClass().getResourceAsStream("/mockDatabase");
    try {
      return new MockConnection(new MockFileDatabase(resourceAsStream));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Connection getConnection(String username, String password) throws SQLException {
    InputStream resourceAsStream = getClass().getResourceAsStream("/mockDatabase");
    try {
      return new MockConnection(new MockFileDatabase(resourceAsStream));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public PrintWriter getLogWriter() throws SQLException {
    return null;
  }

  @Override
  public void setLogWriter(PrintWriter out) throws SQLException {

  }

  @Override
  public void setLoginTimeout(int seconds) throws SQLException {

  }

  @Override
  public int getLoginTimeout() throws SQLException {
    return 0;
  }

  @Override
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    return null;
  }

  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    return null;
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    return false;
  }
}
