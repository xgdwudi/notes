package com.wudidemiao.test;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Test1")
public class Test1 {
    public static void main(String[] args) {
        Thread thread = new Thread("t1") {
            @Override
            public void run() {
                super.run();
                log.debug("running");
            }
        };

        thread.start();

        log.debug("running");
    }
}
