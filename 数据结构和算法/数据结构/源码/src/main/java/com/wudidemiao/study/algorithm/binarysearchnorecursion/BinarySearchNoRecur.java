package com.wudidemiao.study.algorithm.binarysearchnorecursion;

/**
 * @author wudidemiaoa
 * @date 2022/1/22
 * @apiNote
 */
public class BinarySearchNoRecur {
    public static void main(String[] args) {
        int[] arr = {1, 3, 8, 10, 12, 14};
        System.out.println(binarySearch(arr, 14));
    }

    /**
     * 二分查找的非递归实现
     *
     * @param arr    带查找的数组
     * @param target 需要查找的数
     * @return 返回对应的下标，-1表示没有找到
     */
    public static int binarySearch(int arr[], int target) {
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] > target) {  // 需要向左边1查找
                right = mid - 1;
            } else {
                left = mid + 1;  // 需要向右边查找
            }
        }

        return -1;
    }
}

