package com.wudidemiao.threads.lock;

import sun.security.krb5.internal.Ticket;

import java.util.concurrent.locks.ReentrantLock;

// 创建资源类
class LTicket {
    private int number = 30;

    //    创建可重入锁
    private final ReentrantLock lock = new ReentrantLock();

    //     买票方法
    public void sale() {
        try {
            //        上锁
            lock.lock();
            if (number > 0) {
                number--;
                System.out.println(Thread.currentThread().getName() + ":: 剩下:" + number);
            } else {
                System.out.println("没票了");
            }
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
    }
}


public class LSaleTicket {
    public static void main(String[] args) {
        LTicket ticket = new LTicket();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "aa").start();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "bb").start();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "cc").start();
    }
}
