package com.wudidemiao.test;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j(topic = "c.Test5")
public class Test5 {
    static final Object lock = new Object();

    static boolean t2Runned = false;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                while (!t2Runned) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("1");
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                log.debug("2");
                t2Runned = true;
                lock.notify();
            }
        }, "t2");
        t1.start();
        t2.start();
    }
}
