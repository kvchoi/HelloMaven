package com.zaxxer;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariCPTest {

    public static void main(String[] args) {
        HikariConfig config = new HikariConfig();
        config.setMaximumPoolSize(100);
        config.setDataSourceClassName(MysqlDataSource.class.getName());
        config.addDataSourceProperty("serverName", "localhost");
        config.addDataSourceProperty("port", "3306");
        config.addDataSourceProperty("databaseName", "mydb");
        config.addDataSourceProperty("user", "bart");
        config.addDataSourceProperty("password", "51mp50n");

        HikariDataSource ds = new HikariDataSource(config);
        String test = ds.getConnectionTestQuery();
    }
}
