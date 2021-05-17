package com.itcase;

import java.util.concurrent.locks.ReentrantLock;

public class Test04 {
    static final  Object lock = new Object();
    static Boolean t2runedd = false;
//    ReentrantLock
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (lock){
                while (!t2runedd){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
               System.out.println("1111");
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            synchronized (lock){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                t2runedd=true;
                lock.notify();
            }
        }, "t2");
        t1.start();
        t2.start();
    }
}
