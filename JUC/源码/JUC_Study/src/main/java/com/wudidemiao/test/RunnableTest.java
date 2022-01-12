package com.wudidemiao.test;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.RunnableTest")
public class RunnableTest {
    public static void main(String[] args) {
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                log.debug("running");
//            }
//        };
//
//        Thread t1 = new Thread(runnable, "t1");
//        t1.start();
//        log.debug("running");


        Runnable runnable = () -> {
            log.debug("running");
        };
        Thread t1 = new Thread(runnable, "t1");
        t1.start();
        log.debug("running");
    }
}
