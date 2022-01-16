package com.wudidemiao.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author wudidemiaoa
 * @date 2022/1/15
 * @apiNote
 */
@Slf4j(topic = "c.Test11")
public class Test11 {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool(4);
        Integer invoke = pool.invoke(new MyTask(5));
        System.out.println(invoke);
    }
}

// 无返回值
//class MyTask extends RecursiveAction {
//有返回值  1-n之间整数的和
class MyTask extends RecursiveTask<Integer> {

    private int n;

    public MyTask(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if (n == 1) {
            return 1;
        }
        MyTask myTask = new MyTask(n - 1);
        ForkJoinTask<Integer> fork = myTask.fork();
        return n + fork.join();
    }
}
