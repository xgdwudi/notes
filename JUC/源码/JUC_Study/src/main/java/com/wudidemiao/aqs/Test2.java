package com.wudidemiao.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author wudidemiaoa
 * @date 2022/1/15
 * @apiNote
 */
@Slf4j(topic = "c.Test2")
public class Test2 {
    public static void main(String[] args) throws InterruptedException {
        DataContainer dataContainer = new DataContainer();
        new Thread(()->{
            dataContainer.write();
        },"t1").start();
        new Thread(()->{
            dataContainer.write();
        },"t2").start();
    }
}

@Slf4j(topic = "c.Test2")
class DataContainer {
    private Object data;

    private ReentrantReadWriteLock rw = new ReentrantReadWriteLock();

    private ReentrantReadWriteLock.ReadLock readLock = rw.readLock();

    private ReentrantReadWriteLock.WriteLock writeLock = rw.writeLock();

    public Object read() {
        log.debug("读suo");
        readLock.lock();
        try {
            Thread.sleep(1000);
            log.debug("读取");
            return data;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            log.debug("shifang读suo");
            readLock.unlock();
        }
        return null;
    }

    public void write() {
        log.debug("写suo");
        writeLock.lock();
        try {
            Thread.sleep(1000);
            log.debug("写入");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            log.debug("shifang写suo");
            writeLock.unlock();
        }
    }
}