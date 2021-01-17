package com.queue;

/**
 * @version 1.0
 * 注意：本内容仅限于西安城市发展资源信息有限公司内部传阅，禁止外泄以及用于其他的商业目
 * @ClassName Queue8
 * @Description TODO
 * @Aurhor xu
 * @Ddte 2021/1/9 11:20
 **/
public class Queue8 {
//    定义一共有几个皇后
    private static int num=8;
//    定义存放皇后的数组
    private static int[] sums=new int[num];
    public static void main(String[] args) {
        checked(0);
    }
//    放置皇后
    public static void checked(int adds){
        if(num==adds){
            traversal();
            return;
        }
        for (int i=0;i<8;i++){
            sums[adds]=i;
            if(justage(adds)){
                checked(adds+1);
            }
        }
    }
//   判断放置的皇后是否符合要求
    public static Boolean justage(int num){
       for (int i =0;i<num;i++){
            if(sums[i]==sums[num] || Math.abs(num-i)==Math.abs(sums[num]-sums[i])){
                return false;
            }
       }
       return true;
    }
//     遍历数组sum1
    public static void traversal(){
        for (int sum : sums) {
            System.out.print(sum+" ");
        }
        System.out.println();
    }
}
