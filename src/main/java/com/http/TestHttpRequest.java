package com.http;

import com.github.kevinsawicki.http.HttpRequest;

/**
 * @author by cairongfu.crf
 * @since on 2018/5/28 20:35.
 */
public class TestHttpRequest {

    public static void main(String[] args) {
        HttpRequest.get("http://www.baidu.com").followRedirects(false).receive(System.out);
        HttpRequest.get("https://www.baidu.com").receive(System.out);
    }
}
