package com.wudidemiao.test;

import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

/**
 * @author wudidemiaoa
 * @date 2022/1/14
 * @apiNote
 */
@Slf4j(topic = "c.Test10")
public class Test10 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        Future<Boolean> start = pool.submit(() -> {
            log.debug("start");
            int i = 1 / 0;
            return true;
        });
        start.get();
    }

    public static void test03() {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
//        pool.scheduleAtFixedRate(()->{
//            log.debug("running");
//            try {
//                TimeUnit.SECONDS.sleep(2);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        },1,1,TimeUnit.SECONDS);

        pool.scheduleWithFixedDelay(() -> {
            log.debug("running");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    public static void test02() {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);
        scheduledThreadPool.schedule(() -> {
            log.debug("task1");
//            try {
//                TimeUnit.SECONDS.sleep(1);
//                     int i = 1 / 0;
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

        }, 1, TimeUnit.SECONDS);

        scheduledThreadPool.schedule(() -> {
            log.debug("task2");
        }, 1, TimeUnit.SECONDS);
    }

    public static void test01() {
        Timer timer = new Timer();
        TimerTask timerTask01 = new TimerTask() {
            @Override
            public void run() {
//                try {
                log.debug("TimerTask01");
//                    sleep(1);
                int i = 1 / 0;
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        };
        TimerTask timerTask02 = new TimerTask() {
            @Override
            public void run() {
                log.debug("TimerTask02");
            }
        };
        log.debug("start..");
        timer.schedule(timerTask01, 1000);
        timer.schedule(timerTask02, 1000);
    }
}
