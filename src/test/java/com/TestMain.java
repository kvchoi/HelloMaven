package com;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rits.cloning.Cloner;

public class TestMain {

    public static void main(String[] args) {
        new TestMain().sorting();
    }

    public void cloning() {
        JsonArray arr = new JsonArray();
        JsonObject obj = new JsonObject("{\"a\":1}");
        JsonObject obj2 = new JsonObject("{\"b\":true}");
        arr.add(obj);
        arr.add(obj2);
        arr.add(123d);
        Cloner c = new Cloner();
        JsonArray arr2 = c.deepClone(arr);
        System.out.println(arr.toString());
        System.out.println(arr2.toString());
    }

    public void sorting() {
        List<String> cardNames = Lists.newArrayList("1", "2", "3", "4");
        Map<String, Double> cardNameToCtrBeanMap = Maps.newHashMap();
        cardNameToCtrBeanMap.put("1", Double.valueOf(1.1));
        cardNameToCtrBeanMap.put("2", Double.valueOf(1.2));
        cardNameToCtrBeanMap.put("3", Double.valueOf(1.2));
        cardNameToCtrBeanMap.put("4", Double.valueOf(1.0));
        List<String> stickyList = Lists.newArrayList("1");
        Collections.sort(cardNames, Comparator.comparingDouble(s -> {
            if (stickyList.contains(s))
                return Double.POSITIVE_INFINITY;
            else
                return cardNameToCtrBeanMap.getOrDefault(s, 0.0);
        }).reversed());
        cardNames.forEach(s -> System.out.println(s));
    }

}
