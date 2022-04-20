package com.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Optional;

/**
 * Created by Jason on 2017/1/3.
 */
public class ByteArrayUtils {

    public static void main(String[] args) {
        Long kv = new Long(10);
        Optional<byte[]> opt =  objectToBytes(kv);
        if (opt.isPresent()) {
            Optional<Long> optional = bytesToObject(opt.get(), Long.class);
            System.out.println(optional.get());
        }
        
    }

    public static <T> Optional<byte[]> objectToBytes(T obj) {
        byte[] bytes = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream sOut;
        try {
            sOut = new ObjectOutputStream(out);
            sOut.writeObject(obj);
            sOut.flush();
            bytes = out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(bytes);
    }

    public static <T> Optional<T> bytesToObject(byte[] bytes, Class<T> clazz) {
        T t = null;
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ObjectInputStream sIn;
        try {
            sIn = new ObjectInputStream(in);
            Object o = sIn.readObject();
            t = clazz.cast(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(t);
    }
}