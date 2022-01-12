---
title: JUC高并发编程
date: 2022/01/05 20:33:25  
tags:
- java
- JUC
- 多线程
categories: JUC高并发编程
thumbnail: https://tse1-mm.cn.bing.net/th/id/OIP-C.FFKsFar_0s_IkW7Xvvm2wQHaEK?w=314&h=180&c=7&r=0&o=5&pid=1.7
cover: https://tse1-mm.cn.bing.net/th/id/OIP-C.FFKsFar_0s_IkW7Xvvm2wQHaEK?w=314&h=180&c=7&r=0&o=5&pid=1JUC
---

# JUC入门级笔记

# JUC（java.util.concurrent）概述

## 什么是JUC

在Java中，线程部分是一个重点，本篇文章说的JUC也是关于线程的。JUC就是java.util.concurrent工具包的简称。这是一个处理线程的工具包，JDK
1.5开始出现的。

<!-- more -->

## 线程和进程概念

### 进程和线程

**进程**（Process）是计算机中的程序关于某数据集合上的一次运行活动，是系统进行资源分配和调度的基本单位，是操作系统结构的基础。在当代面向线程设计的计算机结构中，进程是线程的容器。程序是指令、数据及其组织形式的描述，进程是程序的实体。是计算机中的程序关于某数据集合上的一次运行活动，是系统进行资源分配和调度的基本单位，是操作系统结构的基础。程序是指令、数据及其组织形式的描述，进程是程序的实体。

**线程**（thread）是操作系统能够进行运算调度的最小单位。它被包含在进程之中，是进程中的实际运作单位。一条线程指的是进程中一个单一顺序的控制流，一个进程中可以并发多个线程，每条线程并行执行不同的任务。

**总结来说**：

- 进程：指在系统中正在运行的一个应用程序；程序一旦运行就是进程；进程一一资源分配的最小单位。
- 线程：系统分配处理器时间资源的基本单元，或者说进程之内独立执行的一个单元执行流。线程——程序执行的最小单位。

### 线程的状态

#### 线程状态枚举类

Thread.State

```
public enum State（
/**：
*Thread state for a thread which has not yet started..
*/：
    NEW，（新建）;
    RUNNABLE(准备就绪)
    BLOCKED(阻塞)
    WAITING(等待)
    TIMED_WAITING(过时不候)
    TERMINATED(终结)
}

```



### wait和sleep

（1)   sleep是Thread的静态方法，wait是Object的方法，任何对象实例都能调用。
		（2）sleep不会释放锁，它也不需要占用锁。wait会释放锁，但调用它的前提是当前线程占有锁（即代码要在synchronized中）。
		（3）它们都可以被interrupted方法中断。

### 并发和并行

#### 串行模式。

串行表示所有任务都——按先后顺序进行。串行意味着必须先装完一车柴才能运送这车柴，只有运送到了，才能卸下这车柴，并且只有完成了这整个三个步骤，才能进行下一个步骤。
		**串行是一次只能取得一个任务，并执行这个任务。**

#### 并行模式

并行意味着可以同时取得多个任务，并同时去执行所取得的这些任务。并行模式相当于将长长的一条队列，划分成了多条短队列，所以并行缩短了任务队列的长度。并行的效率从代码层次上强依赖于多进程/多线程代码，从硬件角度上则依赖于多核CPU。

#### 并发

并发（concurrent）指的是多个程序可以同时运行的现象，更细化的是多进程可以同时运行或者多指令可以同时运行。但这不是重点，在描述并发的时候也不会去扣这种字眼是否精确，==并发的重点在于它是一种现象==，==并发描述的是多进程同时运行的现象==。但实际上，对于单核心CPU来说，同一时刻只能运行一个线程。所以，这里的“同时运行“表示的不是真的同一时刻有多个线程运行的现象，这是并行的概念，而是提供一种功能让用户看来多个程序同时运行起来了，但实际上这些程序中的进程不是一直霸占CPU的，而是执行一会停一会。

