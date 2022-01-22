package com.wudidemiao.study.algorithm.kmp;

/**
 * @author wudidemiaoa
 * @date 2022/1/22
 * @apiNote
 */
public class ViolenceMatch {
    public static void main(String[] args) {
        System.out.println(violenceMatch("1212345","1234"));
    }

    public static int violenceMatch(String str1, String str2) {
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();

        int i = 0;  // 指向s1
        int j = 0;  // 指向s2
        while (i < s1.length && j < s2.length) {
            if (s1[i] == s2[j]) {
                i++;
                j++;
            } else {
                i = i - (j - 1);
                j = 0;
            }
        }
        if (j == s2.length) {
            return i - j;
        } else {
            return -1;
        }
    }
}
