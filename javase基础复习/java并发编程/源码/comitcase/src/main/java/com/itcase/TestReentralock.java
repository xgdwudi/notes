package com.itcase;

import java.util.concurrent.locks.ReentrantLock;

public class TestReentralock {

    public static void main(String[] args) {
//        ReentrantLock reentrantLock = new ReentrantLock();
//        reentrantLock.lock();
//        try {
//            
//        }finally {
//            reentrantLock.unlock();
//        }
        adds adds = new adds();
        adds.tryLock();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            adds.unlock();
        }
    }
}

class adds extends ReentrantLock{
    
}
