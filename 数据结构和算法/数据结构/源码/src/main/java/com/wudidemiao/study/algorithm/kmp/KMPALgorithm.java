package com.wudidemiao.study.algorithm.kmp;

/**
 * @author wudidemiaoa
 * @date 2022/1/22
 * @apiNote
 */
public class KMPALgorithm {
    public static void main(String[] args) {
        String str = "BBC ABCDAB ABCDABCDABDE";
        String str2 = "ABCDABD";
        int[] ints = kmpNext(str2);
        System.out.println(kmpSearch(str, str2, ints));
    }


    /**
     * kmp搜索
     *
     * @param str1 源字符串
     * @param str2 子串
     * @param next 部分匹配表
     * @return 返回下标
     */
    public static int kmpSearch(String str1, String str2, int[] next) {
//        遍历
        for (int i = 0, j = 0; i < str1.length(); i++) {
            if (j > 0 && str1.charAt(i) != str2.charAt(j)) {
                j = next[j - 1];
            }
            if (str1.charAt(i) == str2.charAt(j)) {
                j++;
            }

            if (j == str2.length()) {  // 找到了
                return i - j + 1;
            }
        }
        return -1;
    }

    //        获取到一个字符串（子串）的部分匹配值表
    public static int[] kmpNext(String dest) {
//        创建一个数组，保存部分匹配值
        int[] next = new int[dest.length()];
        next[0] = 0;
        for (int i = 1, j = 0; i < dest.length(); i++) {
//            当dest.charAt(i) != dest.chartAt(j) 我们需要从next[j-1] 获取新的j
//            知道我们发现 有dest.charAt(i) == dest.charAt(j) 成立才退出
//            这是kmp算法的核心点
            while (j > 0 && dest.charAt(i) != dest.charAt(j)) {
                j = next[j - 1];
            }
//            当满足时，部分匹配值就是+1
            if (dest.charAt(i) == dest.charAt(j)) {
                j++;
            }
            next[i] = j;
        }
        return next;
    }
}
