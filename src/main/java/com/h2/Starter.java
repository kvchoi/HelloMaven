package com.h2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.lang3.ArrayUtils;
import org.h2.tools.Server;


public class Starter {

    public static void run(String... args) {
        try {
            System.out.println("Starting h2 server...");
            System.out.println("Command Line args : " + ArrayUtils.toString(args));
            Server server = Server.createTcpServer(args).start();
            System.out.println("Started h2 server! status=" + server.getStatus());;
        } catch (Exception e) {
            e.printStackTrace();
        }
        wait4exit();
    }

    private static void wait4exit() {
        try {
            String tips = "press q or quit or exit then [Enter] to terminated";
            System.out.println(tips);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String txt = null;
            do {
                txt = br.readLine();
            } while (!("q".equalsIgnoreCase(txt) || "quit".equalsIgnoreCase(txt)
                    || "exit".equalsIgnoreCase(txt)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        run("-tcpPort", "9123", "-tcpAllowOthers", "-tcpDaemon");
    }
}
