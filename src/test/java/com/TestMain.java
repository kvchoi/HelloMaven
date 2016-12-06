package com;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rits.cloning.Cloner;

public class TestMain {
    static File dir = new File("D:\\workspace6\\HelloMaven\\src\\test\\resources");

    public static void main(String[] args) throws Exception {
        List<String> file11 = ppUa("smnor2_11.log");
        List<String> file12 = ppUa("smnor2_12.log");
        List<String> file13 = ppUa("smnor2_13.log");
        List<String> file14 = ppUa("smnor2_14.log");
        List<String> file26 = ppUa("smnor2_26.log");
        List<String> file27 = ppUa("smnor2_27.log");
        List<String> file28 = ppUa("smnor2_28.log");
        List<String> file29 = ppUa("smnor2_29.log");
        List<String> all = Lists.newArrayListWithCapacity(6000);
        all.addAll(file11);
        all.addAll(file12);
        all.addAll(file13);
        all.addAll(file14);
        all.addAll(file26);
        all.addAll(file27);
        all.addAll(file28);
        all.addAll(file29);
        // System.out.println(all.size());
        //
        List<String> file1 = smUa();
        //
        Map<String, Map<String, AtomicInteger>> report = Maps.newHashMap();
        System.out.println("解释" + all.size() + "条PP日志");
        for (String ua : all) {
            parse(ua, "pp", report);
        }
        System.out.println("解释" + file1.size() + "条SM日志");
        for (String ua : file1) {
            parse(ua, "sm", report);
        }

        Set<Entry<String, Map<String, AtomicInteger>>> set = report.entrySet();
        set = set.stream().sorted(new Comparator<Entry<String, Map<String, AtomicInteger>>>() {

            @Override
            public int compare(Entry<String, Map<String, AtomicInteger>> o1,
                    Entry<String, Map<String, AtomicInteger>> o2) {
                if (o1.getValue().containsKey("pp") && o2.getValue().containsKey("pp")) {
                    return o1.getValue().get("pp").intValue() - o2.getValue().get("pp").intValue();
                } else if (o1.getValue().containsKey("pp")) {
                    return 1;
                } else {
                    return -1;
                }
            }

        }).collect(Collectors.toSet());
        for (Entry<String, Map<String, AtomicInteger>> entry : set) {
            System.out.println(entry.getKey() + " ====> " + entry.getValue());
            
        }
        System.out.println("合并出" + set.size() + "条UA记录：");
        int i = 0;
        for (Entry<String, Map<String, AtomicInteger>> entry : set) {
            if(!entry.getValue().containsKey("sm")){
                System.out.println(entry.getKey() + " ====> " + entry.getValue());
                i++;
            }
        }
        System.out.println("发现只存在于pp的" + i + "条UA记录：");
        int j = 0;
        for (Entry<String, Map<String, AtomicInteger>> entry : set) {
            if(!entry.getValue().containsKey("pp")){
                System.out.println(entry.getKey() + " ====> " + entry.getValue());
                j++;
            }
        }
        System.out.println("发现只存在于sm的" + j + "条UA记录：");
    }

    public static void parse(String ua, String type, Map<String, Map<String, AtomicInteger>> report) {
        if (report.containsKey(ua)) {
            Map<String, AtomicInteger> countMap = report.get(ua);
            if (countMap.containsKey(type)) {
                countMap.get(type).addAndGet(1);
            } else {
                countMap.put(type, new AtomicInteger(1));
            }
        } else {
            Map<String, AtomicInteger> one = Maps.newHashMap();
            one.put(type, new AtomicInteger(1));
            report.put(ua, one);
        }
    }

    public static List<String> ppUa(String fileName) throws Exception {
        File file = new File(dir, fileName);
        List<String> content = FileUtils.readLines(file, CharEncoding.UTF_8);
        List<String> result = Lists.newArrayListWithCapacity(3000);
        int i = 0;
        for (String c : content) {
            if (!StringUtils.containsIgnoreCase(c, "566489")) {
                continue;
            }
            String[] arr = StringUtils.splitByWholeSeparatorPreserveAllTokens(c, "`");
            String[] ua = StringUtils.splitByWholeSeparatorPreserveAllTokens(arr[37], "=");
            // System.out.println(ua[1]);
            i++;
            result.add(ua[1]);
        }
        // System.out.println(i);
        return result;
    }

    public static List<String> smUa() throws Exception {
        File file = new File(dir, "1.dat");
        List<String> content = FileUtils.readLines(file, CharEncoding.UTF_8);
        int i = 0;
        List<String> result = Lists.newArrayListWithCapacity(3000);
        for (String c : content) {
            if (!StringUtils.containsIgnoreCase(c, "566489")) {
                continue;
            }
            String[] arr = StringUtils.splitByWholeSeparatorPreserveAllTokens(c, "`");
            // System.out.println(arr[35]);
            result.add(arr[35]);
            i++;
        }
        // System.out.println(i);
        return result;
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
