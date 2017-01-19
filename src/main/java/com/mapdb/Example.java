package com.mapdb;

import java.util.concurrent.ConcurrentMap;

import org.mapdb.BTreeMap;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

public class Example {

    public static void main(String[] args) {
        mem();
        file();
        // create form sink, usually to import some data
        sink();
    }

    private static void mem() {
        DB db = DBMaker.memoryDB().closeOnJvmShutdown().make();
        ConcurrentMap map = db.hashMap("mmap").createOrOpen();
        map.put("something", "here");
        System.out.println(map.get("something"));
        db.close();
    }

    private static void file() {
        DB db = DBMaker.fileDB("file1.db").closeOnJvmShutdown().make();
        ConcurrentMap map = db.hashMap("fmap").createOrOpen();
        map.put("something", "here");
        System.out.println(map.get("something"));
        db.close();
    }

    private static void sink() {
        DB db = DBMaker.fileDB("file2.db").closeOnJvmShutdown().make();
        // create sink
        DB.TreeMapSink<Integer, String> sink = db
                .treeMap("smap", Serializer.INTEGER, Serializer.STRING).createFromSink();

        // loop and pass data into map
        for (int lineNum = 0; lineNum < 10000; lineNum++) {
            String line = "some text from file" + lineNum;
            // add key and value into sink, keys must be added in ascending order
            sink.put(lineNum, line);
        }
        // Sink is populated, map was created on background
        // Close sink and return populated map
        BTreeMap<Integer, String> map = sink.create();
        System.out.println(map.get(1));
        db.close();
    }
}