要解决大并发问题，通常是将大任务分解成多个小任务，由于操作系统对进程的调度是随机的，所以切分成多个小任务后，可能会从任一小任务处执行。这可能会出现一些现象：r
  - 可能出现一个小任务执行了多次，还没开始下个任务的情况。这时一般会采用队列或类似的数据结构来存放各个小任务的成果。
  - 可能出现还没准备好第一步就执行第二步的可能。这时，一般采用多路复用或异步的方式，比如只有准备好产生了事件通知才执行某个任务。
  - 可以多进程/多线程的方式并行执行这些小任务。也可以单进程/单线程执行这些小任务，这时很可能要配合多路复用才能达到较高的效率

**并发：同一时刻多个线程在访问同一个资源，多个线程对一个点例子：春运抢票电商秒杀.…**
        **并行：多项工作一起执行，之后再汇总。回例子：泡方便面，电水壶烧水，一边撕调料倒入桶中。**

### 管程(Monitor-监视器-锁)

是一种同步机制,保证在同一时间,只有一个线程能去访问被保护的数据或者代码,

**jvm中的同步基于进入和退出,使用管程对象实现的**

### 用户线程和守护线程

- 用户线程,自定义线程
- 守护线程:比如垃圾回收

```java
public class Main {
    public static void main(String[] args) {
        Thread aa = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "::" + Thread.currentThread().isDaemon());
            while (true) {

            }
        }, "aa");
        aa.start();
        System.out.println(Thread.currentThread().getName()+"主线程结束");
    }
}
```

**主线程结束了，用户线程还在运行，jvm存活**

**当其他线程结束,守护线程自动结束,jvm结束**



# Lock接口

## Synchronized关键字

#### Synchronized关键字回顾。

synchronized是Java中的关键字，是一种同步锁。它修饰的对象有以下几种：。
   			1. 修饰一个代码块，被修饰的代码块称为同步语句块，其作用的范围是大括号0括起来的代码，作用的对象是调用这个代码块的对象；
   			2. 修饰一个方法，被修饰的方法称为同步方法，其作用的范围是整个方法，作用的对象是调用这个方法的对象；
            			1. 虽然可以使用 synchronized 来定义方法，但synchronized 并不属于方法定义的一部分，因此，synchronized关键字不能被继承。如果在父类中的某个方法使用了synchronized关键字，而在子类中覆盖了这个方法，在子类中的这个方法默认情况下并不是同步的，而必须显式地在子类的这个方法中加上synchronized 关键字才可以。当然，还可以在子类方法中调用父类中相应的方法，这样虽然子类中的方法不是同步的，但子类调用了父类的同步方法，因此，子类的方法也就相当于同步了。

#### 多线程编程步奏:

第一步创建资源类，在资源类创建属性和操作方法

第二步创建多个线程，调用资源类的操作方法

```java
/*

第一步创建资源类，在资源类创建属性和操作方法

第二步创建多个线程，调用资源类的操作方法

*/

class Ticket{
//   票数
    private int number = 30 ;
//    买票
    public synchronized void sale(){
//
        if(number>0){
            number--;
            System.out.println(Thread.currentThread().getName()+":: 剩下:"+number );
        }else {
            System.out.println("没票了");
        }
    }
}
public class SaleTicket {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(()->{
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        },"aa").start();
        new Thread(()->{
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        },"bb").start();
        new Thread(()->{
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        },"cc").start();
    }
}
```



## 什么是Lock接口

Lock锁实现提供了比使用同步方法和语句可以获得的更广泛的锁操作。它们允许更灵活的结构，可能具有非常不同的属性，并且可能支持多个关联的条件对象。Lock提供了比synchronized更多的功能。

Lock与的Synchronized区别。

- Lock不是Java语言内置的，synchronized是Java语言的关键字，因此是内置特性。Lock是一个类，通过这个类可以实现同步访问；
- Lock和synchronized 有一点非常大的不同，采用synchronized 不需要用户去手动释放锁，当synchronized 方法或者synchronized 代码块执行完之后，系统会自动让线程释放对锁的占用；而Lock则必须要用户去手动释放锁，如果没有主动释放锁，就有可能导致出现死锁现象。

Lock实现可重入锁

