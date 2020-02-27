package net.pt.jdbc.tester;

import com.mysql.cj.util.StringUtils;

import java.sql.*;
import java.util.Arrays;

public class JdbcTester {

  public static void main(String... args) {
    boolean argsOk = false;
    String usr = null, pwd = null, drv = null, url = null;

    if (args != null && args.length > 0) {
      for (String arg : args) {
        if (arg.startsWith("-url=")) {
          url = arg.substring(5);
        } else if (arg.startsWith("-usr=")) {
          usr = arg.substring(5);
        } else if (arg.startsWith("-pwd=")) {
          pwd = arg.substring(5);
        } else if (arg.startsWith("-drv=")) {
          drv = arg.substring(5);
        }
      }
      if (!StringUtils.isNullOrEmpty(url)) {
        if (StringUtils.isNullOrEmpty(drv)) {
          if (url.startsWith("jdbc:oracle")) {
            drv = "oracle.jdbc.OracleDriver";
            argsOk = true;
          } else if (url.startsWith("jdbc:mysql")) {
            drv = "com.mysql.cj.jdbc.Driver";
            argsOk = true;
          } else {
            System.out.println("Cannot determine driver from jdbc url. Please provide a driver class with -drv=...");
          }
        } else {
          argsOk = true;
        }
      }
    }

    if (argsOk) {
      try {
        new JdbcTester().run(url, drv, usr, pwd);
      } catch (ClassNotFoundException|SQLException ex) {
        ex.printStackTrace();
      }
    } else {
      System.out.println("Cannot parse arguments: " + Arrays.toString(args));
      System.out.println("Usage: -url=jdbcurl [-usr=jdbcusr] [-pwd=jdbcpwd] [-drv=driverclass] ...");
    }

  }

  private void run(String url, String drv, String usr, String pwd) throws ClassNotFoundException, SQLException {
    Timestamp result = null;

    System.out.println("Loading driver: " + drv);
    Class.forName(drv);

    System.out.println("Connecting to: " + url);
    try (Connection con = DriverManager.getConnection(url, usr, pwd)) {
      try (PreparedStatement stmt = con.prepareStatement("SELECT CURRENT_TIMESTAMP FROM DUAL")) {
        try (ResultSet rs = stmt.executeQuery()) {
          while (rs.next()) {
            result = rs.getTimestamp(1);
          }
        }
      }
    }

    if (result != null) {
      System.out.println("Successfully connected and queried timestamp: " + result);
    } else {
      System.out.println("Cannot query timestamp.");
    }
  }
}
