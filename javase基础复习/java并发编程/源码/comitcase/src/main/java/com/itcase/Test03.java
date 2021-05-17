package com.itcase;

import java.util.Scanner;

public class Test03 {
    public static int count =0;
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        String[] s1 = s.split(" ");
        int[][] adds = new int[2][2];
        for (int i = 0; i < s1.length; i++) {
            adds[0][i]=Integer.parseInt(s1[i]);
        }
        int[][] add = new int[adds[0][0]][adds[0][1]];
        for (int i = 0; i < adds[0][0]; i++) {
            String s2 = scanner.nextLine();
            String[] s3 = s2.split(" ");
            for (int i1 = 0; i1 < s3.length; i1++) {
                add[i][i1]=Integer.parseInt(s3[i1]);
            }
        }
        Thread thread = new Thread(() -> {

        });
        thread.join();
//        将初始数组add 和 adds[0][0]行,adds[0][1]列传进去
        adds(add,adds[0][0],adds[0][1]);
    }
    //找出每个聚餐的节点
    public static void adds(int[][] ints,int k,int h){
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < h; j++) {
                if(ints[i][j]==3){
//                  判断聚餐的节点是否满足要求，符合要求则加一   i和j 为当前3的位置，k,h 为几行几列 ，其实这个不用传，可以通过数组拿到，但是不想改了
                    if(dgs(ints,i,j,k,h)){
                        count++;
                    }
                }
            }
        }
        System.out.println(count);
    }
// 拿到小华或者小为的初始位置
    public static Boolean dgs(int[][] ints,int i,int j,int z,int n){
        // 存储他们的初始位置
        int k = -1,v=-1;
        int d = -1,l=-1;
        int[][] ints1 = new int[z][n];
        int[][] ints2 = new int[z][n];
        for (int i1 = 0; i1 < z; i1++) {
                for (int m = 0; m <  n; m++) {
                    ints1[i1][m]=ints[i1][m];
                    ints2[i1][m]=ints[i1][m];
                    if(ints[i1][m]==2){
                        if(k==-1){
                            k = i1;
                            v=m;
                        }else{
                            d=i1;
                            l=m;
                        }
                    }
                }
            }
//        int[][] clone = ints.clone();
//            判断两个人是否都能到达此聚餐节点，如能，则返回true
        if(dg(ints2, k, v, i, j, z, n) && dg(ints1, d, l, i, j, z, n)){
            return true;
        }else {
            return false;
        }
    }
//    i j 目前顾客所在位置
//    d b 聚餐地点坐在位置
//    k l int[][] 大小
//    走过的路为6
    public static Boolean dg(int[][] ints,int i,int j,int d,int b,int k,int l){
//        上移
        if(i>0){
//            当数组上一行同一列的元素为0 时，允许上移
            if(i-1==d&&j==b){
                return true;
            }else if(ints[i-1][j] !=1  && ints[i-1][j] !=6){
                ints[i-1][j]=2;
                ints[i][j]=6;
//                递归，判断上移后的节点，能否到达此聚餐点
                Boolean dg = dg(ints, i - 1, j,d, b, k, l);
//                如果不能，将数据数据归位，进行后面操作
                if(!dg){
                    ints[i-1][j]=0;
                    ints[i][j]=2;
                }else {
                    return true;
                }
//                如果上移后的节点是聚餐节点，则返回true,后面类同这个，
            }
        }
//       左移
            if(j>0){
            if(i==d&&j-1==b){
                return true;
            } else if(ints[i][j-1]!=1 &&ints[i][j-1]!=6){
                ints[i][j]=2;
                ints[i][j-1]=6;
                Boolean dg = dg(ints, i, j-1,d, b, k, l);
                if(!dg){
                    ints[i][j]=0;
                    ints[i][j-1]=2;
                }else {
                    return true;
                }
            }
        }
        //        下移
        if(i+1<k){
             if(i+1==d&&j==b){
                return true;
            }else{
                 if(ints[i+1][j]!=1&&ints[i+1][j]!=6){
                     ints[i+1][j]=2;
                     ints[i][j]=6;
                     Boolean dg = dg(ints, i +1, j,d, b, k, l);
                     if(!dg){
                         ints[i+1][j]=0;
                         ints[i][j]=2;
                     }else {
                         return true;
                     }
                 }
             }
        }
//       右移
        if(j+1<l){
             if(i==d&&j+1==b){
                return true;
            }else if(ints[i][j+1]!=1&&ints[i][j+1]!=6){
                 ints[i][j+1]=2;
                 ints[i][j]=6;
                 Boolean dg = dg(ints, i, j+1,d, b, k, l);
                 if(!dg){
                     ints[i][j+1]=0;
                     ints[i][j]=2;
                 }else {
                     return true;
                 }
             }
        }
        return false;
    }

}
