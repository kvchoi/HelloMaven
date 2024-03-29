package com.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestAsyncHttpClient {

    private static final CloseableHttpAsyncClient client;

    static {
        //新建配置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(50000)
                .setSocketTimeout(50000)
                .setConnectionRequestTimeout(1000)
                .build();

        //配置io线程
        IOReactorConfig ioReactorConfig = IOReactorConfig.custom().
                setIoThreadCount(Runtime.getRuntime().availableProcessors())
                .setSoKeepAlive(true)
                .build();

        //设置连接池大小
        ConnectingIOReactor ioReactor = null;
        try {
            ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);
        } catch (IOReactorException e) {
            e.printStackTrace();
        }
        PoolingNHttpClientConnectionManager connManager = new PoolingNHttpClientConnectionManager(ioReactor);
        connManager.setMaxTotal(100);
        connManager.setDefaultMaxPerRoute(100);

        client = HttpAsyncClients.custom().
                setConnectionManager(connManager)
                .setDefaultRequestConfig(requestConfig)
                .build();

        //start
        client.start();
    }

    public static void main(String[] args) {

        String url = "https://www.baidu.com";

        //test async post
        testAsyncPost(url);

        int recursive = 30;

        List<String> urls = Stream.iterate(url, UnaryOperator.identity()).limit(recursive).collect(Collectors.toList());
        testForkJoinPoolWrapped(urls);

        //hold the main thread
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static void testForkJoinPoolWrapped(List<String> urls) {
        urls.parallelStream().forEach(TestAsyncHttpClient::testAsyncPost);
    }

    static void testAsyncPost(String url) {
        //构造请求
        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = null;
        try {
            String a = "{ \"index\": { \"_index\": \"test\", \"_type\": \"test\"} }\n" +
                    "{\"name\": \"上海\",\"age\":33}\n";
            entity = new StringEntity(a);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        httpPost.setEntity(entity);

        //异步请求
        client.execute(httpPost, new Back());
    }

    static class Back implements FutureCallback<HttpResponse> {

        private long start = System.currentTimeMillis();

        Back() {
        }

        public void completed(HttpResponse httpResponse) {
            try {
                System.out.println("cost is:" + (System.currentTimeMillis() - start) + ":" + EntityUtils.toString(httpResponse.getEntity()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void failed(Exception e) {
            System.err.println(" cost is:" + (System.currentTimeMillis() - start) + ":" + e);
        }

        public void cancelled() {

        }
    }
}