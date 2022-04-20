package com.caffeine;

import java.util.concurrent.TimeUnit;

import com.common.utils.ByteArrayUtils;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.google.common.collect.ImmutableList;
import com.google.common.hash.Funnels;
import com.google.common.hash.Hashing;
// import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

public class Example {

    public static void main(String[] args) throws Exception {
        LoadingCache<Object, Object> cache = Caffeine.newBuilder().maximumSize(10_000)
                .expireAfterWrite(5, TimeUnit.MINUTES).refreshAfterWrite(3, TimeUnit.SECONDS)
                .build(Example::createExpensiveGraph);
        // direct set
        cache.put("key", "value");
        Object value = cache.get("key");

        // loading dynamic by string key
        System.out.println(value);
        Object value2 = cache.get("key2");
        System.out.println(value2);

        // loading dynamic by special key (optimal multiple partial keys than string appending)
        Pair<String, String> pair = Pair.of("key", "key2");
        Object value3 = cache.get(pair);
        System.out.println(value3);
        Triple<String, String, String> triple = Triple.of("key", "key2", "key3");
        Object value4 = cache.get(triple);
        System.out.println(value4);
        ImmutableList<Object> dynamic = ImmutableList.of("key", "key2", "key3", "key4", 999_999);
        Object value5 = cache.get(dynamic);
        System.out.println(value5);
    }

    private static Object createExpensiveGraph(Object key) {
        return Hashing.murmur3_128().hashObject(ByteArrayUtils.objectToBytes(key).get(),
                Funnels.byteArrayFunnel());
    }
}
