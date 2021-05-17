package com.itcase;

import java.util.concurrent.*;

public class Test06 {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 4, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>());
        ExecutorService executorService = Executors.newFixedThreadPool(5);
    }
}
