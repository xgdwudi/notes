package com.wudidemiao.test;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  根据策略模式实现
 * @author wudidemiaoa
 * @date 2022/1/14
 * @apiNote
 */
@Slf4j(topic = "c.ThreadPools")
public class ThreadPools {
    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(1, 1000, TimeUnit.MILLISECONDS, 1,((queue, task) -> {
//           1.  死等
//            queue.put(task);
//                2.带超时等待
//            boolean offer = queue.offer(task, 500, TimeUnit.MILLISECONDS);
//            log.debug(""+offer);
//                3. 放弃任务执行
//            log.debug("放弃task{}任务",task);
//                4.抛出异常
//            throw new RuntimeException("任务执行失败，"+task);
//                5.让调用者自己去执行任务
            task.run();
        }));
        for (int i = 0; i < 4; i++) {
            int k = i;
            threadPool.execute(() -> {
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("{}", k);
            });
        }
    }
}

@Slf4j(topic = "c.ThreadPool")
class ThreadPool {
    //    任务队列
    private BlockingQueues<Runnable> taskQueue;

    //    线程集合
    private HashSet<Worker> workers = new HashSet<>();

    //    核心线程数
    private int coreSize;

    //    获取任务的超时时间
    private long timeOut;

    //    时间单位
    private TimeUnit timeUnit;

//    拒绝策略
    private RejectPolicy<Runnable> rejectPolicy;

    //    执行任务
    public void execute(Runnable task) {
//        当任务数没有超过coreSize直接教给workers对象执行
//        如果任务数超过coreSize时，加入任务队列
        synchronized (workers) {
            if (workers.size() < coreSize) {
                Worker worker = new Worker(task);
                log.debug("新增worker{}", task);
                workers.add(worker);
                worker.start();
            } else {
                log.debug("加入任务队列{}", task);
//                1.死等
//                2.带超时等待
//                3. 放弃任务执行
//                4.抛出异常
//                5.让调用者自己去执行任务
//                taskQueue.put(task);
//                taskQueue.offer(task,timeOut,timeUnit);
                taskQueue.tryPut(rejectPolicy,task);
            }
        }
    }

    public ThreadPool(int coreSize, long timeOut, TimeUnit timeUnit, int queueCapcity,RejectPolicy<Runnable> rejectPolicy) {
        this.taskQueue = new BlockingQueues(queueCapcity);
        this.coreSize = coreSize;
        this.timeOut = timeOut;
        this.timeUnit = timeUnit;
        this.rejectPolicy = rejectPolicy;
    }

    class Worker extends Thread {
        private Runnable task;

        public Worker(Runnable task) {
            this.task = task;
        }

        //        执行任务
        @Override
        public void run() {
//          1.  当task不为空,直接执行
//            2. task执行完毕后，在从任务队列中添加任务并执行
//            while (task != null || (task = taskQueue.take()) != null) {
//            加上超时时间
            while (task != null || (task = taskQueue.poll(timeOut, timeUnit)) != null) {
                try {
                    log.debug("正在执行。。。{}", task);
                    task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    task = null;
                }
            }

            synchronized (workers) {
                log.debug("worker被移除{}", this);
                workers.remove(this);
            }

        }
    }
}
@Slf4j(topic = "c.BlockingQueues")
class BlockingQueues<T> {
    //    1.任务队列
    private Deque<T> queue = new ArrayDeque<>();

    //    2. 锁
    private ReentrantLock lock = new ReentrantLock();

    //    3. 生产者条件变量
    private Condition fullWaitSet = lock.newCondition();

    //    4.消费者条件变量
    private Condition emptyWaitSet = lock.newCondition();

    //    5. 容量
    private int capcity;

    public BlockingQueues(int queueCapcity) {
        this.capcity = queueCapcity;
    }

    //    带超时的阻塞获取
    public T poll(long timeout, TimeUnit unit) {
        lock.lock();
        try {
//            将超时时间统一转换为纳秒
            long nanos = unit.toNanos(timeout);
            while (queue.isEmpty()) {
                try {
//                    返回的是剩余时间
                    if (nanos <= 0) {
                        return null;
                    }
                    nanos = emptyWaitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = queue.removeFirst();
            fullWaitSet.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }

    //    阻塞获取
    public T take() {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                try {
                    emptyWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = queue.removeFirst();
            fullWaitSet.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }

    //    阻塞添加
    public void put(T element) {
        lock.lock();
        try {
            while (queue.size() == capcity) {
                try {
                    log.debug("等待加入任务队列。。。{}",element);
                    fullWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("加入任务队列。。。{}",element);
            queue.addLast(element);
            emptyWaitSet.signal();
        } finally {
            lock.unlock();
        }
    }

//    带超时时间的阻塞添加
    public boolean offer(T element,long timeOut,TimeUnit timeUnit){
        lock.lock();
        try {
            long nanos = timeUnit.toNanos(timeOut);
            while (queue.size() == capcity) {
                try {
                    log.debug("等待加入任务队列。。。{}",element);
                    if(nanos<=0){
                        return false;
                    }
                    nanos = fullWaitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("加入任务队列。。。{}",element);
            queue.addLast(element);
            emptyWaitSet.signal();
            return true;
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        lock.lock();
        try {
            return queue.size();
        } finally {
            lock.unlock();
        }
    }

    public void tryPut(RejectPolicy<T> rejectPolicy, T task) {
       lock.lock();
       try {
//            判断队列是否已满
           if(queue.size()==capcity){
               rejectPolicy.reject(this,task);
           }else {
               log.debug("加入任务队列。。。{}",task);
               queue.addLast(task);
               emptyWaitSet.signal();
           }
       }finally {
           lock.unlock();
       }
    }
}

@FunctionalInterface
        // 拒绝策略
interface RejectPolicy<T> {
    void reject(BlockingQueues<T> queue, T task);
}
