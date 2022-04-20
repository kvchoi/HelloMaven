package com.zaxxer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.h2.jdbcx.JdbcDataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariCPTest {

    public static void main(String[] args) {
        try {
            // mysql();
            Connection c = h2();
            // do query
            PreparedStatement p = c.prepareStatement("select 1");
            boolean result = p.execute();
            System.out.println(result);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static Connection mysql() throws SQLException {
        HikariConfig config = new HikariConfig();
        config.setMaximumPoolSize(100);
        config.setDataSourceClassName(MysqlDataSource.class.getName());
        config.addDataSourceProperty("serverName", "localhost");
        config.addDataSourceProperty("port", "3306");
        config.addDataSourceProperty("databaseName", "mydb");
        config.addDataSourceProperty("user", "bart");
        config.addDataSourceProperty("password", "51mp50n");

        try (HikariDataSource ds = new HikariDataSource(config)) {
            Connection c = ds.getConnection();
            return c;
        }
    }

    public static Connection h2() throws SQLException {
        HikariConfig config = new HikariConfig();
        config.setMaximumPoolSize(100);
        config.setDataSourceClassName(JdbcDataSource.class.getName());
        config.addDataSourceProperty("URL", "jdbc:h2:tcp://localhost:9123/~/test");
        config.addDataSourceProperty("user", "sa");
        config.addDataSourceProperty("password", "sa");

        try (HikariDataSource ds = new HikariDataSource(config)) {
            ds.setConnectionTestQuery("select 1");
            Connection c = ds.getConnection();
            return c;
        }
    }

}
