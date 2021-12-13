package com.atguigu.代码追踪;

public class OperandStackTest {
    static int index = 0;

    static {
        index = 5;
    }

    public void testAddOperation() {
        byte i = 15;
        int j = 8;
        int k = i + j;
    }
}
