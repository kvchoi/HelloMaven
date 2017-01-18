package com.jooq;

import java.io.InputStream;

import org.jooq.util.GenerationTool;
import org.jooq.util.jaxb.Configuration;

public class Main {

    public static void main(String[] args) throws Exception {
        InputStream is = Main.class.getResourceAsStream("/codegen.xml");
        Configuration configuration = GenerationTool.load(is);
        GenerationTool.generate(configuration);
    }

}