```java
package com.wudidemiao.threads.lock;

import sun.security.krb5.internal.Ticket;

import java.util.concurrent.locks.ReentrantLock;

// 创建资源类
class LTicket {
    private int number = 30;

    //    创建可重入锁
    private final ReentrantLock lock = new ReentrantLock();

    //     买票方法
    public void sale() {
        try {
            //        上锁
            lock.lock();
            if (number > 0) {
                number--;
                System.out.println(Thread.currentThread().getName() + ":: 剩下:" + number);
            } else {
                System.out.println("没票了");
            }
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
    }
}


public class LSaleTicket {
    public static void main(String[] args) {
        LTicket ticket = new LTicket();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "aa").start();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "bb").start();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "cc").start();
    }
}

```

Lock和synchronized有以下几点不同：
  		1. Lock是一个接口，而synchronized是Java中的关键字，synchronized是内置的语言实现；I
  		2. synchronized在发生异常时，会自动释放线程占有的锁，因此不会导致死锁现象发生；而Lock在发生异常时，如果没有主动通过unLock0去释放锁，则很可能造成死锁现象，因此使用Lock 时需要在finally块中释放锁；
  		3. Lock可以让等待锁的线程响应中断，而synchronized却不行，使用synchronized时，等待的线程会一直等待下去，不能够响应中断；。
  		4. 通过Lock 可以知道有没有成功获取锁，而synchronized却无法办到。
  		5. Lock可以提高多个线程进行读操作的效率.

## 创建多线程的多种方式

- 继承Thread类,重写run方法
- 继承Runable类,重写run方法
- 实现CallAble接口,实现call方法
- 线程池

# 线程间通信

步奏:

​	第一步创建资源类，在资源类创建属性和操作方法

​	第二步在资源类操作方法

​	（1）判断

​	（2）干活

​	（3）通知
​			第三步创建多个线程，调用资源类的操作方法

synchronized

```
//创建资源类
class Share {
    private int number = 0;

    //    +1的方法
    public synchronized void incr() throws InterruptedException {
        while (number != 0) {
            this.wait();
        }

        number++;
        System.out.println(Thread.currentThread().getName() + "::" + number);
        // 通知其他线程
        this.notifyAll();
    }

    //    -1的方法
    public synchronized void decr() throws InterruptedException {
        while (number != 1) {
            this.wait();
        }

        number--;
        System.out.println(Thread.currentThread().getName() + "::" + number);
        // 通知其他线程
        this.notifyAll();
    }
}

public class ThreadDemo1 {
    public static void main(String[] args) {
        Share share = new Share();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    share.incr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"AA").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    share.decr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"BB").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    share.incr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"CC").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    share.decr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"DD").start();
    }
}

```

防止虚假唤醒问题

```
对于某一个参数的版本，实现中断和虚假唤醒是可能的，而且此方法应始终在循环中使用：
synchronized（obj）{
while（<condition does not hold>）
obj.wait（）；
..//Perfora action appropriate to condition
```

Lock方式覆写案例

```
package com.wudidemiao.threads.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//第一步，创建资源类，定义属性和操作方法
class LShare{
    private int number = 0;
//    创建Lock
    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    public void incr(){
        lock.lock();
        try {
            while (number!=0){
                condition.await();
            }
            number++;
            System.out.println(Thread.currentThread().getName() + "::" + number);
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void decr(){
        lock.lock();
        try {
            while (number!=1){
                condition.await();
            }
            number--;
            System.out.println(Thread.currentThread().getName() + "::" + number);
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
public class ThreadDemo2 {
    public static void main(String[] args) {
        LShare share = new LShare();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                    share.incr();
            }
        },"AA").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                    share.decr();
            }
        },"BB").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                    share.incr();
            }
        },"CC").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                    share.decr();
            }
        },"DD").start();
    }
}

```



# 线程间定制化通信

# 集合的线程安全

# 多线程锁

# Callable接口

# JUC强大的辅助类

# ReentrantReadWriteLock读写锁

# BlockingQueue阻塞队列

# ThreadPool线程池

# Fork/Join分支合并框架

# CompletableFuture异步回调