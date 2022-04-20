package com.algorithm.force_opt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FourSumEqualK {

    public static void main(String[] args) {
        int[] a = new int[]{-1, 0, 1, 2, -1, -4};

        int target = 0;

        List<List<Integer>> ans = force(a, target);

        System.out.println(Arrays.toString(a));

        System.out.println(ans);
    }

    // 类似三数之和，可以优化掉一次n遍历，达到O（n^3）
    public static List<List<Integer>> force(int[] nums, int target) {
        Arrays.sort(nums);

        List<List<Integer>> ans = new ArrayList<>();

        for(int a = 0; a < nums.length; a++) {
            if (a == 0 || nums[a] != nums[a-1]) {
                for(int b = a + 1; b < nums.length; b++) {
                    if (b == a+1 || nums[b] != nums[b-1]) {
                        for(int c = b + 1; c < nums.length; c++) {
                            if(c == b+1 || nums[c] != nums[c-1]){
                                for(int d=c+1; d < nums.length ; d++) {
                                    if (d == c+1 || nums[d] != nums[d-1]) {
                                        if (nums[a]+nums[b]+nums[c]+nums[d]==target) {
                                            List<Integer> tmp = new ArrayList<>(4);
                                            tmp.add(nums[a]);
                                            tmp.add(nums[b]);
                                            tmp.add(nums[c]);
                                            tmp.add(nums[d]);
                                            Collections.sort(tmp);
                                            ans.add(tmp);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return ans;
    }
}
