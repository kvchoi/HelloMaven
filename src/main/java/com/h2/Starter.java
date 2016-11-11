package com.h2;

import java.sql.SQLException;

import org.h2.tools.Server;

public class Starter {

    public static void run(String... args) {
        try {
            Server server = Server.createTcpServer(args).start();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        run(args);
    }
}
