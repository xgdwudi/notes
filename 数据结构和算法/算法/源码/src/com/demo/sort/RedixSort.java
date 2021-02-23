package com.demo.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @version 1.0
 * 注意：本内容仅限于西安城市发展资源信息有限公司内部传阅，禁止外泄以及用于其他的商业目
 * @ClassName RedixSort
 * @Description TODO
 * @Aurhor xu
 * @Ddte 2021/2/7 10:23
 **/
public class RedixSort {
    public static void main(String[] args) {
//        int[] arr = {12,18,1,568,198,1869};
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int[] arr = new int[8000000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 8000000);
        }
        System.out.println(simpleDateFormat.format(new Date()));
        redixSort(arr);
        System.out.println(simpleDateFormat.format(new Date()));
//        System.out.println(Arrays.toString(arr));
    }

    //    基数排序  注意：基数排序会耗费额外的内存空间
    public static void redixSort(int[] arr) {
//        思路
//        1、第一轮，创建10个数组，分别从数组中去出每个元素的值，拿到他的个位数，放到对应的下标数组中，
//         创建一个大小为[10][arr.length()]大小的二维数组用来代替10个桶，
//        2、将数据分别从二位数组中取出，依次存入以前的数组中，
//        3、第二轮，重复之前的操作，拿到他的十位数，。。。。
//        4、创建一个一维数组来记录二维数组中各个桶中有效值的位数。
//        代码实现：
//          先去找到数组的最大值，得到最大位数
        int maxnumber = 0;
        for (int i : arr) {
            if (i > maxnumber) {
                maxnumber = i;
            }
        }
        int maxlength = (maxnumber + "").length();
//        创建二维数组
        int[][] erarr = new int[10][arr.length];
//        创建记录各个桶的有效数字的个数
        int[] yxarr = new int[10];
//        总共循环 maxlength此
        for (int i = 0, n = 1; i < maxlength; i++, n *= 10) {
//            存
            for (int i1 = 0; i1 < arr.length; i1++) {
//                拿到个或Ⅹ位数
                int tss = arr[i1] / n % 10;
//                将数组存入这个桶中
                erarr[tss][yxarr[tss]] = arr[i1];
                yxarr[tss]++;
            }
//            取
            int index = 0;
            for (int i1 = 0; i1 < yxarr.length; i1++) {
                if (yxarr[i1] != 0) {
                    for (int j = 0; j < yxarr[i1]; j++) {
                        arr[index++] = erarr[i1][j];
                    }
                }
                yxarr[i1] = 0;
            }

        }
    }
}
