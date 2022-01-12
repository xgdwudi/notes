package com.wudidemiao.threads;

public class Main {
    public static void main(String[] args) {
        Thread aa = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "::" + Thread.currentThread().isDaemon());
            while (true) {

            }
        }, "aa");
        aa.setDaemon(true);
        aa.start();
        System.out.println(Thread.currentThread().getName()+"主线程结束");
    }
}
