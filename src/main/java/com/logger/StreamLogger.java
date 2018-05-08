package com.logger;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.MemoryHandler;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.io.IOUtils;

/**
 * 日志输出到缓冲区，以便提取展示
 *
 * @author by cairongfu.crf
 * @since on 2018/5/7 20:38.
 */
public class StreamLogger {

    private ByteArrayOutputStream loggerContent = new ByteArrayOutputStream();
    private PrintStream prStr = new PrintStream(loggerContent);
    private StreamHandler streamHandler = new StreamHandler(prStr, new SimpleFormatter() {
        private static final String format = "%1$s %n";

        @Override
        public synchronized String format(LogRecord record) {
            return String.format(format, super.formatMessage(record));
        }
    });
    private MemoryHandler memoryHandler = new MemoryHandler(streamHandler, 1000, Level.FINER);

    public Logger createLogger(Class clz) {
        final Logger logger = Logger.getLogger(clz.getName());
        logger.setUseParentHandlers(false);
        logger.addHandler(memoryHandler);
        return logger;
    }

    public String getLoggerContent() {
        String content = null;
        try {
            memoryHandler.flush();
            content = loggerContent.toString(CharEncoding.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return content;
    }

    public void destroy() {
        try {
            this.memoryHandler.close();
            this.streamHandler.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(this.prStr);
            IOUtils.closeQuietly(this.loggerContent);
        }
    }

    @Override
    public String toString() {
        return getLoggerContent();
    }

    public static void main(String[] args) {
        StreamLogger streamLogger = new StreamLogger();
        Logger logger = streamLogger.createLogger(StreamLogger.class);
        logger.log(Level.INFO, "here{0}", "1");
        logger.log(Level.INFO, "here2");
        System.out.println(streamLogger.toString());
        streamLogger.destroy();
    }
}
