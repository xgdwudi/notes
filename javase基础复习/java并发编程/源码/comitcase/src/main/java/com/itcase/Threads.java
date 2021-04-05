package com.itcase;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * 创建线程
 */
@Slf4j(topic = "c.Test")
public class Threads {
    public static void main(String[] args) {
       Thread thread = new Thread("1"){
            public void run(){
                Threads.log.debug(Thread.currentThread().getName());
            }
        };
       thread.start();
    }
}

/**
 * 创建线称第二种方式
 */
@Slf4j(topic = "c.Test")
class MyThread {
    public static void main(String[] args) {
        Runnable runnable = () -> {
                log.debug(Thread.currentThread().getName());
            };
        new Thread(runnable,"多线程").start();
    }
}

/**
 * 创建线程的三种方法
 */
class MyThreadTest {
    public static void main(String[] args) throws Exception {
        FutureTask<Integer> integerFutureTask = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 123;
            }
        });
        Thread zhangsan = new Thread(integerFutureTask, "zhangsan");
        zhangsan.start();
        System.out.println(integerFutureTask.get());
//        Callable<Object> callable = new Callable<Object>() {
//            public Object call() throws Exception {
//                return "执行成功";
//            }
//        };
//        callable.
    }
}
/**
 *  Block
 */
class Number{
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
                while (true){

                }
        }, "t1");
        t1.setDaemon(true);
        t1.start();
        System.out.println("adds");

    }
}
