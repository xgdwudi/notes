package com.wudidemiao.study.tree;

import java.util.Arrays;

/**
 * @author wudidemiaoa
 * @date 2022/1/19
 * @apiNote
 */
public class HeadSort {

    public static void main(String[] args) {
//        升序排列，进行大顶堆排序
        int[] arr = {4, 6, 8, 5, 9};
        headSort(arr);
    }

    //    堆排序的方法
    public static void headSort(int[] arr) {
        int tmp = 0;
        System.out.println("堆排序");
// 分布完成
//        adjustHead(arr, 1, arr.length);
//        System.out.println("第一次" + Arrays.toString(arr));  // 4 9 8 5 6
//
//        adjustHead(arr, 0, arr.length);
//        System.out.println("第二次" + Arrays.toString(arr));  // 9, 6, 8, 5, 4
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            adjustHead(arr, i, arr.length);
        }

        for (int j = arr.length - 1; j > 0; j--) {
//            交换
            tmp = arr[j];
            arr[j] = arr[0];
            arr[0] = tmp;
            adjustHead(arr, 0, j);
        }

        System.out.println("数组：" + Arrays.toString(arr));  // 9, 6, 8, 5, 4
    }

    /**
     * 将一个数组（二叉树），调整成一颗大顶堆，
     * <p>
     * 将以i对应的非叶子节点的树调整为大顶堆
     *
     * @param arr    待调整的数组
     * @param i      表示非叶子节点在数组中的索引
     * @param length 表示对多少个元素进行调整，length 实在逐渐的减小
     */
    public static void adjustHead(int arr[], int i, int length) {
// 取出当前元素的值
        int temp = arr[i];
//        开始调整
//        i1= i*2+1 i1是i节点的左子节点
        for (int i1 = i * 2 + 1; i1 < length; i1 = i1 * 2 + 1) {
            if (i1 + 1 < length && arr[i1] < arr[i1 + 1]) {  // 说明左子节点的值小于右子节点的值
                i1++;  // k指向
            }
            if (arr[i1] > temp) {  // 如果子节点大于夫节点
                arr[i] = arr[i1];  // 把较大的值付给当前节点
                i = i1;     // 把i指向i1，持续循环比较
            } else {
                break;
            }
        }

//        当for循环结束后，我们将i为夫节点的树的最大值，放在u最前面（局部）
        arr[i] = temp;
    }
}
