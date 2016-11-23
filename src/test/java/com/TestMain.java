package com;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import com.rits.cloning.Cloner;

public class TestMain {

    public static void main(String[] args) {
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
}
