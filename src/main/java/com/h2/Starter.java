package com.h2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;

import org.h2.tools.Server;

public class Starter {

    public static void run(String... args) {
        try {
            Server server = Server.createTcpServer(args).start();
            new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        run("-tcpPort", "9123", "-tcpAllowOthers", "-tcpDaemon");
    }
}
