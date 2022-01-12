package com.wudidemiao.test;

import lombok.extern.slf4j.Slf4j;

import static java.lang.Thread.sleep;

@Slf4j(topic = "c.DeamonTest")
public class DeamonTest {
    public static void main(String[] args) throws InterruptedException {
        log.debug("running..");
        Thread t = new Thread(() -> {
            log.debug("running");
            try {
                sleep(2);
                log.debug("end..");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");
        t.setDaemon(true);
        t.start();
        sleep(1);
        log.debug("end...");
    }
}

class Room {
    private int counter = 0;

    public void increament() {
        synchronized (this) {
            counter++;
        }
    }

    public void decrement() {
        synchronized (this) {
            counter--;
        }
    }

    public int getCounter(){
        synchronized (this){
            return counter;
        }
    }
}
