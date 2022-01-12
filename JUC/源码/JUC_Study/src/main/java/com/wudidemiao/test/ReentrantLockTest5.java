package com.wudidemiao.test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest5 {
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
//        创建一个新的条件变量
         Condition condition1 = lock.newCondition();
         Condition condition2 = lock.newCondition();

         lock.lock();
        try {
//            进入某个休息是等待
            condition1.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
//        叫醒一个休息的线程
        condition1.signal();
//        全部叫醒
        condition1.signalAll();
    }

    public static void m1(){
        lock.lock();
        try {
            m3();
        } finally {
            lock.unlock();
        }
    }

    public static void m3(){
        lock.lock();
        try {

        } finally {
            lock.unlock();
        }
    }
}
