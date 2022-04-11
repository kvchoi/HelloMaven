package com.algorithm.back_track;

import java.util.ArrayList;
import java.util.List;

public class PhoneNumLetterCombination {

    public static void main(String[] args) {
        String digits = "23";

        List<String> combinations = new ArrayList<>();

        backtrack(combinations, digits, 0, new StringBuffer());

        System.out.println(combinations);
    }

    public static void backtrack(List<String> combinations, String digits, int index, StringBuffer combination) {
        if (index == digits.length()) {
            // 结束，保存当前词条
            combinations.add(combination.toString());
            return;
        }
        // 第index个号码，对应的字母串
        String letters = getLetters(digits.charAt(index));
        // 对字母串中每一个字母，循环遍历
        for (int i = 0; i < letters.length(); i++) {
            //开始本级组词（相当于前序遍历）
            combination.append(letters.charAt(i));
            //进入下一级组词
            backtrack(combinations, digits, index + 1, combination);
            //清理本级组词（相当于后序遍历）
            combination.deleteCharAt(index);
        }

    }

    // 静态map：0空间占用，而且速度很快(编译成int和字符常量池)
    private static String getLetters(char phoneMun) {
        //@formatter:off
        switch (phoneMun) {
            case '2' : return "abc";
            case '3' : return "def";
            case '4' : return "ghi";
            case '5' : return "jkl";
            case '6' : return "mno";
            case '7' : return "pqrs";
            case '8' : return "tuv";
            case '9' : return "wxyz";
            default: return "";
        }
        //@formatter:on
    }

}
