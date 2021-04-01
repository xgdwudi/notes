package com.atguigu.link;

/**
 *
 */
public class links {
    public static void main(String[] args) {
        Runnable runnable = ()->{
            System.out.println(Thread.currentThread().getName()+"开始");
            Adds adds = new Adds();
            System.out.println(Thread.currentThread().getName()+"结束");
        };
        Thread t1 = new Thread(runnable, "线程1");
        Thread t2 = new Thread(runnable, "线程2");
        t1.start();
        t2.start();
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
