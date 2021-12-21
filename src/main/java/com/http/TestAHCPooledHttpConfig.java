package com.http;

import java.util.concurrent.ExecutionException;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;

/**
 * @author by cairongfu.crf
 * @since on 2017/7/21 14:55.
 */
public class TestAHCPooledHttpConfig {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        AsyncHttpClientConfig.Builder builder = new AsyncHttpClientConfig.Builder();

        builder.setAcceptAnyCertificate(true);
        //builder.setAllowPoolingConnections(false);
        //builder.setAllowPoolingSslConnections(false);
        //builder.setPooledConnectionIdleTimeout(3000);

        AsyncHttpClient client = new AsyncHttpClient(builder.build());

        client.prepareGet("https://www.google.com.hk/").execute().get();
        client.prepareGet("https://www.google.com.hk/").execute().get();

    }
}
