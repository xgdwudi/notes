package com.wudidemiao.aqs;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wudidemiaoa
 * @date 2022/1/15
 * @apiNote
 */
public class Test3 {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        ExecutorService pool = Executors.newCachedThreadPool();
        pool.submit(()->{

        });
    }
}
