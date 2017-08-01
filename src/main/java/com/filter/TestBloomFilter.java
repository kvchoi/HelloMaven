package com.filter;

import orestes.bloomfilter.BloomFilter;
import orestes.bloomfilter.CountingBloomFilter;
import orestes.bloomfilter.FilterBuilder;

/**
 * @author by cairongfu.crf
 * @since on 2017/8/1 17:43.
 */
public class TestBloomFilter {

    public static void main(String[] args) {
        //Expect 10M URLs
        BloomFilter<String> bf = new FilterBuilder(10_000_000, 0.01).buildBloomFilter();
        //Add millions of URLs
        bf.add("http://github.com");
        //Know in an instant which ones you have or have not seen before
        boolean has = bf.contains("http://twitter.com");
        System.out.println(has);

        CountingBloomFilter<String> bf2 = new FilterBuilder(10_000_000, 0.01).buildCountingBloomFilter();
        bf2.addAndEstimateCount("http://github.com");
        bf2.addAndEstimateCount("http://github.com");
        bf2.remove("http://github.com");
        boolean has2 = bf2.contains("http://github.com");
        System.out.println(has2);
        bf2.remove("http://github.com");
        boolean has3 = bf2.contains("http://github.com");
        System.out.println(has3);
    }
}
