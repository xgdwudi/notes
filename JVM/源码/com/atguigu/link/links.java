package com.atguigu.link;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
public class links {
    public static void main(String[] args) {
        AtomicInteger integer = new AtomicInteger(0);
        int i = integer.incrementAndGet();
//        System.out.println(i);
//        Runnable runnable = ()->{
//            System.out.println(Thread.currentThread().getName()+"开始");
//            Adds adds = new Adds();
//            System.out.println(Thread.currentThread().getName()+"结束");
//        };
//        Thread t1 = new Thread(runnable, "线程1");
//        Thread t2 = new Thread(runnable, "线程2");
//        t1.start();
//        t2.start();
//        Thread

//        try {
//            Thread.sleep(2000000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}

class Adds{
    static {
       if(true){
           System.out.println(Thread.currentThread().getName());
           while (true){

           }
       }
    }
}
