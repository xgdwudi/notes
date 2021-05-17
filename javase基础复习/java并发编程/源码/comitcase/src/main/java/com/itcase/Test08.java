package com.itcase;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Test08 {
    public static void main(String[] args) {

    }
}
// 不可重入锁
class MyLock implements Lock {
    //    创建同步器类  独占锁
    class Mysync extends AbstractQueuedSynchronizer {
        @Override // 尝试获取锁
        protected boolean tryAcquire(int arg) {
            if(compareAndSetState(0,1)){
//                加锁并设置owner为当前线程
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override  // 尝试释放锁
        protected boolean tryRelease(int arg) {
           setExclusiveOwnerThread(null);
//           将此方法放在后面，防止指令重排
           setState(0);
           return true;
        }

        @Override  // 是否持有独占锁
        protected boolean isHeldExclusively() {
            return getState()==1;
        }

        public Condition newCondition(){
            return new ConditionObject();
        }
    }
    private Mysync mysync = new Mysync();

    @Override
    public void lock() {
        mysync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        mysync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return mysync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return mysync.tryAcquireNanos(1,unit.toNanos(time));
    }

    @Override
    public void unlock() {
        mysync.release(1);
    }

    @Override
    public Condition newCondition() {
        return mysync.newCondition();
    }
}
