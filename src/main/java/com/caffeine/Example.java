package com.caffeine;

import java.util.concurrent.TimeUnit;

import com.common.utils.ByteArrayUtils;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.google.common.hash.Funnels;
import com.google.common.hash.Hashing;

public class Example {

    public static void main(String[] args) throws Exception {
        LoadingCache<Object, Object> cache = Caffeine.newBuilder().maximumSize(10_000)
                .expireAfterWrite(5, TimeUnit.MINUTES).refreshAfterWrite(3, TimeUnit.SECONDS)
                .build(key -> createExpensiveGraph(key));
        cache.put("key", "value");
        Object value = cache.get("key");
        System.out.println(value);
        Object value2 = cache.get("key2");
        System.out.println(value2);
    }

    private static Object createExpensiveGraph(Object key) {
        return Hashing.murmur3_128().hashObject(ByteArrayUtils.objectToBytes(key).get(),
                Funnels.byteArrayFunnel());
    }
}
