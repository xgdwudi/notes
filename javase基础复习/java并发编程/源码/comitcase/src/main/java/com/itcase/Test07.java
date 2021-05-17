package com.itcase;



public class Test07 {
    public static void main(String[] args) {
        System.out.println(pd(new int[]{4,1,0,0,4}));
    }

    public static Boolean pd(int[] ints){
            if(pd1(ints,0)){
                return true;
            }else {
                return false;
            }
    }

    public static Boolean pd1(int[] ints,int i){
        int j = ints[i];
        for (int k=j;k>0;k--){
            if(i+k==ints.length-1){
                return true;
            }
            if(ints[i+k]==0){
                return false;
            }
            if(i+k<ints.length){
                 if(pd1(ints,i+k)){
                     return true;
                 }
            }
        }
        return false;
    }
}
