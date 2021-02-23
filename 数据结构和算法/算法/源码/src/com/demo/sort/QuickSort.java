package com.demo.sort;

import java.util.Arrays;

/**
 * @version 1.0
 * 注意：本内容仅限于西安城市发展资源信息有限公司内部传阅，禁止外泄以及用于其他的商业目
 * @ClassName qUICKsORT
 * @Description  快速排序
 * @Aurhor xu
 * @Ddte 2021/2/5 11:02
 **/
public class QuickSort {
    public static void main(String[] args) {
//        int[] adds ={100,78,0,82,-567,70};
//        sort(adds,0,adds.length-1);
//        System.out.println(Arrays.toString(adds));
        String s = num2Chinese(99999);
        System.out.println(s);
    }

    public static String num2Chinese(int section) {
        if (section >= 10 && section < 20)
            return "十" + num2Chinese(section % 10);
        String[] chnNumChar = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String[] chnUnitChar = {"", "十", "百", "千","万"};
        StringBuilder chnStr = new StringBuilder();
        StringBuilder strIns = new StringBuilder();
        int unitPos = 0;
        boolean zero = true;
        while (section > 0) {
            int v = section % 10;
            if (v == 0) {
                if (!zero) {
                    zero = true;
                    chnStr.append(chnNumChar[v]).append(chnStr);
                }
            } else {
                zero = false;
                strIns.delete(0, strIns.length());
                strIns.append(chnNumChar[v]);
                strIns.append(chnUnitChar[unitPos]);
                chnStr.insert(0, strIns);
            }
            unitPos++;
            section = (int) Math.floor(section / 10f);
        }
        return chnStr.toString();
    }
    public static void sort(int[] arr,int left,int right){
        int l = left;
        int r = right;
        int temp=0;
        int pivot = arr[(left+right)/2];
        while (l<r){
            while (arr[l]<pivot){
                l++;
            }
            while (arr[r]>pivot){
                r--;
            }
            if(l>=r){
                break;
            }
            temp=arr[l];
            arr[l]=arr[r];
            arr[r]=temp;
//            if(arr[r]==pivot){
//                l++;
//            }
//            if(arr[l]==pivot){
//                r--;
//            }
        }
        while (l==r){
            l++;
            r--;
        }
        if(left<r){
            sort(arr,left,r);
        }
        if(l<right){
            sort(arr,l,right);
        }
    }
}
