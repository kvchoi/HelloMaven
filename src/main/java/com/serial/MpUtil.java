package com.serial;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.msgpack.MessagePack;
import org.msgpack.packer.BufferPacker;
import org.msgpack.template.Template;
import org.msgpack.template.Templates;
import org.msgpack.unpacker.BufferUnpacker;

public class MpUtil {

    private static final MessagePack MESSAGE_PACK = new MessagePack();

    static {
        MESSAGE_PACK.setClassLoader(MpUtil.class.getClassLoader());
    }

    public static byte[] pack(Object value) {
        if (value == null) {
            return null;
        }
        try {
            BufferPacker packer = MESSAGE_PACK.createBufferPacker();
            packer.write(value);
            return packer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T unpack(byte[] raw, Class<T> clz) {
        if (raw == null) {
            return null;
        }
        try {
            return MESSAGE_PACK.read(raw, clz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> byte[] pack(T list, Template<T> template) {
        if (list == null || template == null) {
            return null;
        }
        try {
            BufferPacker packer = MESSAGE_PACK.createBufferPacker();
            template.write(packer, list);
            return packer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T unpack(byte[] bytes, Template<T> template) {
        if (bytes == null || template == null) {
            return null;
        }
        try {
            BufferUnpacker unpacker = MESSAGE_PACK.createBufferUnpacker(bytes);
            unpacker.resetReadByteCount();
            return template.read(unpacker, null);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <E> Template<E> lookupTemplate(Class<E> elementClass) {
        return MESSAGE_PACK.lookup(elementClass);
    }

    public static void main(String[] args) {
        AdxInfo adxInfo = new AdxInfo();
        adxInfo.setDspType(DspType.BAIDU);
        adxInfo.setAppId(123123);

        // 简单类型测试
        byte[] bytes = pack(adxInfo);
        System.out.println(unpack(bytes, AdxInfo.class));

        //===============================================
        List<AdxInfo> adxInfos = Lists.newArrayList();
        adxInfos.add(adxInfo);

        // 列表类型测试
        Template<List<AdxInfo>> listTemplate = Templates.tList(lookupTemplate(AdxInfo.class));
        byte[] bytes1 = pack(adxInfos, listTemplate);
        List<AdxInfo> newAdxInfos = unpack(bytes1, listTemplate);
        System.out.println(newAdxInfos);

        //================================================
        Map<String, Map<String, AdxInfo>> stringMapMap = Maps.newHashMap();
        Map<String, AdxInfo> stringAdxInfoMap = Maps.newHashMap();
        stringAdxInfoMap.put("bb", adxInfo);
        stringMapMap.put("a", stringAdxInfoMap);

        // 复杂map类型测试
        Template<Map<String, Map<String, AdxInfo>>> mapTemplate = Templates.tMap(Templates.TString,
            Templates.tMap(Templates.TString, lookupTemplate(AdxInfo.class)));
        byte[] bytes2 = pack(stringMapMap, mapTemplate);
        Map<String, Map<String, AdxInfo>> newStringMapMap = unpack(bytes2, mapTemplate);
        System.out.println(newStringMapMap);
    }

}
