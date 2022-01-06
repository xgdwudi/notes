package com.atguigu.代码追踪;

public class OperandStackTest {
//    static int index = 0;
//
//    static {
//        index = 5;
//    }
//
//    public void testAddOperation() {
//        byte i = 15;
//        int j = 8;
//        int k = i + j;
//    }

    public static void main(String[] args) {
        String str = new String("abc");
        System.out.println(str.hashCode());
        addds(str);
        System.out.println(str);
    }

    public static void addds(String str) {
        str = new String("vbc");
    }
}
