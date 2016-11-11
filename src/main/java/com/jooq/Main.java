package com.jooq;

import java.io.InputStream;

import org.jooq.util.GenerationTool;

public class Main {

    public static void main(String[] args) throws Exception {
        InputStream is = Main.class.getResourceAsStream("/codegen.xml");
        GenerationTool.load(is);
    }

}
