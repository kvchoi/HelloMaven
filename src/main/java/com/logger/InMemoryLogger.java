package com.logger;

import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.CharEncoding;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.WriterAppender;

/**
 * @author by cairongfu.crf
 * @since on 2018/9/29 11:21.
 */
public class InMemoryLogger {

    public static void main(String[] args) {
        StringWriter consoleWriter = new StringWriter();

        WriterAppender appender = new WriterAppender();
        appender.setName("CONSOLE_APPENDER");
        appender.setThreshold(Level.DEBUG);
        appender.setEncoding(CharEncoding.UTF_8);
        appender.setLayout(new PatternLayout("%d{ISO8601} %p - %m%n"));
        appender.setWriter(consoleWriter);

        Logger.getRootLogger().addAppender(appender);
        for (int i = 0; i < 100; i++) {
            Logger log = Logger.getLogger(String.valueOf(i));
            log.info("test:" + i);
        }

        System.out.println("nothing before, i write below :");
        System.out.println(consoleWriter.getBuffer().toString());

        Logger.getRootLogger().removeAppender("CONSOLE_APPENDER");
        appender.close();
        IOUtils.closeQuietly(consoleWriter);
    }

}
