package com.wudidemiao.threads.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//第一步，创建资源类，定义属性和操作方法
class LShare{
    private int number = 0;
//    创建Lock
    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    public void incr(){
        lock.lock();
        try {
            while (number!=0){
                condition.await();
            }
            number++;
            System.out.println(Thread.currentThread().getName() + "::" + number);
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void decr(){
        lock.lock();
        try {
            while (number!=1){
                condition.await();
            }
            number--;
            System.out.println(Thread.currentThread().getName() + "::" + number);
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
public class ThreadDemo2 {
    public static void main(String[] args) {
        LShare share = new LShare();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                    share.incr();
            }
        },"AA").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                    share.decr();
            }
        },"BB").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                    share.incr();
            }
        },"CC").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                    share.decr();
            }
        },"DD").start();
    }
}
