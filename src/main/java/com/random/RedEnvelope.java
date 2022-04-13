package com.random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 抢红包算法分析：
 * 1，直接随机法；2，限定单个红包最大金额；3，线段切割法。
 * <p>
 * <p>
 * 1，每次new一个随机对象，或者共享一个随机对象，在高并发场景都有不足，所以考虑线程本地随机对象
 * Random random = new Random();
 * 2，为了使用random.nextInt(Integer)方法, 可以先把红包金额放大100倍，最后结合再除以100
 */
public class RedEnvelope {

    public static void main(String[] args) {
        System.out.println(directRandom(100, 10));
        System.out.println(directRandom(50, 5));
        System.out.println("=========");
        System.out.println(doubleAvg(100, 10));
        System.out.println(doubleAvg(50, 5));
        System.out.println("=========");
        System.out.println(lineDivide(100, 10));
        System.out.println(lineDivide(50, 5));
    }

    /**
     * 直接随机，每次随机获得一个金额大小比例，但超后面红包超小，非公平算法且可能提前抢完。
     *
     * @param totalAmount 红包总金额
     * @param nums        红包个数
     */
    static List<Integer> directRandom(int totalAmount, int nums) {
        List<Integer> redEnvelope = new ArrayList<>(nums);
        int remaining = totalAmount;
        for (int i = 0; i < nums - 1; i++) {
            double probability = ThreadLocalRandom.current().nextDouble();
            int subAmount = (int) Math.round(remaining * probability);
            if (subAmount == 0 || remaining - subAmount == 0) {
                continue;
            }
            redEnvelope.add(subAmount);
            remaining = remaining - subAmount;
        }
        // 剩下的归最后一个人，不公平
        redEnvelope.add(remaining);
        return redEnvelope;
    }

    /**
     * 两倍均值法，即每个人拿到红包大小，不得大于剩余人均金额的2倍。有一人不公平。
     *
     * @param totalAmount
     * @param nums
     * @return
     */
    public static List<Integer> doubleAvg(int totalAmount, int nums) {
        int last_money = totalAmount;
        int last_people = nums;
        List<Integer> redEnvelope = new ArrayList<>(nums);
        for (int i = 0; i < nums - 1; i++) {
            // 限定随机范围：[1，剩余人均金额的2倍 - 1] 分
            int randRedValue = ThreadLocalRandom.current().nextInt(last_money / last_people * 2 - 1) + 1;
            redEnvelope.add(randRedValue);
            last_money -= randRedValue;
            last_people--;
        }
        //最后一人分剩余金额, 此处为非公平
        redEnvelope.add(last_money);
        return redEnvelope;
    }

    /**
     * 线段切割法，即当N个人一起抢红包的时候，就需要确定N-1个切割点。相对公平算法。
     *
     * @param totalMoney
     * @param nums
     */
    private static List<Integer> lineDivide(int totalMoney, int nums) {
        //切割点集合，初始头和尾两个切割点，还需要n-1个
        List<Integer> boards = new ArrayList<>();
        boards.add(0);
        boards.add(totalMoney);
        //红包个数和线段个数的关系
        while (boards.size() <= nums) {
            // 随机生成切割点
            int index = ThreadLocalRandom.current().nextInt(totalMoney - 1) + 1;
            if (boards.contains(index)) {
                //保证线段的位置不相同
                continue;
            }
            boards.add(index);
        }

        //计算每个红包的金额，将两个板子之间的钱加起来
        Collections.sort(boards);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < boards.size() - 1; i++) {
            Integer e = boards.get(i + 1) - boards.get(i);
            list.add(e);
        }
        return list;
    }
}