package com.wudidemiao.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author wudidemiaoa
 * @date 2022/1/15
 * @apiNote
 */
@Slf4j(topic = "c.Test1")
public class Test1 {
    public static void main(String[] args) {
        MyLock myLock = new MyLock();
        new Thread(() -> {
            myLock.lock();
            try {
                log.debug("加锁成功");
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                log.debug("接锁成功");
                myLock.unlock();
            }
        }).start();
        new Thread(() -> {
            myLock.lock();
            try {
                log.debug("加锁成功。。");
            } finally {
                log.debug("接锁成功。。。");
                myLock.unlock();
            }
        }).start();
    }
}

// 自定义锁（不可重入锁）
class MyLock implements Lock {

    //    独占锁
    class MySync extends AbstractQueuedSynchronizer {
        //        尝试获取锁
        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0, 1)) {
//                加上锁，设置owner为当前线程
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        //尝试释放锁
        @Override
        protected boolean tryRelease(int arg) {
//                加上锁，设置owner为当前线程
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        //是否持有独占锁
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        public Condition newCondition() {
            return new ConditionObject();
        }
    }

    public MySync mySync = new MySync();

    @Override // 加锁，如不成功则静茹等待队列
    public void lock() {
        mySync.acquire(1);
    }

    @Override // 可打断
    public void lockInterruptibly() throws InterruptedException {
        mySync.acquireInterruptibly(1);
    }

    @Override // 尝试加锁，加锁不成公失败
    public boolean tryLock() {
        return mySync.tryAcquire(1);
    }

    @Override  // 尝试加锁，可超时
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return mySync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override  // jiesuo
    public void unlock() {
        mySync.release(0);
    }

    @Override  // 成员变量
    public Condition newCondition() {
        return mySync.newCondition();
    }
}
