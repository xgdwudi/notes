package com.itcase;

import java.util.Scanner;

public class Test02 {
    public static void main(String[] args) {
//        System.out.println(Math.abs(1));
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        String[] s1 = s.split(" ");
        int[] ints = new int[s1.length];
        for (int i = 0; i < s1.length; i++) {
            ints[i] = Integer.parseInt(s1[i]);
        }
        minSum(ints);
    }

    public static void minSum(int[] ints) {
//        存储绝对的值
        int j = 65536 * 2;
//        记录两个数
        int k = 0;
        int m = 0;
        for (int i = 0; i < ints.length; i++) {
            for (int i1 = i + 1; i1 < ints.length; i1++) {
                int abs = Math.abs(ints[i] + ints[i1]);
                if (abs < j) {
                    j = abs;
                    k = ints[i];
                    m = ints[i1];
                }
            }
        }
        if (j == 65536 * 2) {
            System.out.println(" " + " " + " " + " " + " ");
        } else {
            System.out.println(k + " " + m + " " + j);
        }
    }
}
