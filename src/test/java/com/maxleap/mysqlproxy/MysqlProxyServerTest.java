package com.maxleap.mysqlproxy;

import io.vertx.core.Vertx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.maxleap.mysqlproxy.MysqlProxyServer.MysqlProxyServerVerticle;

public class MysqlProxyServerTest {
    private static String driverName = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3307/25pp_android";
    private static String user = "root";
    private static String password = "";

    @Before
    public void runServer() {
        Vertx.vertx().deployVerticle(new MysqlProxyServerVerticle());
    }

    @After
    public void closeServer() {
        Vertx.vertx().close();
    }

    @Test
    public void test() {
        try {
            Class.forName(driverName);// 指定连接类型
            Connection conn = DriverManager.getConnection(url, user, password);// url为代理服务器的地址
            PreparedStatement pst = conn.prepareStatement("select * from android_ads_config;");// 准备执行语句
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getLong(1) + ": " + resultSet.getString(2));
            }
            pst.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
