package com.demo.sort;


import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @version 1.0
 * 注意：本内容仅限于西安城市发展资源信息有限公司内部传阅，禁止外泄以及用于其他的商业目
 * @ClassName BobbleSort  冒泡算法
 * @Description TODO
 * @Aurhor xu
 * @Ddte 2021/1/16 16:47
 **/
public class BobbleSort {
    public static void main(String[] args) {
            int[] maopao = {2,5,3,4,8,1};
//      第一步 将数组最大的一位移到最后
//      第二步 将数组第二大的移到倒数第二位
//        以此类推
        int temp = 0;
        for (int i = 0; i <maopao.length-1 ; i++) {
            for (int j = 0; j < maopao.length-1-i; j++) {
                if(maopao[j]>maopao[j+1]){
                    temp=maopao[j+1];
                    maopao[j+1]=maopao[j];
                    maopao[j]=temp;
                }
            }
            System.out.println("第"+(i+1)+"次循环"+ Arrays.toString(maopao));
        }
    }
}
