package com.itcase;

import java.util.Scanner;

public class Test01 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        String[] split = str.split(",");
//        记录最近的位置距离
        int k = 0;
//        记录上一个1的位置
        int l = 0;
        int[] strs = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            strs[i] = Integer.parseInt(split[i]);
        }
        for (int i = 0; i < strs.length; i++) {
            if (strs[i] == 1) {
                l = i;
            }
            if (strs[i] == 0) {
                int m = i;
                while (true){
                    if(m==strs.length){
                        break;
                    }
                    if(strs[m]==1){
                        break;
                    }
                    m++;
                }
                int h=0;
                if(i==0){
                    h= m-i;
                }else{
                    h =i-l>m-i?m-i:i-l;
                }

                if(h>k){
                   k=h;
                }
            }
            if(i==strs.length-1){
                System.out.println(k);
            }
        }
    }
}


