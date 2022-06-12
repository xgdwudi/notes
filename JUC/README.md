---
title: JUC高并发编程
date: 2022/01/08 20:33:25  
tags:
- java
- JUC
- 多线程
categories: JUC高并发编程
thumbnail: https://tse1-mm.cn.bing.net/th/id/OIP-C.FFKsFar_0s_IkW7Xvvm2wQHaEK?w=314&h=180&c=7&r=0&o=5&pid=1.7
cover: https://tse1-mm.cn.bing.net/th/id/OIP-C.FFKsFar_0s_IkW7Xvvm2wQHaEK?w=314&h=180&c=7&r=0&o=5&pid=1JUC

---



# 概览

前置知识：

​	java Web开发、jdbc开发、Web服务器、分布式框架

<!-- more -->

pom依赖

```xml
   <properties>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.10</version>
        </dependency>
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.2.3</version>
        </dependency>
    </dependencies>
```

logback.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration
               xmlns="http://ch.qos.logback/xml/ns/logback"
               xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xsi:schemaLocation="http://ch.qos.1ogback/xml/ns/logback logback.xsd">
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%date{HH:mm:ss}[%t]%logger-%m%n</pattern>
        </encoder>
    </appender>
    <logger name="c" level="debug" additivity="false">
        <appender-ref ref="STDOUT"/>
    </logger>
    <root level="ERROR">
        <appender-ref ref="STDOUT"/>
    </root>
</configuration>
```



# 进程和线程

## 进程和线程

### 进程

- 程序由指令和数据组成，但这些指令要运行，数据要读写，就必须将指令加载至CPU，数据加载至内存。在指令运行过程中还需要用到磁盘、网络等设备。进程就是用来加载指令、管理内存、管理IO的
- 当一个程序被运行，从磁盘加载这个程序的代码至内存，这时就开启了一个进程。
- 进程就可以视为程序的一个实例。大部分程序可以同时运行多个实例进程（例如记事本、画图、浏览器等），也有的程序只能启动一个实例进程（例如网易云音乐、360安全卫士等）

### 线程

- 一个进程之内可以分为一到多个线程。
- 一个线程就是一个指令流，将指令流中的一条条指令以一定的顺序交给CPU执行
- Java中，线程作为最小调度单位，进程作为资源分配的最小单位。在windows中进程是不活动的，只是作为线程的容器

### 二者对比

- 进程基本上相互独立的，而线程存在于进程内，是进程的一个子集
- 进程拥有共享的资源，如内存空间等，供其内部的线程共享
- 进程间通信较为复杂
  - 同一台计算机的进程通信称为IPC（Inter-process communication）
  - 不同计算机之间的进程通信，需要通过网络，并遵守共同的协议，例如HTTP
- 线程通信相对简单，因为它们共享进程内的内存，一个例子是多个线程可以访问同一个共享变量
- 线程更轻量，线程上下文切换成本一般上要比进程上下文切换低

## 并行和并发

### 并发

单核cpu下，线程实际还是串行执行的。操作系统中有一个组件叫做任务调度器，将cpu的时间片（windows下时间片最小约为15毫秒）分给不同的线程使用，只是由于cpu在线程间（时间片很短）的切换非常快，人类感觉是同时运行的。总结为一句话就是：微观串行，宏观并行，

一般会将这种线程轮流使用CPU的做法称为并发，concurrent

![image-20220108172335706](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220108172335706.png)

### 并行

多核cpu下，每个核（core）都可以调度运行线程，这时候线程可以是并行的了

![image-20220108172605284](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220108172605284.png)



![image-20220108172506194](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220108172506194.png)

### 总结

**并发是同一时间应对多件事情的能力**

**并行是同一段时间动手做多件事情的能力**

### 应用之异步调用

- 需要等待结果返回，才能继续运行就是同步

- 不需要等待结果返回，就能继续运行就是异步



注意：同步在多线程中还有另外一层意思，是让多个线程步调一致

1）设计
		多线程可以让方法执行变为异步的（即不要巴巴干等着）比如说读取磁盘文件时，假设读取操作花费了5秒钟，如果没有线程调度机制，这5秒调用者什么都做不了，其代码都得暂停.…
		2）结论

- 比如在项目中，视频文件需要转换格式等操作比较费时，这时开一个新线程处理视频转换，避免阻塞主线程
- tomcat的异步servlet也是类似的目的，让用户线程处理耗时较长的操作，避免阻塞 tomcat的工作线程
- ui程序中，开线程进行其他操作，避免阻塞ui线程



# java线程

## 创建和使用线程

#### 方法一，直接使用Thread

```
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

```

#### 方法二：使用Runnable配合Thread

- Thread代表线程
- Runnable可运行的任务（线程要执行的代码）

```
@Slf4j(topic = "c.RunnableTest")
public class RunnableTest {
    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                log.debug("running");
            }
        };

        Thread t1 = new Thread(runnable, "t1");
        t1.start();
        log.debug("running");
    }
}
```

lamda简化

```

        Runnable runnable = () -> {
            log.debug("running");
        };
        Thread t1 = new Thread(runnable, "t1");
        t1.start();
        log.debug("running");
```

##### 原理只Thread于Runnable 的关系

小结

- 方法1是把线程和任务合并在了一起，方法2是把线程和任务分开了
- 用Runnable 更容易与线程池等高级API配合
- 用Runnable 让任务类脱离了Thread继承体系，更灵活

#### 方式三：FutureTask 配合 Thread

FutureTask能够接收Callable类型的参数，用来处理有返回结果的情况

```
@Slf4j(topic = "c.FutureTaskTest")
public class FutureTaskTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("running...");
                return 11;
            }
        });
//        task.run();
        Thread t1 = new Thread(task, "t1");
        t1.start();
        Integer integer = task.get();
        log.debug(integer + "");
    }
}
```

## 查看进程线程的方法

**windows**

- 任务管理器可以查看进程和线程数，也可以用来杀死进程
- tasklist 查看进程
- taskki11杀死进程



**linux**

- ps-fe查看所有进程
- ps-fT-p<PID>查看某个进程（PID）的所有线程
- ki11杀死进程
- top按大写H切换是否显示线程
- top-H-p<PID>查看某个进程（PID）的所有线程

java

- ps命令查看所有Java进程
- jstack<PID>查看某个Java进程（PID）的所有线程状态
- jconsole来查看某个Java进程中线程的运行情况（图形界面）

jconsole 远程监控配置

- 需要以如下方式运行你的java类

  ```
  java-Djava.rmi.server.hostname=ip地址-Dcom.sun.management.jmxremote-
  Dcom.sun.management.jmxremote.port=连接端口-Dcom.sun.management.jmxremote.ss1=是否安全连接-Dcom.sun.management.jmxremote.authenticate=是否认证java类
  ```

    修改/etc/hosts文件将127.0.0.1映射至主机名

  如果要认证访问，还需要做如下步骤

    复制jmxremote.password文件
    修改jmxremote.password和jmxremote.access文件的权限为600即文件所有者可读写
    连接时填入controlRole（用户名），R&D（密码）

## 线程运行原理

栈与栈帧
Java Virtual Machine Stacks（Java虚拟机栈）我们都知道JVM中由堆、栈、方法区所组成，其中栈内存是给谁用的呢？其实就是线程，每个线程启动后，虚拟机就会为其分配一块栈内存。

- 每个栈由多个栈帧（Frame）组成，对应着每次方法调用时所占用的内存
- 每个线程只能有一个活动栈帧，对应着当前正在执行的那个方法

线程上下文切换（Thread Context Switch）

因为以下一些原因导致cpu不再执行当前的线程，转而执行另一个线程的代码

- 线程的cpu时间片用完
- 垃圾回收
- 有更高优先级的线程需要运行
- 线程自己调用了sleep、yield、wait、join、park、synchronized、lock等方法chengxu当Context Switch发生时，需要由操作系统保存当前线程的状态，并恢复另一个线程的状态，Java中对应的概念就是程序计数器（Program Counter Register），它的作用是记住下一条jvm指令的执行地址，是线程私有的。

当Context Switch发生时，需要由操作系统保存当前线程的状态，并恢复另一个线程的状态，Java中对应的概念就是程序计数器（Program Counter Register），它的作用是记住下一条jvm指令的执行地址，是线程私有的

- 状态包括程序计数器、虚拟机栈中每个栈帧的信息，如局部变量、操作数栈、返回地址等
- Context Switch频繁发生会影响性能

## 常见方法

| 方法名           | static | 功能说明                                                   | 注意                                                         |
| ---------------- | ------ | ---------------------------------------------------------- | ------------------------------------------------------------ |
| start()          |        | 启动一个新线程，在新的线程运行run方法中的代码              | start 方法只是让线程进入就绪，里面代码不一定立刻运行（CPU的时间片还没分给它）。每个线程对象的start方法只能调用一次，如果调用了多次会出现I IllegalThreadStateException |
| run()            |        | 新线程启动后会调用的方法                                   | 如果在构造Thread对象时传递了Runnable参数，则线程启动后会调用Runnable中的run方法，否则默认不执行任何操作。但可以创建Thread的子类对象，来覆盖默认行为 |
| join()           |        | 等待线程运行结束                                           |                                                              |
| join(n)          |        | 等待线程运行结束,最多等待n毫秒                             |                                                              |
| getId()          |        | 获取线程长整型的id                                         | id唯一                                                       |
| getName()        |        | 获取线程名                                                 |                                                              |
| setName(String)  |        | 设置线程名                                                 |                                                              |
| getPriority()    |        | 获取线程优先级                                             |                                                              |
| setPriority(int) |        | 修改线程优先级                                             | java中规定线程优先级是1~10的整数，较大的优先级能提高该线程被CPU调度的机率 |
| getState()       |        | 获取线程状态                                               | Java中线程状态是用6个enum表示，分别为：NEW，RUNNABLE，BLOCKED，WAITING，TIMED WAITING，TERMINATED |
| isInterrupted()  |        | 判断是否被打断                                             | 不会清除打断标记                                             |
| isAlive()        |        | 线程是否存活（还没有运行完毕）                             |                                                              |
| interrupt()      |        | 打断线程                                                   | 如果被打断线程正在sleep，wait，join会导致被打断的线程抛出InteruptedException，并清除打断标记；如果打断的正在运行的线程，则会设置打断标记；park的线程被打断，也会设置打断标记 |
| interrupted()    | static | 判断当前线程是否被打断                                     | 会清除打断标记                                               |
| currentThread()  | static | 获取当前正在执行的线程                                     |                                                              |
| sleep(long n)    | static | 让当前执行的线程休眠n毫秒，休眠时让出cpu的时间片给其它线程 |                                                              |
| yieId()          | static | 提示线程调度器让出当前线程对CPU的使用                      | 主要是为了测试和调试                                         |



### 调用start() 和run()

**调用start()方法会启动一个新线程，直接调用run()方法不会启动一个一个新线程，run()方法会在本线程中运行。**



### Sleep和yield

**sleep**

1. 调用sleep会让当前线程从Raming 进入Tmed Waiting状态
2. 其它线程可以使用interupt方法打断正在睡眠的线程，这时sleep方法会抛出InterruptedException
3. 睡眠结束后的线程未必会立刻得到执行
4. 建议用TimeUnit的sleep代替Thread的sleep来获得更好的可读性

**yield**

1. 调用yield 会让当前线程从Riming进入Rimmable状态，然后调度执行其它同优先级的线程。如果这时没有同优先级的线程，那么不能保证让当前线程暂停的效果
2. 具体的实现依赖于操作系统的任务调度器

**线程优先级**

- 线程优先级会提示（hint）调度器优先调度该线程，但它仅仅是一个提示，调度器可以忽略它
- 如果cpu比较忙，那么优先级高的线程会获得更多的时间片，但cpu闲时，优先级几乎没作用

防止CPU占用100%

where（true）中空转浪费CPU,这时可以使用yield或sleep来让出cpu的使用权给其他程序

- 可以用wait或条件变量达到类似的效果
- 不同的是，后两种都需要加锁，并且需要相应的唤醒操作，一般适用于要进行同步的场景
- sleep适用于无需锁同步的场景

### 为什么需要join

等待新开的线程运行结束

join();

### 打断方法interrupt()

![image-20220109121741187](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220109121741187.png)

#### 两阶段终止模式

在一个线程T1中如何“优雅终止线程T2？这里的【优雅】指的是给T2一个料理后事的机会。

##### 错误思路

- 使用线程对象的stop）方法停止线程
  - stop方法会真正杀死线程，如果这时线程锁住了共享资源，那么当它被杀死后就再也没有机会释放锁，其它线程将永远无法获取锁）
- 使用System.exit（int）方法停止线程
  - 目的仅是停止一个线程，但这种做法会让整个程序都停止

代码演示：

![image-20220109122801844](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220109122801844.png)

### 打断park线程

打断park线程，不会清空打断线程

```java
@Slf4j(topic = "c.Test4")
public class Test4 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("park...");
            LockSupport.park();
            log.debug("unpark..");
            log.debug("打断状态：{}", Thread.currentThread().isInterrupted());
            // 后面代码任会执行，当打断标记为真的时候，park方法，会失效，当打断标记设置为假的时候，会park()方法会执行              Thread.interrupted()
            LockSupport.park();
            log.debug("unpark..");
        }, "t1");
        t1.start();
        sleep(1);
        t1.interrupt();
    }
}
```

### 不推荐使用的方法

![image-20220109124351315](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220109124351315.png)

### 主线程与守护线程

默认情况下，Java进程需要等待所有线程都运行结束，才会结束。有一种特殊的线程叫做守护线程，只要其它非守护线程运行结束了，即使守护线程的代码没有执行完，也会强制结束。

```
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

```

**注意**

- 垃圾回收器线程就是一种守护线程
- Tomcat中的Acceptor和Poller线程都是守护线程，所以Tomcat 接收到shutdown命令后，不会等待它们处理完当前请求

## 线程的五种状态

**操作系统层面**



![image-20220109125504366](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220109125504366.png)

- 【初始状态】仅是在语言层面创建了线程对象，还未与操作系统线程关联
- 【可运行状态】（就绪状态）指该线程已经被创建（与操作系统线程关联），可以由CPU调度执行
- 【运行状态】指获取了CPU时间片运行中的状态
  - 当CPU时间片用完，会从【运行状态】转换至【可运行状态】，会导致线程的上下文切换
- 【阻塞状态】
  - 如果调用了阻塞API，如BIO读写文件，这时该线程实际不会用到CPU，会导致线程上下文切换，进入【阻塞状态】
  - 等BIO操作完毕，会由操作系统唤醒阻塞的线程，转换至【可运行状态】
  - 与【可运行状态】的区别是，对【阻塞状态】的线程来说只要它们一直不唤醒，调度器就一直不会考虑调度它们
- 【终止状态】表示线程已经执行完毕，生命周期已经结束，不会再转换为其它状态

**java Api层面**

Thread.State六种状态

![image-20220109130126870](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220109130126870.png)

- NEW线程刚被创建，但是还没有调用start（）方法
- RUNNABLE 当调用了start（）方法之后，注意，Java API层面的RUNNABLE状态涵盖了操作系统层面的
  【可运行状态】、【运行状态】和【阻塞状态】（由于BIO导致的线程阻塞，在Java里无法区分，仍然认为是可运行）
- BLOCKED，WAITING，TIMED_WAITING 都是JavaAPI层面对【阻塞状态】的细分，后面会在状态转换一节详述
- TERMTNATED当线程代码运行结束

线程创建但未启动  NEW状态

线程创建已启动  RUNNABLE

线程创建启动后调用SLEEP方法  TIMED_WAITING 

线程创建启动后在等待其他线程结束  未加时限 WAITING

线程创建启动后在尝试获取锁的时候失败，就会陷入阻塞状态   BLOCKED

线程结束后  TERMINATED状态

**小结**

![image-20220109134515644](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220109134515644.png)

# 共享模型之管程

## 共享问题

**临界区 Critical Section**

- 一个程序运行多个线程本身是没有问题的
- 问题出在多个线程访问共享资源
  - 多个线程读共享资源其实也没有问题
  - 在多个线程对共享资源读写操作时发生指令交错，就会出现问题
- 一段代码块内如果存在对共享资源的多线程读写操作，称这段代码块为临界区

例子：

![image-20220109141048108](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220109141048108.png)

## synchronized

互斥锁

**为了避免临界区的竞态条件发生，有多种手段可以达到目的。**

- 阻塞式的解决方案：synchronized，Lock
- 非阻塞式的解决方案：原子变量



本次课使用阻塞式的解决方案：synchronized，来解决上述问题，即俗称的【对象锁】，它采用互斥的方式让同一时刻至多只有一个线程能持有【对象锁】，其它线程再想获取这个【对象锁】时就会阻塞住。这样就能保证拥有锁的线程可以安全的执行临界区内的代码，不用担心线程上下文切换注意
	虽然java中互斥和同步都可以采用synchronized关键字来完成，但它们还是有区别的：

- 互斥是保证临界区的竞态条件发生，同一时刻只能有一个线程执行临界区代码
- 同步是由于线程执行的先后、顺序不同、需要一个线程等待其它线程运行到某个点

语法

```java
        synchronized (对象){
            临界区
        }
```

**你可以做这样的类比：**

- synchronized（对象）中的对象，可以想象为一个房间（room），有唯一入口（门）房间只能一次进入一人进行计算，线程tl，t2想象成两个人
- 当线程tl执行到synchronized（room）时就好比tl进入了这个房间，并锁住了门拿走了钥匙，在门内执行count++代码
- 这时候如果t2也运行到了synchronized（room）时，它发现门被锁住了，只能在门外等待，发生了上下文切换，阻塞住了
- 这中间即使tl的cpu时间片不幸用完，被踢出了门外（不要错误理解为锁住了对象就能一直执行下去哦），这时门还是锁住的，tl仍拿着钥匙，t2线程还在阻塞状态进不来，只有下次轮到t1自己再次获得时间片时才能开门进入
- 当tl执行完synchronized{}块内的代码，这时候才会从obj房间出来并解开门上的锁，唤醒t2线程把钥匙给他。t2线程这时才可以进入obj房间，锁住了门拿上钥匙，执行它的count--代码

**字节码执行流程**

![image-20220109142956021](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220109142956021.png)

![image-20220109143514017](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220109143514017.png)

![image-20220109143545539](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220109143545539.png)

synchronized实际是用对象锁保证了临界区内代码的原子性，临界区内的代码对外是不可分割的，不会被线程切换所打断。

synchronized加到方法上是锁的this对象，

synchronized加到静态方法上锁的是字节码对象



## 线程安全分析

**成员变量和静态变量是否线程安全？**

- 如果它们没有共享，则线程安全
- 如果它们被共享了，根据它们的状态是否能够改变，又分两种情况
  - 如果只有读操作，则线程安全
  - 如果有读写操作，则这段代码是临界区，需要考虑线程安全

**局部变量是否线程安全？**

- 局部变量是线程安全的
- 但局部变量引用的对象则未必
  - 如果该对象没有逃离方法的作用访问，它是线程安全的
  - 如果该对象逃离方法的作用范围，需要考虑线程安全

### 常见线程安全类

- String
- Integer
- StringBuffer
- Random·Vector
- Hashtable
- java.util.concurrent包下的类

这里说它们是线程安全的是指，多个线程调用它们同一个实例的某个方法时，是线程安全的。也可以理解为

- 它们的每个方法是原子的
- 但注意它们多个方法的组合不是原子的，见后面分析

如果将两个方法聚合在一起用，会出现线程安全问题，

### 不可变类线程安全性

Sting、Integer等都是不可变类，因为其内部的状态不可以改变，因此它们的方法都是线程安全的

String有 replace，substring等方法【可以】改变值啊，那么这些方法又是如何保证线程安全的呢？

​	方法底层都是复制了一个字符串对象返回





## Monitor

### java对象头

32位虚拟机

**普通对象**

![image-20220110103909381](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220110103909381.png)

**数组对象**

![image-20220110103948628](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220110103948628.png)

**其中Mark Word结构为**

![image-20220110104026361](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220110104026361.png)



### Monitor（锁）

**Monitor 被翻译为监视器或管程**
		每个Java对象都可以关联一个Monitor对象，如果使用synchronized给对象上锁（重量级）之后，该对象头的Mark Word中就被设置指向 Monitor对象的指针		Monitor 结构如下

![image-20220110104413846](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220110104413846.png)

**官方描述：**

- 刚开始Monitor中Owner为null
- 当Thread-2执行synchronized（obj）就会将Monitor 的所有者Owner 置为Thread-2，Monitor中只能有一个Owner
- 在Thread-2上锁的过程中，如果Thread-3，Thread-4，Thread-5也来执行synchronized（obj），就会进入EntryList BLOCKED
- Thread-2执行完同步代码块的内容，然后唤醒EntryList中等待的线程来竞争锁，竞争的时是非公平的
- 图中WaitSet中的Thread-0，Thread-1是之前获得过锁，但条件不满足进入WAITING状态的线程，后面讲wait-notify时会分析

**注意**：

- synchronized必须是进入同一个对象的monitor才有上述的效果
- 不加synchronized的对象不会关联监视器，不遵从以上规则

![image-20220110105013673](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220110105013673.png)

过程：

- 线程进入加锁区域，尝试将obj（java提供的）对象和Monitor（操作系统提供的）对象相关联，
- 如何关联
  - 在obj的markword中记录了Monitor对象的地址，具体情况：：记录在下图，并将标志位由01变为10，将前置的30用来存储Monitor地址
  - 然后此线程成为此锁对象的Owner
- 又进来了一个线程，进入到加锁区域，他会去先检查这个锁有没有主人，也是Owner,这个线程被加入到锁对象的EntryList（底层链表结构）中，也叫阻塞队列或者等待队列，线程进入BLOCKED状态，等待其他线程释放Owner所有权，才会竞争执行。

![image-20220110105211222](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220110105211222.png)

- 又进来一个线程，进入加锁区域，他会去先检查这个锁有没有主人，也是Owner，和上面一样
- 当持有Owner的线程执行完成之后，Owner空出来了，会通知等待队列 EntryList，唤醒等待的线程，让等待的线程去竞争Owner的所有权（非公平的），抢到的线程状态变为Runnable,运行下去。其他的线程任在EntryList中等待，



字节码指令Monitor分析

![image-20220110111339884](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220110111339884.png)

对应字节码

![image-20220110113416711](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220110113416711.png)

### 轻量级锁

轻量级锁的使用场景：如果一个对象虽然有多线程访问，但多线程访问的时间是错开的（也就是没有竞争），那么可以使用轻量级锁来优化。

轻量级锁对使用者是透明的，即语法仍然是synchronized

假设有两个方法同步块，利用同一个对象加锁

**![image-20220110114359658](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220110114359658.png)

- 创建锁记录（Lock Record）对象，每个线程都的栈帧都会包含一个锁记录的结构，内部可以存储锁定对象的Mark Word

- ![image-20220110114505902](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220110114505902.png)

- 让锁记录中Object reference 指向锁对象，并尝试用cas 替换Object的Mark Word，将Mark Word的值存入锁记录

- ![image-20220110114611264](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220110114611264.png)

- 如果cas替换成功，对象头中存储了锁记录地址和状态ee，表示由该线程给对象加锁，这时图示如下

- ![image-20220110114738293](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220110114738293.png)

- 如果cas失败，有两种情况

  - 如果是其它线程已经持有了该Object的轻量级锁，这时表明有竞争，进入锁膨胀过程
  - 如果是自己执行了synchronized锁重入，那么再添加一条Lock Record作为重入的计数

  ![image-20220110114926156](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220110114926156.png)

- ·当退出synchronized代码块（解锁时）如果有取值为null的锁记录，表示有重入，这时重置锁记录，表示重入计数减一

  ![image-20220110115337550](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220110115337550.png)

- 当退出synchronized代码块（解锁时）锁记录的值不为null，这时使用cas将Mark Word的值恢复给对象头

  - 成功，则解锁成功
  - 失败，说明轻量级锁进行了锁膨胀或已经升级为重量级锁，进入重量级锁解锁流程

### 锁膨胀

如果在尝试加轻量级锁的过程中，CAS操作无法成功，这时一种情况就是有其它线程为此对象加上了轻量级锁（有竞争），这时需要进行锁膨胀，将轻量级锁变为重量级锁。

![image-20220110120044621](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220110120044621.png)

- 当Thread-1进行轻量级加锁时，Thread-0已经对该对象加了轻量级锁

  ![image-20220110120143836](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220110120143836.png)

  
  
  
  
- 这时Thread-1加轻量级锁失败，进入锁膨胀流程

  - 即为Object对象申请Monitor锁，让Object 指向重量级锁地址
  - 然后自己进入Monitor的EntryList BLOCKED

  ![](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220110121550534.png)

- 当Thread-0退出同步块解锁时，使用cas 将Mark Word的值恢复给对象头，失败。这时会进入重量级解锁流程，即按照Monitor 地址找到Monitor对象，设置Owner为null，唤醒EntryList中BLOCKED线程

### 自旋优化

重量级锁竞争的时候，还可以使用自旋来进行优化，如果当前线程自旋成功（即这时候持锁线程已经退出了同步块，释放了锁），这时当前线程就可以避免阻塞。
自旋重试成功的情况

![image-20220110124556411](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220110124556411.png)

![image-20220110124839902](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220110124839902.png)

- 在Java6之后自旋锁是自适应的，比如对象刚刚的一次自旋操作成功过，那么认为这次自旋成功的可能性会高，就多自旋几次；反之，就少自旋甚至不自旋，总之，比较智能。
- 自旋会占用CPU时间，单核CPU自旋就是浪费，多核CPU自旋才能发挥优势。
- Java7之后不能控制是否开启自旋功能

### 偏向锁

轻量级锁在没有竞争时（就自己这个线程），每次重入仍然需要执行CAS操作。
		Java6中引入了偏向锁来做进一步优化：只有第一次使用CAS将线程ID设置到对象的Mark Word头，之后发现这个线程ID是自己的就表示没有竞争，不用重新CAS。以后只要不发生竞争，这个对象就归该线程所

![image-20220110125715376](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220110125715376.png)

![image-20220110125738495](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220110125738495.png)

![image-20220110130615412](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220110130615412.png)

#### 偏向状态

对象头格式

![image-20220110190027824](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220110190027824.png)



​					正常状态  hashcode值，									分代年龄，是否启用偏向锁 0未启用  1启用    01是正常      Normal 正常

![image-20220110190126129](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220110190126129.png)

线程id						批量重偏向和批量撤销时用到

![image-20220110190405380](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220110190405380.png)

一个对象创建时：

- 如果开启了偏向锁（默认开启），那么对象创建后，markword值为0x05即最后3位为101，这时它的thread、epoch、age都为0
- 偏向锁是默认是延迟的，不会在程序启动时立即生效，如果想避免延迟，可以加VM参数-XX:BiasedLockingstartupoelay=0来禁用延迟
- 如果没有开启偏向锁，那么对象创建后，markword值为0x01即最后3位为001，这时它的hashcode、age都为0，第一次用到hashcode时才会赋值

![image-20220110192016982](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220110192016982.png)

**测试禁用**
在上面测试代码运行时在添加VM参数-XX:-useBiasedLocking禁用偏向锁

**还有一种情况在对象调用hashcode()方法时，会禁用偏向锁**，对象头中无法储存住hashcode值，因为thread要占对象头中的54位;



![image-20220110192252306](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220110192252306.png)



#### 撤销-其他线程使用对象

当有其它线程使用偏向锁对象时，会将偏向锁升级为轻量级锁



#### 撤销-调用对象hashcode

调用了对象的hashCode，但偏向锁的对象MarkWord中存储的是线程id，如果调用hashCode会导致偏向锁被撤销

- 轻量级锁会在锁记录中记录hashCode
- 重量级锁会在Monitor中记录 hashCode

在调用hashCode后使用偏向锁，记得去掉-xx:-UseBiasedLocking

输出

![image-20220110193129999](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220110193129999.png)

#### 撤销-调用wait/notify

调用这个方法时，会将任何锁升级为重量级锁

#### 批量重偏向

如果对象虽然被多个线程访问，但没有竞争，这时偏向了线程T1的对象仍有机会重新偏向T2，重偏向会重置对象的Thread ID

当撤销偏向锁阈值超过20次后，jvm会这样觉得，我是不是偏向错了呢，于是会在给这些对象加锁时重新偏向至加锁线程

#### 批量撤销

当撤销偏向锁阈值超过40次后，jvm会这样觉得，自己确实偏向错了，根本就不该偏向。于是整个类的所有对象都会变为不可偏向的，新建的对象也是不可偏向的

#### 锁消除

![image-20220110200233883](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220110200233883.png)

**禁止锁消除**

![image-20220110200615124](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220110200615124.png)



### wait notify

#### 原理之wait  notify原理

![image-20220110201026282](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220110201026282.png)

- Owner 线程发现条件不满足，调用wait方法，即可进入WaitSet变为WAITING状态
- BLOCKED和WAITING的线程都处于阻塞状态，不占用CPU时间片
- BLOCKED线程会在Owner 线程释放锁时唤醒
- WAITING 线程会在Owner 线程调用notify或notifyAll时唤醒，但唤醒后并不意味者立刻获得锁，仍需进入EntryList 重新竞争

#### Api介绍

- obj.wait（）让进入object 监视器的线程到waitSet等待
- obj.notify（）在object 上正在waitSet等待的线程中挑一个唤醒
- obj.notifyA11（）让object 上正在waitSet等待的线程全部唤醒

它们都是线程之间进行协作的手段，都属于Object对象的方法。必须获得此对象的锁，才能调用这几个方法

#### 使用wait(),notify()

开始之前先看看
sleep（1ong n）和wait（1ong n）的区别

1）sleep是Thread方法，而 wait是Object的方法

2）sleep不需要强制和synchronized配合使用，但wait 需要和synchronized一起用

3）sleep在睡眠的同时，不会释放对象锁的，但wait在等待的时候会释放对象锁。

**虚假唤醒**

错误的唤醒一个线程，使用notifyAll();但出现都唤醒，出现资源浪费的问题，解决：使用while 条件判断

### （同步模式）保护性暂停模式

即Guarded Suspension，用在一个线程等待另一个线程的执行结果

要点

- 有一个结果需要从一个线程传递到另一个线程，让他们关联同一个GuardedObject
- 如果有结果不断从一个线程到另一个线程那么可以使用消息队列（见生产者/消费者）
- JDK中，join的实现、Future的实现，采用的就是此模式
- 因为要等待另一方的结果，因此归类到同步模式

![image-20220111104817826](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220111104817826.png)

```java
package com.wudidemiao.test;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j(topic = "c.SynchronousMode")
public class SynchronousMode {
    public static void main(String[] args) {
        GuardeObject guardeObject = new GuardeObject();
        new Thread(() -> {
            log.debug("等待结果...");
            List<String> response = (List<String>) guardeObject.getResponse(1000);
            log.debug("结果大小::" + response.size());
        }, "t1").start();

        new Thread(() -> {
            try {
                log.debug("传送结果...");
                List<String> download = GuardeObject.download();
                guardeObject.complete(download);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "t2").start();
    }
}

class GuardeObject {
    //     结果
    private Object response;

    //    获取结果
    public Object getResponse(long timeOut) {
        synchronized (this) {
//            开始点 
            long begin = System.currentTimeMillis();
//            经历的时间
            long passedTime = 0;
            while (Objects.isNull(response)) {
                long waitTime = timeOut - passedTime;
//                时间超过了等待时间
                if (waitTime <= 0) {
                    return new ArrayList<String>();
                }
                try {
                    this.wait(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                求得经历时间
                passedTime = System.currentTimeMillis() - begin;
            }

            return response;
        }
    }

    public void complete(Object response) {
        synchronized (this) {
//            给成员变量赋值
            this.response = response;
            this.notifyAll();
        }
    }


    public static List<String> download() throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL("https://www.baidu.com").openConnection();
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

}

```

### join原理

參照上面的代碼，在等待時間為0的情况下，长时间去等待，小于0报出参数异常，大于0和上面的参数类似

### 扩展

图中Futures就好比居民楼一层的信箱（每个信箱有房间编号），左侧的t0，t2，t4就好比等待邮件的居民，右侧的tl，t3，t5就好比邮递员
		如果需要在多个类之间使用GuardedObject对象，作为参数传递不是很方便，因此设计一个用来解耦的中间类，这样不仅能够解耦【结果等待者】和【结果生产者】，还能够同时支持多个任务的管理



![image-20220111113251594](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220111113251594.png)

```java
package com.wudidemiao.test;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j(topic = "c.SynchronousMode")
public class SynchronousMode {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            new People().start();
        }

        Thread.sleep(2000);

        for (Integer id : Mailboxes.getIds()) {
            new Postman(id,"内容"+id).start();
        }
        
    }
}

class Mailboxes {
    private static Map<Integer, GuardeObject> boxes = new Hashtable<>();

    private static int id = 1;

    public static synchronized int generateId() {
        return id++;
    }

    public static GuardeObject createGuardeObject() {
        GuardeObject guardeObject = new GuardeObject(generateId());
        boxes.put(guardeObject.getId(), guardeObject);
        return guardeObject;
    }

    public static Set<Integer> getIds() {
        return boxes.keySet();
    }

    public static GuardeObject getGuardeObject(int id) {
        return boxes.remove(id);
    }
}

@Slf4j(topic = "c.People")
class People extends Thread {
    @Override
    public void run() {
        //        收信
        GuardeObject guardeObject = Mailboxes.createGuardeObject();
//        id
        log.debug("收信id::" + guardeObject.getId());
        Object response = guardeObject.getResponse(5000);
        log.debug("收信id:{},内容 {}", guardeObject.getId(), response);
    }
}

@Slf4j(topic = "c.Postman")
class Postman extends Thread {
    private int id;
    private String mail;

    public Postman(int id, String mail) {
        this.id = id;
        this.mail = mail;
    }

    @SneakyThrows
    @Override
    public void run() {
        GuardeObject guardeObject = Mailboxes.getGuardeObject(id);
        guardeObject.complete(mail);
        log.debug("发送完毕。。id:{},内容{}", id, mail);
    }
}

class GuardeObject {
    private int id;

    public GuardeObject(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    //     结果
    private Object response;

    //    获取结果
    public Object getResponse(long timeOut) {
        synchronized (this) {
//            开始点 
            long begin = System.currentTimeMillis();
//            经历的时间
            long passedTime = 0;
            while (Objects.isNull(response)) {
                long waitTime = timeOut - passedTime;
//                时间超过了等待时间
                if (waitTime <= 0) {
                    return new ArrayList<String>();
                }
                try {
                    this.wait(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                求得经历时间
                passedTime = System.currentTimeMillis() - begin;
            }

            return response;
        }
    }

    public void complete(Object response) {
        synchronized (this) {
//            给成员变量赋值
            this.response = response;
            this.notifyAll();
        }
    }


    public static List<String> download() throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL("https://www.baidu.com").openConnection();
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

}

```

### 异步模式之生产者/消费者

要点

- 与前面的保护性暂停中的GuardObject不同，不需要产生结果和消费结果的线程——对应
- 消费队列可以用来平衡生产和消费的线程资源I
- 生产者仅负责产生结果数据，不关心数据该如何处理，而消费者专心处理结果数据
- 消息队列是有容量限制的，满时不会再加入数据，空时不会再消耗数据
- JDK中各种阻塞队列，采用的就是这种模式

![image-20220111120917747](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220111120917747.png)

```java
package com.wudidemiao.test;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Objects;

import static java.lang.Thread.sleep;

@Slf4j(topic = "c.AsynchronousModeTest4")
public class AsynchronousModeTest4 {
    public static void main(String[] args) {
        MessageQueue messageQueue = new MessageQueue(2);
        for (int i = 0; i < 100; i++) {
            int id = i;
            new Thread(() -> {
                messageQueue.put(new Message(id, "结果" + id));
            }, "生产者" + i).start();
        }

        new Thread(() -> {
         while (true){
             try {
                 sleep(1);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
             Message message = messageQueue.getMessage();
             log.debug("收到了"+message.toString());
         }
        }, "消费者").start();
    }
}

//消息队列类
@Slf4j(topic = "c.MessageQueue")
class MessageQueue {
    private LinkedList<Message> list = new LinkedList<>();

    //    队列容量
    private int capcity;

    public MessageQueue(int capcity) {
        this.capcity = capcity;
    }

    //    获取消息
    public Message getMessage() {
        synchronized (list) {
//        检查队列是否为空
            while (list.isEmpty()) {
                try {
                    log.debug("等待中...");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Message message = list.removeFirst();
            list.notifyAll();
            return message;
        }

    }

    public void put(Message message) {
        synchronized (list) {
            if (list.size() == capcity) {
                try {
                    log.debug("容量满，等待泄洪中...");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("存储中。。。");
            list.addLast(message);
            list.notifyAll();
        }
    }
}

final class Message {
    private int id;
    private Object value;

    public Message(int id, Object value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}

```





## park&Unpark

**基本使用**

它们是LockSupport类中的方法

```java
//暂停当前线程
LockSupport.park（）；
//恢复某个线程的运行
Locksupport.unpark（暂停线程对象）
```

先park再unpark

```java
Thread t1=new Thread(()->{
1og. debug("start..."); sleep(1);I
1og. debug("park..."); LockSupport. park();
1og. debug("resume..");
},"t1"); 
t1. start();
sleep(2);
1og. debug("unpark..…");
LockSupport. unpark(t1);
```

unpark可以在park之前调用也可以在park之后调用，在之前调用会使park失效，需要重新设置标志位



**特点**

与Object的wait&notify相比

- wait，notify 和notifyAll必须配合Object Monitor一起使用，而unpark不必
- park&unpark是以线程为单位来【阻塞】和【唤醒】线程，而notify只能随机唤醒一个等待线程，notifyAll 是唤醒所有等待线程，就不那么【精确】
- park&unpark 可以先unpark，而 wait&notify 不能先notify



### park&unpark原理

每个线程都有自己的一个Parker对象，由三部分组成_counter，_cond和_mutex打个比喻

- 线程就像一个旅人，Parker就像他随身携带的背包，条件变量就好比背包中的帐篷。_counter就好比背包中的备用干粮（0为耗尽，1为充足）
- 调用park就是要看需不需要停下来歇息
  - 如果备用干粮耗尽，那么钻进帐篷歇息
  - 如果备用干粮充足，那么不需停留，继续前进
- 调用unpark，就好比令干粮充足
  - 如果这时线程还在帐篷，就唤醒让他继续前进
  - 如果这时线程还在运行，那么下次他调用park时，仅是消耗掉备用干粮，不需停留继续前进
    - 因为背包空间有限，多次调用unpark仅会补充一份备用干粮

![image-20220111125144508](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220111125144508.png)

1. 当前线程调用Unsafe.park0方法

2. 检查_counter，本情况为0，这时，获得_mutex 互斥锁
3. 线程进入_cond条件变量阻塞_
4. 设置_counter=0

![image-20220111125605448](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220111125605448.png)

1. 调用Unsafe.unpark（Thread_0）方法，设置_counter为1_
2. _当前线程调用Unsafe.park0方法
3. _检查_counter，本情况为1，这时线程无需阻塞，继续运行
4. 设置_counter为0

## 线程状态转换

![image-20220111125756443](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220111125756443.png)

假设有线程 Thread t

**情况1NEW-->RUNNABLE**

- 当调用t.start（）方法时，由NEW-->RUNNABLE

**情况2RUNNABLE<-->WAITING** 

t线程用synchronized（obj）获取了对象锁后

- 调用obj.wait（）方法时，t线程从RUNNABLE-->WATTING
- 调用obj.notify（），obj.notifyAl1（），t.interrupt（）时
  - 竞争锁成功，t线程从WAITING-->RUNNABLE
  - 竞争锁失败，t线程从WAITING-->BLOCKED

**情况3RUNNABLE<-->WAITING**

- 当前线程调用t.join（）方法时，当前线程从RUNABLE-->WAITING
  - 注意是当前线程在t线程对象的监视器上等待
- t线程运行结束，或调用了当前线程的interrupt（）时，当前线程从WAITING-->RUNNABLE

**情况 4RUNNABLE<-->WAITING**

- 当前线程调用LockSupport.park（）方法会让当前线程从RUNNABLE-->WAITING
- 调用Locksupport.unpark（目标线程）或调用了线程的interrupt（），会让目标线程从WAITING-->
  RUNNABLE

**情况5RUNNABLE<-->TIMED_WAITING**

t线程用synchronized（obj）获取了对象锁后

- 调用obj.wait（1ongn）方法时，t线程从RUNNABLE-->TIMED_WAITING
- t线程等待时间超过了n毫秒，或调用obj.notify（），obj.notifyA11（），t.interrupt（）时
  - 竞争锁成功，t线程从TIMED_WAITING-->RUNNABLE
  - 竞争锁失败，t线程从TIMED_WAITING-->BLOCKED

**情况6RUNNABLE<-->TIMED_WAITING**

- 当前线程调用t.join（1ong n）方法时，当前线程从RUNNABLE-->TIMED_WATTING
  - 注意是当前线程在t线程对象的监视器上等待
- 当前线程等待时间超过了n毫秒，或t线程运行结束，或调用了当前线程的interrupt（）时，当前线程从TIMED_WAITING-->RUNNABLE

**情况7RUNNABLE<-->TIMED_WAITING**

- 当前线程调用Thread.sleep（1ongn），当前线程从RUNNABLE-->TIMED_WAITING
- 当前线程等待时间超过了n毫秒，当前线程从TIMED_WATTING-->RUNNABLE

**情况8RUNNABLE<-->TIMED_WAITING**

- 当前线程调用LockSupport.parkNanos（1ong nanos）或LockSupport.parkUnti1（1ong mil1is）时，当前线程从RUNNABLE-->TIMED_WAITING
- 调用Locksupport.unpark（目标线程）或调用了线程的interrupt（），或是等待超时，会让目标线程从TIMED_WAITING-->RUNNABLE

**情况9RUNNABLE<-->BLOCKED**

- t线程用synchronized（obj）获取了对象锁时如果竞争失败，从RUNNABLE-->BLOCKED
- 持obj锁线程的同步代码块执行完毕，会唤醒该对象上所有BLOCKED的线程重新竞争，如果其中t线程竞争成功，从BLOCKED-->RUNNABLE，其它失败的线程仍然BLOCKED

**情况10RUNNABLE<-->TERMINATED**

当前线程所有代码运行完毕，进入TERMINATED

## 多把锁

通过添加多把锁来增加并发性，

将锁的粒度细分

- 好处，是可以增强并发度
- 坏处，如果一个线程需要同时获得多把锁，就容易发生死锁，

## 活跃性

### 死锁

有这样的情况：一个线程需要同时获取多把锁，这时就容易发生死锁

t1线程获得A对象锁，接下来想获取B对象的锁

t2线程获得B对象锁，接下来想获取A对象的锁

避免死锁的方式：**顺序加锁**

### 定位死锁

- 检测死锁可以使用jconsole工具，或者使用jps定位进程id，再用jstack定位死锁：

![image-20220111191336674](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220111191336674.png)

![image-20220111191548876](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220111191548876.png)

**Jconsole**

![image-20220111192917578](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220111192917578.png)

### 活锁

活锁出现在两个线程互相改变对方的结束条件，最后谁也无法结束，例如

![image-20220111192331243](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220111192331243.png)

解决方案：指令时间和睡眠时间有一点的交错。

### 饥饿

很多教程中把饥饿定义为，一个线程由于优先级太低，始终得不到CPU调度执行，也不能够结束，饥饿的情况不易演示，讲读写锁时会涉及饥饿问题
	下面我讲一下我遇到的一个线程饥饿的例子，先来看看使用顺序加锁的方式解决之前的死锁问题

![image-20220111192557384](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220111192557384.png)

![image-20220111192608446](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220111192608446.png)





**顺序加锁容易产生饥饿问题**

推荐使用**ReentrantLock**解决

## ReentrantLock



相对于synchronized它具备如下特点

- 可中断
- 可以设置超时时间
- 可以设置为公平锁
- 支持多个条件变量（多个waitSet）

与synchronized一样，都支持可重入

基本语法：

![image-20220111193536052](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220111193536052.png)

### 可重入

可重入是指同一个线程如果首次获得了这把锁，那么因为它是这把锁的拥有者，因此有权利再次获取这把锁如果是不可重入锁，那么第二次获得锁时，自己也会被锁挡住

```java
package com.wudidemiao.test;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest5 {
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        lock.lock();
        try {
            m1();
        } finally {
            lock.unlock();
        }
    }

    public static void m1(){
        lock.lock();
        try {
            m3();
        } finally {
            lock.unlock();
        }
    }

    public static void m3(){
        lock.lock();
        try {

        } finally {
            lock.unlock();
        }
    }
}

```

### 可打断

ReentranctLock.lock()  无法被打断方法所打断，但是ReentranctLock.lockInterruptibly()  可以被打断方法所打断

### 锁超时

立即失败

尝试获取一段时间后，则会放弃等待，总之，不会死等。

```java
        new Thread(()->{
//            if (!lock.tryLock()) {
//                return;
//            }

            try {
//                tryLock也支持可打断特性
                if (!lock.tryLock(1, TimeUnit.SECONDS)) {
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }

            try {

            }finally {
                lock.unlock();
            }
        },"t1").start();
```

### 公平锁

ReentrantLock默认是不公平的，本意是解决饥饿问题

可以通过构造方法来设置公平与否

![image-20220111195821783](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220111195821783.png)

一般没有必要，会降低并发度，

### 条件变量

synchronized中也有条件变量，就是我们讲原理时那个waitSet休息室，当条件不满足时进入waitSet等待

ReentrantLock的条件变量比synchronized强大之处在于，它是支持多个条件变量的，这就好比

- synchronized是那些不满足条件的线程都在一间休息室等消息
- 而ReentrantLock支持多间休息室，有专门等烟的休息室、专门等早餐的休息室、唤醒时也是按休息室来唤醒
  使用流程
- await 前需要获得锁
- await 执行后，会释放锁，进入conditionObject等待
- await的线程被唤醒（或打断、或超时）取重新竞争lock锁
- 竞争lock锁成功后，从await后继续执行

```java
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
//        创建一个新的条件变量
         Condition condition1 = lock.newCondition();
         Condition condition2 = lock.newCondition();

         lock.lock();
        try {
//            进入某个休息是等待
            condition1.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
//        叫醒一个休息的线程
        condition1.signal();
//        全部叫醒
        condition1.signalAll();
    }
```

## 同步模式之顺序控制

```java
    static final Object lock = new Object();

    static boolean t2Runned = false;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                while (!t2Runned) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("1");
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                log.debug("2");
                t2Runned = true;
                lock.notify();
            }
        }, "t2");
        t1.start();
        t2.start();
    }
```

# 共享模型之内存（JMM）

共享变量在多线程间的【可见性】问题与多条指令执行时的【有序性】问题

## java内存模型



JMM即Java Memory Model，它定义了主有工作内存抽象概念，底层对应着CPU寄存器、缓存、硬件内存、CPU指令优化等。

JMM体现在以下几个方面

- 原子性-保证指令不会受到线程上下文切换的影响
- 可见性-保证指令不会受cpu缓存的影响
- 有序性-保证指令不会受cpu 指令并行优化的影响

## 可见性

### 退不出的循环

先来看一个现象，main线程对run变量的修改对于t线程不可见，导致了t线程无法停止：

![image-20220112122131435](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220112122131435.png)

为什么呢？分析一下：

1. 初始状态，t线程刚开始从主内存读取了run的值到工作内存。

![image-20220112122302979](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220112122302979.png)

2. 因为t线程要频繁从主内存中读取run的值，IT编译器会将run的值缓存至自己工作内存中的高速缓存中，减少对主存中run的访问，提高效率

![image-20220112122452845](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220112122452845.png)

3. 1秒之后，main线程修改了run的值，并同步至主存，而t是从自己工作内存中的高速缓存中读取这个变量的值，结果永远是旧值

![image-20220112122606115](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220112122606115.png)

**解决方法**：

1.给共享的变量加上volatile 关键字 意思为不能从缓存中读取数据，到主内存中读取最新值

volatile（易变关键字）

它可以用来修饰成员变量和静态成员变量，他可以避免线程从自己的工作缓存中查找变量的值，必须到主存中获取它的值，线程操作volatile 变量都是直接操作主存

2. 在synchronized 同步代码块中也可以解决共享变量修改问题

## 可见性和原子性区别

前面例子体现的实际就是可见性，它保证的是在多个线程之间，一个线程对volatile变量的修改对另一个线程可见，不能保证原子性，仅用在一个写线程，多个读线程的情况：

上例从字节码理解是这样的：

![image-20220112123538795](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220112123538795.png)

比较一下之前我们将线程安全时举的例子：两个线程一个i+一个i-，只能保证看到最新值，不能解决指令

注意
			synchronized 语句块既可以保证代码块的原子性，也同时保证代码块内变量的可见性。但缺点是synchronized是属于重量级操作，性能相对更低

​	如果在前面示例的死循环中加入System.ouf printin）会发现即使不加volatile修饰符，线程t也能正确看到对run变量的修改了，想一想为什么？

## 终止模式之两阶段终止模式

Two Phase Termination

在一个线程T1中如何优雅终止线程T2？这里的【优雅】指的是给T2一个料理后事的机会。

### 错误思路

- 使用线程对象的stop0方法停止线程
  - stop方法会真正杀死线程，如果这时线程锁住了共享资源，那么当它被杀死后就再也没有机会释放锁，其它线程将永远无法获取锁
- 使用System.exit（int）方法停止线程
  - 目的仅是停止一个线程，但这种做法会让整个程序都停止

正确思路：通过volatile关键字来修饰退出的关键字即可。



## 同步模式之Balking

Balking（犹豫）模式用在一个线程发现另一个线程或本线程已经做了某一件相同的事，那么本线程就无需再做了，直接结束返回



![image-20220112125346870](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220112125346870.png)

## 有序性

JVM会在不影响正确性的前提下，可以调整语句的执行顺序，思考下面一段代码

![image-20220112130345538](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220112130345538.png)

### 指令重排序优化

事实上，现代处理器会设计为一个时钟周期完成一条执行时间最长的CPU指令。为什么这么做呢？可以想到指令还可以再划分成一个个更小的阶段，例如，每条指令都可以分为：取指令-指令译码-执行指令-内存访问-数据写回这5个阶段

![image-20220112131211210](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220112131211210.png)

在不改变程序结果的前提下，这些指令的各个阶段可以通过重排序和组合来实现指令级并行，这一技术在80's中叶到90's中叶占据了计算架构的重要地位。
			提示：
				分阶段，分工是提升效率的关键！

![image-20220112131657819](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220112131657819.png)

### 支持流水线的处理器

现代CPU支持**多级指令流水线**，例如支持同时执行取指令-指令译码-执行指令-内存访问-数据写回的处理器，就可以称之为**五级指令流水线**。这时CPU可以在一个时钟周期内，同时运行五条指令的不同阶段（相当于一条执行时间最长的复杂指令），IPC=1，本质上，流水线技术并不能缩短单条指令的执行时间，但它变相地提高了指令地吞吐率。
		提示：
		奔腾四（Pentium 4）支持高达35级流水线，但由于功耗太高被废弃

![image-20220112131756492](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220112131756492.png)

### 问题

![image-20220112135841438](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220112135841438.png)

I_Result是一个对象，有一个属性rl用来保存结果，问，可能的结果有几种？
		有同学这么分析
		情况1：线程1先执行，这时ready=false，所以进入else分支结果为1

情况2：线程2先执行num=2，但没来得及执行ready=true，线程1执行，还是进入else分支，结果为1

情况3：线程2执行到ready=true，线程1执行，这回进入if分支，结果为4（因为num已经执行过了）

但我告诉你，结果还有可能是0，信不信吧！
		这种情况下是：线程2执行ready=true，切换到线程1，进入if分支，相加为0，再切回线程2执行num=2相信很多人已经晕了©@？



这种现象叫做指令重排，是JIT编译器在运行时的一些优化，这个现象需要通过大量测试才能复现：
				借助java 并发压测工具j[cstress](https://wikiopenjidk java.net/display/Code Tools/jcstress) 

```
mvn archetype:generate -DinteractiveMode=false -DarchetypeGroupId=org.openjdk.jcstress-
DarchetypeArtifactId=jcstress-java-test-archetype -DarchetypeVersion=0.5-
DgroupId=cn.itcast -DartifactId=ordering -Dversion=1.0
```

经验证会出现指令重排问题，会颠倒顺序，如下图。

![image-20220112175430370](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220112175430370.png)

## volatile

volatile的底层实现原理是内存屏障，Memory Barrier（Memory Fence）

- 对volatile变量的写指令后会加入写屏障
- 对volatile变量的读指令前会加入读屏障

### 如何保证可见性

- 写屏障（sfence）保证在该屏障之前的，对共享变量的改动，都同步到主存当中

- ```
  public void actor2（I_Result r）{
  	num=2；
  	ready=true；//ready是volatile赋值带写屏障
   	//写屏障
  }
  ```

- 而读屏障（lfence）保证在该屏障之后，对共享变量的读取，加载的是主存中最新数据

```java
public void actor1（I_Result r）{
//读屏障
//ready是volatile读取值带读屏障if（ready）{
r.r1=num+num；
}else{
r.r1=1；
}
```

![image-20220112180748628](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220112180748628.png)

### 保证有序性

- 写屏障会确保指令重排序时，不会将写屏障之前的代码排在写屏障之后

- ```java
  public void actor2（I_Resultr）{
  num=2；
  ready=true；//ready是volatile赋值带写屏障I
  /|
  }
  ```

- 读屏障会确保指令重排序时，不会将读屏障之后的代码排在读屏障之前

- ```java
  public void actorl（I_Result r）{
      // 读屏障
  //ready是volatile读取值带读屏障if（ready）{
  	r.r1=num+num；
  }else{
  	r.r1=1；
  }
  ```

  ![image-20220112181714305](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220112181714305.png)

还是那句话，不能解决指令交错：

- 写屏障仅仅是保证之后的读能够读到最新的结果，但不能保证读跑到它前面去
- 而有序性的保证也只是保证了本线程内相关代码不被重排序

![image-20220112181802771](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220112181802771.png)

### double-checked locking 问题

以著名的double-checked locking单例模式为例

![image-20220112182142600](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220112182142600.png)

以上的实现特点是：

- 懒惰实例化
- 首次使用getlnstance（）才使用synchronized加锁，后续使用时无需加锁
- 有隐含的，但很关键的一点：第一个if使用了INSTANCE变量，是在同步块之外

但在多线程环境下，上面的代码是有问题的，getlnstance方法对应的字节码为：

![image-20220112183011109](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220112183011109.png)

其中

- 17表示创建对象，将对象引用入栈//new Singleton
- 20表示复制一份对象引用//引用地址
- 21表示利用一个对象引用，调用构造方法/∥根据引用地址调用
- 24表示利用一个对象引用，赋值给 static INSTANCE

也许jvm会优化为：先执行24，再执行21。如果两个线程tl，t2按如下时间序列执行：

![image-20220112183428608](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220112183428608.png)

关键在于0：getstatic这行代码在monitor控制之外，它就像之前举例中不守规则的人，可以越过monitor读取INSTANCE变量的值

这时tl还未完全将构造方法执行完毕，如果在构造方法中要执行很多初始化操作，那么t2拿到的是将是一个未初始化完毕的单例

对INSTANCE使用volatile修饰即可，可以禁用指令重排，但要注意在JDK5以上的版本的volatile才会真正有效

**解决方案**

![image-20220112184029243](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220112184029243.png)

### happens-before 

happens-before 规定了对共享变量的写操作对其它线程的读操作可见，它是可见性与有序性的一套规则总结，抛开以下happens-before规则，JMM并不能保证一个线程对共享变量的写，对于其它线程对该共享变量的读可见

- 线程解锁m之前对变量的写，对于接下来对m加锁的其它线程对该变量的读可见

![image-20220112184533480](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220112184533480.png)

- 线程对volatile变量的写，对接下来其它线程对该变量的读可见

![image-20220112184649578](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220112184649578.png)

- 线程start 前对变量的写，对该线程开始后对该变量的读可见

![image-20220112184743439](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220112184743439.png)

- 线程结束前对变量的写，对其它线程得知它结束后的读可见（比如其它线程调用tl.isAlive）或tl.join0等待它结束）
- ![image-20220112184830271](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220112184830271.png)

- 线程tl打断t2（interrupt）前对变量的写，对于其他线程得知t2被打断后对变量的读可见（通过t2.interrupted 或t2.isInterrupted）

![image-20220112184907193](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220112184907193.png)

- 对变量默认值（0，false，null）的写，对其它线程对该变量的读可见
- 具有传递性，如果xhb->y并且yhb->z那么有xhb->z，配合volatile的防指令重排，有下面的例子

![image-20220112185035252](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220112185035252.png)





![image-20220112185527974](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220112185527974.png)

加入readResovle(),序列化时会返回创建的单例对象 

  ![image-20220112185558054](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220112185558054.png)

**小结**

![image-20220112191924332](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220112191924332.png)

# 共享模型之无锁

## CAS与volatile

### CAS

前面看到的AtomicInteger的解决方法，内部并没有用锁来保护共享变量的线程安全。那么它是如何实现的呢？

![image-20220113115440350](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220113115440350.png)

其中的关键是compareAndSet，它的简称就是CAS（也有Compare And Swap的说法），它必须是原子操作。

![image-20220113115641575](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220113115641575.png)

注意
其实CAS的底层是lock cmpxchg指令（X86架构），在单核CPU和多核CPU下都能够保证【比较交换】的原子性。

- 在多核状态下，某个核执行到带lock的指令时，CPU会让总线锁住，当这个核把此指令执行完毕，再开启总线。这个过程中不会被线程的调度机制所打断，保证了多个线程对内存操作的准确性，是原子的。

### volatile

获取共享变量时，为了保证该变量的可见性，需要使用volatile修饰。

它可以用来修饰成员变量和静态成员变量，他可以避免线程从自己的工作缓存中查找变量的值，必须到主存中获取它的值，线程操作volatile 变量都是直接操作主存。即一个线程对volatile 变量的修改，对另一个线程可见。

注意
volatile仅仅保证了共享变量的可见性，让其它线程能够看到最新值，但不能解决指令交错问题（不能保证原子性）

CAS必须借助volatile才能读取到共享变量的最新值来实现【比较并交换】的效果

### 为什么无锁效率高

- 无锁情况下，即使重试失败，线程始终在高速运行，没有停歇，而synchronized会让线程在没有获得锁的时候，发生上下文切换，进入阻塞。打个比喻
- 线程就好像高速跑道上的赛车，高速运行时，速度超快，一旦发生上下文切换，就好比赛车要减速、熄火，等被唤醒又得重新打火、启动、加速..恢复到高速运行，代价比较大
- 但无锁情况下，因为线程要保持运行，需要额外CPU的支持，CPU在这里就好比高速跑道，没有额外的跑道，线程想高速运行也无从谈起，虽然不会进入阻塞，但由于没有分到时间片，仍然会进入可运行状态，还是会导致上下文切换。

### CAS的特点

结合CAS和volatile可以实现无锁并发，适用于线程数少、多核CPU的场景下。

- CAS是基于乐观锁的思想：最乐观的估计，不怕别的线程来修改共享变量，就算改了也没关系，我吃亏点再重试呗。
- synchronized是基于悲观锁的思想：最悲观的估计，得防着其它线程来修改共享变量，我上了锁你们都别想改，我改完了解开锁，你们才有机会。
- CAS体现的是无锁并发、无阻塞并发，请仔细体会这两句话的意思
  - 因为没有使用 synchronized，所以线程不会陷入阻塞，这是效率提升的因素之一
  - 但如果竞争激烈，可以想到重试必然频繁发生，反而效率会受影响

## 原子整数

J.U.C并发包提供了：

- AtomicBoolean
- AtomicInteger
- AtomicLong

![image-20220113122859706](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220113122859706.png)

![image-20220113123405386](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220113123405386.png)

![image-20220113123927603](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220113123927603.png)

## 原子引用

为什么需要原子引用类型？

- AtomicReference
- AtomicMarkableReference
- AtomicStampedReference

![image-20220113124639427](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220113124639427.png)

### A-B-A问题

![image-20220113124950745](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220113124950745.png)

![image-20220113125009051](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220113125009051.png)

简述；其他线程将值由A->B再有其他线程改为B->A，而现在这个线程无法感知AtomicReference的共享值的变化，其实对业务造成不了影响，

主线程仅能判断出共享变量的值与最初值A是否相同，不能感知到这种从A改为B又改回A的清况，如果主线程希望：

只要有其它线程【动过了】共享变量，那么自己的cas就算失败，这时，仅比较值是不够的，需要再加一个版本号

![image-20220113125601374](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220113125601374.png)

AtomicStampedReference可以给原子引用加上版本号，追踪原子引用整个的变化过程，如：
		A->B->A->c，通过AtomicStampedReference，我们可以知道，引用变量中途被更改了几次。
		但是有时候，并不关心引用变量更改了几次，只是单纯的关心是否更改过，所以就有了AtomicMarkableReference

![image-20220113130743885](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220113130743885.png)

## 原子数组

- AtomicIntegerArray
- AtomicLongAray
- AtomicRefergnceArray

![image-20220113134125804](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220113134125804.png)

![image-20220113134145375](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220113134145375.png)



![image-20220113134359901](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220113134359901.png)



## 字段更新器

- AtomicReferenceFieldUpdater//域字段
- AtomicIntegerFieldUpdater
- AtomicLongFieldUpdater

利用字段更新器，可以针对对象的某个域（Field）进行原子操作，只能配合volatile修饰的字段使用，否则会出现异常

![image-20220113134546869](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220113134546869.png)

![image-20220113134917048](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220113134917048.png)

## 原子累加器

![image-20220113135502856](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220113135502856.png)



![image-20220113135844416](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220113135844416.png)



性能提升的原因很简单，就是在有竞争时，设置多个累加单元，Therad-0累加Cell[]，而Thread-1累加Cell[1]..最后将结果汇总。这样它们在累加时操作的不同的Cell变量，因此减少了CAS重试失败，从而提高性能。

### LongAdder

LongAdder是并发大师@author Doug Lea（大哥李）的作品，设计的非常精巧

LongAdder类有几个关键域

![image-20220113140333361](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220113140333361.png)

CAS锁

![image-20220113140656680](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220113140656680.png)



### 原理之伪共享

其中Cell即为累加单元

![image-20220113141318105](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220113141318105.png)

得从缓存说起

缓存与内存的速度比较

![image-20220113141429670](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220113141429670.png)

![image-20220113141458266](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220113141458266.png)

因为CPU与内存的速度差异很大，需要靠预读数据至缓存来提升效率。

而缓存以缓存行为单位，每个缓存行对应着一块内存，一般是64byte（8个long）

缓存的加入会造成数据副本的产生，即同一份数据会缓存在不同核心的缓存行中

CPU要保证数据的一致性，如果某个CPU核心更改了数据，其它CPU核心对应的整个缓存行必须失效



![image-20220113141837282](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220113141837282.png)

因为Cell是数组形式，在内存中是连续存储的，一个Cell为24字节（16字节的对象头和8字节的value），因此缓存行可以存下2个的Cell对象。这样问题来了：

- Core-0要修改Cell[o]
- Core-1要修改Cell[1]

无论谁修改成功，都会导致对方Core的缓存行失效，比如Core-0中ce1l[e]=6eee，Cell[1]=8eee要累加Ce11[e]=6ee1，Ce11[1]=8eee，这时会让Core-1的缓存行失效

@sun.misc.Contenfled用来解决这个问题，它的原理是在使用此注解的对象或字段的前后各增加128字节大小的padding，从而让CPU将对象预读至缓存时占用不同的缓存行，这样，不会造成对方缓存行的失效

![image-20220113142326887](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220113142326887.png)

### add方法源码解读

![image-20220113180947863](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220113180947863.png)

### longAccumulate(long x,longBinaryOperator,boolean wasUncountened);解读

![image-20220113181713545](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220113181713545.png)



## Unsafe

**概述**
		Unsafe对象提供了非常底层的，操作内存、线程的方法，Unsafe对象不能直接调用，只能通过反射获得

```
public class Test6 {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe unsafe = (Unsafe) theUnsafe.get(null);
        System.out.println(unsafe);

//      1. 获取属性的偏移地址
        long idOffect = unsafe.objectFieldOffset(Student.class.getDeclaredField("id"));
        long nameOffect = unsafe.objectFieldOffset(Student.class.getDeclaredField("name"));
        Student student = new Student();
//       2.执行cas操作
        unsafe.compareAndSwapInt(student, idOffect, 0, 1);
        unsafe.compareAndSwapObject(student, nameOffect, null, "张三");
        System.out.println(student);
    }
}

@Data
class Student {
    volatile int id;
    volatile String name;
}
```

# 共享模型之不可变

- 不可变类的使用
- 不可变类设计
- 无状态类设计

## 日期转换的问题

**问题提出**

下面的代码在运行时，由于SimpleDateFormat不是线程安全的

```
package com.wudidemiao.test;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

/**
 * @author wudidemiaoa
 * @date 2022/1/13
 * @apiNote
 */
@Slf4j(topic = "c.Test7")
public class Test7 {
    public static void main(String[] args) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                try {
                    log.debug("{}",dateTimeFormatter.parse("1951-04-21"));
                }catch (Exception e){
                    log.error("{}",e);
                }
            }).start();
        }
    }

    static void test(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                try {
                    log.debug("{}",simpleDateFormat.parse("1951-04-21"));
                }catch (Exception e){
                    log.error("{}",e);
                }
            }).start();
        }
    }
}

```

## 不可变类的设计

另一个大家更为熟悉的String类也是不可变的，以它为例，说明一下不可变设计的要素

![image-20220113193053109](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220113193053109.png)

**final的使用**

发现该类、类中所有属性都是final的

- 属性用final修饰保证了该属性是只读的，不能修改
- 类用final修饰保证了该类中的方法不能被覆盖，防止子类无意间破坏不可变性

**保护性拷贝**

但有同学会说，使用字符串时，也有一些跟修改相关的方法啊，比如substring等，那么下面就看一看这些方法是如何实现的，就以substring为例：

![image-20220113193500091](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220113193500091.png)

发现其内部是调用String的构造方法创建了一个新字符串，再进入这个构造看看，是否对final char]value做出了修改：

![image-20220113194236787](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220113194236787.png)

结果发现也没有，构造新字符串对象时，会生成新的char[]value，对内容进行复制。这种通过创建副本对象来避免共享的手段称之为【保护性拷贝（defensive copy）】

## 享元模式

### 简介

**定义**

英文名称：Flyweight pattern.当需要重用数量有限的同一类对象时

```
wikipedia：（最小化内存的使用，对已创建对象的共享）
A flyweight is an object that minimizes memory usage by sharing as much data as possible with other similar
objects
```

**出自**
		"Gang of Four"design patterns
		**归类**
		Structual patterns

### 体现

**包装类**
在JDK中Boolean，Byte，Short，Integer，Long，Character等包装类提供了valueOf方法，例如Long的valueOf会缓存-128~127之间的Long对象，在这个范围之间会重用对象，大于这个范围，才会新建Long对象：

```
    public static Long valueOf(long l) {
        final int offset = 128;
        if (l >= -128 && l <= 127) { // will cache
            return LongCache.cache[(int)l + offset];
        }
        return new Long(l);
    }
```

**注意：**

​	Byte，Short，Long 缓存的范围都是-128~127

​	Character缓存的范围是0~127

​	Integer的默认范围是-128~127，最小值不能变，但最大值可以通过调整虚拟机参数-

​	Djava.lang.Integer.IntegerCache.high来改变

​	Boolean 缓存了 TRUE和FALSE

### String串池

### BigDecimal BigInteger

**注意**这些类的单个方法是线程安全的，多个方法组合可能会出现线程安全问题。

### DIY数据库连接池

例如：一个线上商城应用，QPS达到数干，如果每次都重新创建和关闭数据库连接，性能会受到极大影响。这时预先创建好一批连接，放入连接池。一次请求到达后，从连接池获取连接，使用完毕后再还回连接池，这样既节约了连接的创建和关闭时间，也实现了连接的重用，不至于让庞大的连接数压垮数据库。

```java
public class Test8 {
    public static void main(String[] args) {
        Pool pool = new Pool(2);
        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                Connection borrow = pool.borrow();
                 try {
                     TimeUnit.SECONDS.sleep(1);
                 }catch (InterruptedException e){
                    e.printStackTrace();
                 }finally {
                     pool.free(borrow);
                 }
            }).start();
        }
    }
}

@Slf4j(topic = "c.Pool")
class Pool {
    //    1. 连接池大小
    private final int poolSize;

    //   2.连接对象的数组
    private Connection[] connections;

    //    3.连接状态数组，0 表示繁忙  1表示正常
    private AtomicIntegerArray states;

    //    构造
    public Pool(int poolSize) {
        this.poolSize = poolSize;
        this.connections = new Connection[poolSize];
        this.states = new AtomicIntegerArray(new int[poolSize]);
        for (int i = 0; i < poolSize; i++) {
            connections[i] = new MockConnection("连接池-"+i);
        }
    }

    //    取连接的方法
    public Connection borrow() {
        while (true) {
            for (int i = 0; i < poolSize; i++) {
//                获取空闲连接
                if (states.get(i) == 0) {
                    if (states.compareAndSet(i, 0, 1)) {
                        log.debug("获取{}", connections[i]);
                        return connections[i];
                    }
                }
            }

//            如果没有空闲连接了,当前线程进入等待
            synchronized (this) {
                try {
                    log.debug("等待。。。");
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //    归还连接
    public void free(Connection con) {
        for (int i = 0; i < poolSize; i++) {
            if (connections[i] == con) {
                states.set(i, 0);
                synchronized (this) {
                    log.debug("归还{}", con);
                    this.notifyAll();
                }
                break;
            }
        }
    }
}


class MockConnection implements Connection {
    private String name;

    public MockConnection(String name) {
        this.name = name;
    }
```

以上实现没有考虑：

- 连接的动态增长与收缩
- 连接保活（可用性检测）
- 等待超时处理
- 分布式hash

对于关系型数据库，有比较成熟的连接池实现，例如c3p0，druid等

对于更通用的对象池，可以考虑使用apache commons pool，例如redis连接池可以参考jedis中关于连接池的实现

## final原理

### 设置final变量的原理

理解了volatile原理，再对比final的实现就比较简单了

![image-20220113212200374](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220113212200374.png)

字节码

![image-20220113212216033](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220113212216033.png)

发现final变量的赋值也会通过putfield 指令来完成，同样在这条指令之后也会加入写屏障，保证在其它线程读到它的值时不会出现为0的情况

### 获取final变量的原理

加上final之后，是从常量池中复制元素到栈中，

去掉final之后，加上static之后，是从静态常量池中引用的，

### 无状态

在web阶段学习时，设计Servlet时为了保证其线程安全，都会有这样的建议，不要为Servlet设置成员变量，这种没有任何成员变量的类是线程安全的

因为成员变量保存的数据也可以称为状态信息，因此没有成员变量就称之为【无状态】



# 并发工具类

## 自定义线程池

![image-20220113215136638](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220113215136638.png)

#### 步奏一：自定义拒绝策略

```java
@FunctionalInterface
        // 拒绝策略
interface RejectPolicy<T> {
    void reject(BlockingQueue<T> queue, T task);
}
```

```java
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

```

## ThreadPoolExecutor

![image-20220114130018034](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220114130018034.png)

### 线程池状态

ThreadPoolExecutor使用int的高3位来表示线程池状态，低29位表示线程数量



![image-20220114130220179](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220114130220179.png)

从数字上比较，TERMINATED> TIDYING>STOP> SHUTDOWN> RUNNING

这些信息存储在一个原子变量ct中，目的是将线程池状态与线程个数合二为一，这样就可以用一次cas原子操作进行赋值

![image-20220114130723043](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220114130723043.png)

### 构造方法

```
    public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory,
                              RejectedExecutionHandler handler) {
```

- corePoolSize 核心线程数目（最多保留的线程数）
- maximumPoolSize 最大线程数目
- keepAlive Time 生存时间-针对救急线程
- unit时间单位-针对救急线程
- workQueue阻塞队列
- threadFactory 线程工厂-可以为线程创建时起个好名字
- handler 拒绝策略

工作方式

![image-20220114132727673](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220114132727673.png)

![image-20220114132117512](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220114132117512.png)44+4444444444444444444444

- 线程池中刚开始没有线程，当一个任务提交给线程池后，线程池会创建一个新线程来执行任务。
- 当线程数达到corePoolSize并没有线程空闲，这时再加入任务，新加的任务会被加入workQueue队列排队，直到有空闲的线程。
- 如果队列选择了有界队列，那么任务超过了队列大小时，会创建 maximumPoolSize-corePoolSize数目的线程来救急。
- 如果线程到达maximumPoolSize仍然有新任务这时会执行拒绝策略。拒绝策略jdk提供了4种实现，其它著名框架也提供了实现
  - AbortPolicy 让调用者抛出RejectedExecutionException 异常，这是默认策略
  - CallerRunsPolicy让调用者运行任务
  - DiscardPolicy 放弃本次任务
  - DiscardOldestPolicy放弃队列中最早的任务，本任务取而代之
  - Dubbo 的实现，在抛出RejectedExecutionException 异常之前会记录日志，并dump线程栈信息，方便定位问题
  - Netty的实现，是创建一个新线程来执行任务
  - ActiveMQ的实现，带超时等待（60s）尝试放久队列，类似我们之前自定义的拒绝策略
  - PinPoint的实现，它使用了一个拒绝策略链，会逐一尝试策略链中每种拒绝策略
- 当高峰过去后，超过corePoolSize的救急线程如果一段时间没有任务做，需要结束节省资源，这个时间由keepAlive Time 和unit 来控制。

![image-20220114133111805](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220114133111805.png)

根据这个构造方法，JDK  Executors类中提供了众多工厂方法来创建各种用途的线程池

### newFixedThreadPool

```java
    public static ExecutorService newFixedThreadPool(int nThreads) {
        return new ThreadPoolExecutor(nThreads, nThreads,
                                      0L, TimeUnit.MILLISECONDS,
                                      new LinkedBlockingQueue<Runnable>());
    }
```

特点

- 核心线程数=最大线程数（没有救急线程被创建），因此也无需超时时间

- 阻塞队列是无界的，可以放任意数量的任务

  评价
  适用于任务量已知，相对耗时的任务

定义线程名称，在线程工厂中

![image-20220114134758181](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220114134758181.png)

### newCachedThreadPool

```
    public static ExecutorService newCachedThreadPool() {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                      60L, TimeUnit.SECONDS,
                                      new SynchronousQueue<Runnable>());
    }
```

特点

- 核心线程数是0，最大线程数是Integer.MAX_VALUE，救急线程的空闲生存时间是60s，意味着

  - 全部都是救急线程（60s后可以回收）
  - 救急线程可以无限创建

- 队列采用了SynchronousQueue实现特点是，它没有容量，没有线程来取是放不进去的（一手交钱、一手交货）

  评价

  整个线程池表现为线程数会根据任务量不断增长，没有上限，当任务执行完毕，空闲1分钟后释放线

  适合任务数比较密集，但每个任务执行时间较短的情况

### newSingleThreadExecutor

```java
    public static ExecutorService newSingleThreadExecutor() {
        return new FinalizableDelegatedExecutorService
            (new ThreadPoolExecutor(1, 1,
                                    0L, TimeUnit.MILLISECONDS,
                                    new LinkedBlockingQueue<Runnable>()));
    }
```

使用场景
		希望多个任务排队执行。线程数固定为1，任务数多于1时，会放入无界队列排队。任务执行完毕，这唯一的线程也不会被释放。
区别：

- 自己创建一个单线程串行执行任务，如果任务执行失败而终止那么没有任何补救措施，而线程池还会新建一个线程，保证池的正常工作
- Executors.newSingle ThreadExecutor）线程个数始终为1，不能修改
  - FinalizableDelegatedExecutorService 应用的是装饰器模式，只对外暴露了ExecutorService接口，因此不能调用ThreadPoolExecutor中特有的方法
- Executors.newFixedThreadPool（1）初始时为1，以后还可以修改
  - 对外暴露的是ThreadPoolExecutor对象，可以强转后调用 setCorePoolSize 等方法进行修改

### 提交任务

![image-20220114140439435](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220114140439435.png)

### 关闭线程池

#### **shutdown**

![image-20220114191647833](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220114191647833.png)

![image-20220114191701897](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220114191701897.png)

#### shutdownNow

![image-20220114191821752](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220114191821752.png)

![image-20220114191837201](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220114191837201.png)

#### 其他方法

![image-20220114191938656](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220114191938656.png)

## 异步模式之工作模式

让有限的工作线程（Worker Thread）来轮流异步处理无限多的任务。也可以将其归类为分工模式，它的典型实现就是线程池，也体现了经典设计模式中的享元模式。

例如，海底捞的服务员（线程），轮流处理每位客人的点餐（任务），如果为每位客人都配一名专属的服务员，那么成本就太高了（对比另一种多线程设计模式：Thread-Per-Message）

注意，不同任务类型应该使用不同的线程池，这样能够避免饥饿，并能提升效率

例如，如果一个餐馆的工人既要招呼客人（任务类型A），又要到后厨做菜（任务类型B）显然效率不咋地，分成服务员（线程池A）与厨师（线程池B）更为合理，当然你能想到更细致的分工

### 饥饿现象

固定大小线程池会有饥饿现象I

- 两个工人是同一个线程池中的两个线程
- 他们要做的事情是：为客人点餐和到后厨做菜，这是两个阶段的工作
  - 客人点餐：必须先点完餐，等菜做好，上菜，在此期间处理点餐的工人必须等待
  - 后厨做菜：没啥说的，做就是了
- 比如工人A处理了点餐任务，接下来它要等着工人B把菜做好，然后上菜，他俩也配合的蛮好
- 但现在同时来了两个客人，这个时候工人A和工人B都去处理点餐了，这时没人做饭了，饥饿

### 创建多少线程池合适

- 过小会导致程序不能充分地利用系统资源、容易导致饥饿
- 过大会导致更多的线程上下文切换，占用更多内存

**CPU密集型运算**

通常采用cpu核数+1能够实现最优的CPU利用率，+1是保证当线程由于页缺失故障（操作系统）或其它原因导致暂停时，额外的这个线程就能顶上去，保证CPU时钟周期不被浪费
		**I/O密集型运算**
		CPU不总是处于繁忙状态，例如，当你执行业务计算时，这时候会使用CPU资源，但当你执行I0操作时、远程RPC调用时，包括进行数据库操作时，这时候CPU就闲下来了，你可以利用多线程提高它的利用率。

经验公式如下

线程数=核数*期望cPU利用率*总时间（cPU计算时间+等待时间）/CPU计算时间

例如4核CPU计算时间是50%，其它等待时间是50%，期望cpu被100%利用，套用公式

4* 100% * 10%/50%=8例如4核CPU计算时间是10%，其它等待时间是90%，期望cpu被100%利用，套用公式

4 * 180% * 180%/16%=40

### 任务调度线程池

在[任务调度线程池】功能加入之前，可以使用java.util.Timer来实现定时功能，Timer的优点在于简单易用，但由于所有任务都是由同一个线程来调度，因此所有任务都是串行执行的，同一时间只能有一个任务在执行，前一个任务的延迟或异常都将会影响到之后的任务。



```java
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
        // 处理异常
        // 主动加trycatch块区处理异常
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

```

![image-20220114210023810](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220114210023810.png)

### Tomcat线程池

Tomcat 在哪里用到了线程池呢

![image-20220115103014554](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115103014554.png)

- Limitatch 用来限流，可以控制最大连接个数，类似J.U.C中的Semaphore后面再讲
- Acceptor只负责【接收新的socket连接】
- Poller 只负责监听 socket channel是否有【可读的/O事件】
- 一旦可读，封装一个任务对象（socketProcessor），提交给Executor 线程池处理
- Executor线程池中的工作线程最终负责【处理请求】

Tomcat线程池扩展了ThreadPoolExecutor，行为稍有不同

- 如果总线程数达到maximumPoolSize
  - 这时不会立刻抛RejectedExecutionException异常
  - 而是再次尝试将任务放入队列(机会只有一次)，如果还失败，才抛出RejectedExecutionException异常

![image-20220115103635962](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115103635962.png)

![image-20220115103833993](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115103833993.png)

![image-20220115103914571](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115103914571.png)

![image-20220115103955445](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115103955445.png)

![image-20220115104848588](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115104848588.png)

### Fork/join

**1）概念**

Fork/Join是JDK1.7加入的新的线程池实现，它体现的是一种分治思想，适用于能够进行任务拆分的cpu密

所谓的任务拆分，是将一个大任务拆分为算法上相同的小任务，直至不能拆分可以直接求解。跟递归相关的一些计算，如归并排序、斐波那契数列、都可以用分治思想进行求解

Fork/Join在分治的基础上加入了多线程，可以把每个任务的分解和合并交给不同的线程来完成，进一步提升了运算效率

Fork/Join 默认会创建与cpu核心数大小相同的线程池

**2）使用**

提交给Fork/Join 线程池的任务需要继承 RecursiveTask（有返回值）或RecursiveAction（没有返回值），例如下面定义了一个对1~n之间的整数求和的任务

```java
package com.wudidemiao.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author wudidemiaoa
 * @date 2022/1/15
 * @apiNote
 */
@Slf4j(topic = "c.Test11")
public class Test11 {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool(4);
        Integer invoke = pool.invoke(new MyTask(5));
        System.out.println(invoke);
    }
}

// 无返回值
//class MyTask extends RecursiveAction {
//有返回值  1-n之间整数的和
class MyTask extends RecursiveTask<Integer> {

    private int n;

    public MyTask(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if (n == 1) {
            return 1;
        }
        MyTask myTask = new MyTask(n - 1);
        ForkJoinTask<Integer> fork = myTask.fork();
        return n + fork.join();
    }
}
```

可以拆分任务来优化此代码

## JUC(重点)

### AQS原理

全称是AbsfractQueuedSynchronizer，是阻塞式锁和相关的同步器工具的框架特点：

- 用state属性来表示资源的状态（分独占模式和共享模式），子类需要定义如何维护这个状态，控制如何获取锁和释放锁
  - getState-获取state状态
  - setState-设置state状态
  - compareAndSetState-乐观锁机制设置state状态
  - 独占模式是只有一个线程能够访问资源，而共享模式可以允许多个线程访问资源
- 提供了基于FIFO的等待队列，类似于Monitor的EntryList
- 条件变量来实现等待、唤醒机制，支持多个条件变量，类似于Monitor的WaitSet

子类主要实现这样一些方法（默认抛出UnsupportedOperationException）

- tryAcquire
- tryRelease
- tryAcquireshared
- tryReleaseShared
- isHeldExclusively

![image-20220115112636112](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115112636112.png)

```java
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

```

### ReentrantLock

![image-20220115114936608](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115114936608.png)

#### 非公平锁实现原理

**加锁解锁流程**

先从构造器开始看，默认为非公平锁实现

```java
    /**
     * Creates an instance of {@code ReentrantLock}.
     * This is equivalent to using {@code ReentrantLock(false)}.
     */
    public ReentrantLock() {
        sync = new NonfairSync();
    }
```

NonfairSync继承自AQS
		没有竞争时

![image-20220115115210442](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115115210442.png)

第一个竞争出现时

![image-20220115115519400](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115115519400.png)

Thread-1执行了

1. CAS尝试将state由0改为1，结果失败T
2. 进入tryAcquire逻辑，这时state已经是1，结果仍然失败
3. 接下来进入addWaiter逻辑，构造Node队列
   - 图中黄色三角表示该Node的waitStatus状态，其中0为默认正常状态
   - Node的创建是懒惰的
   - 其中第一个Node称为Dummy（哑元）或哨兵，用来占位，并不关联线程

![image-20220115115856120](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115115856120.png)

当前线程进入acquireQueued逻辑

1. acquireQueued 会在一个死循环中不断尝试获得锁，失败后进入park阻塞
2. 如果自己是紧邻着head（排第二位），那么再次tryAcquire尝试获取锁，当然这时state仍为1，失败
3. 进入shouldParkAfterFailedAcquire逻辑，将前驱node，即head的 waitStatus改为-1，这次返回false

![image-20220115120224681](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115120224681.png)

再次有多个线程经历上述过程竞争失败，变成这个样子



![image-20220115120513407](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115120513407.png)

Thread-0释放锁，进入tryRelease流程，如果成功

- 设置 exclusiveOwnerThread为null
-  astate=0

![image-20220115120600112](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115120600112.png)

当前队列不为nul，并且head的waitStatus=-1，进入unparkSuccessor流程

找到队列中离head最近的一个Node（没取消的），unpark恢复其运行，本例中即为Thread-1

回到Thread-1的 acquireQueued流程

![image-20220115121049524](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115121049524.png)

如果加锁成功（没有竞争），会设置

- exclusiveOwnerThread为Thread-1，state=1
- head 指向刚刚 Thread-1所在的Node，该Node 清空Thread
- 原本的head因为从链表断开，而可被垃圾回收

如果这时候有其它线程来竞争（非公平的体现），例如这时有Thread-4来了

![image-20220115121417473](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115121417473.png)

如果不巧又被Thread-4占了先

- Thread-4被设置为exclusiveOwnerThread，state=1
- Thread-1再次进入arquireQueued 流程，获取锁失败，重新进入park阻塞

#### 可重入原理

![image-20220115121850588](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115121850588.png)

![image-20220115121927174](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115121927174.png)

#### 可打断原理

##### 不可打断模式

在此模式下，即使它被打断，仍会驻留在AQS队列中，等获得锁后方能继续运行（是继续运行！只是打断标记被设置为true）

![image-20220115122612275](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115122612275.png)

![image-20220115122634920](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115122634920.png)

![image-20220115122955502](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115122955502.png)

##### 可打断模式

![image-20220115123042737](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115123042737.png)

![image-20220115123115726](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115123115726.png)

#### 非公平锁实现原理

![image-20220115123803007](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115123803007.png)

![image-20220115123824093](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115123824093.png)

#### 条件变量实现原理

每个条件变量其实就对应着一个等待队列，其实现类是ConditionObject 

##### await 流程

开始Thread-0持有锁，调用await，进入ConditionObject的 addConditionWaiter流程

创建新的Node状态为-2（Node.CONDITION），关联Thread-0，加入等待队列尾部

![image-20220115124156867](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115124156867.png)

接下来进入AQS的fullyRelease流程，释放同步器上的锁

![image-20220115124707613](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115124707613.png)

unparkAQS队列中的下一个节点，竞争锁，假设没有其他竞争线程，那么Thread-1竞争成功

![image-20220115124727633](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115124727633.png)

signal流程

假设Thread-1要来唤醒 Thread-0

![image-20220115124917382](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115124917382.png)

进入ConditionObject的doSignal流程，取得等待队列中第一个Node，即Thread-0所在Node

![image-20220115125112955](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115125112955.png)

执行transferForSignal 流程，将该Node加入AQS队列尾部，将Thread-0的waitStatus改为0，Thread-3的waitStatus改为-1

![image-20220115130140538](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115130140538.png)

Thread-1释放锁，进入unlock流程，略

### 读写锁

#### ReentrantReadWriteLock

当读操作远远高于写操作时，这时候使用读写锁让读-读可以并发，提高性能。

类似于数据库中的select.…from..lock in share mode

提供一个数据容器类内部分别使用读锁保护数据的read（）方法，写锁保护数据的write（）方法

```java
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
```

注意事项

- 读锁不支持条件变量
- 重入时升级不支持：即持有读锁的情况下去获取写锁，会导致获取写锁永久等待
- 重入时降级支持：即持有写锁的情况下去获取读锁



![image-20220115173630414](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115173630414.png)

#### 缓存更新策略

更新时，是先清缓存还是先更新数据库

先清缓存

![image-20220115175245439](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115175245439.png)

先更新数据库

![image-20220115175536545](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115175536545.png)

注意

- 以上实现体现的是读写锁的应用，保证缓存和数据库的一致性，但有下面的问题没有考虑
- 适合读多写少，如果写操作比较频繁，以上实现性能低
- 没有考虑缓存容量·没有考虑缓存过期
- 只适合单机
- 并发性还是低，目前只会用一把锁
- 更新方法太过简单粗暴，清空了所有key（考虑按类型分区或重新设计key）

#### 读写锁原理

读写锁角的是同一个Sycn同步器，因此等待队列、state等也是同一个

**tl w.lock，t2 r.lock**

1）tl成功上锁，流程与ReentrantLock加锁相比没有特殊之处，不同是写锁状态占了state的低16位，而读锁使用的是state的高16位

![image-20220115181842835](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115181842835.png)



2）t2执行rlock，这时进入读锁的sync.acquireShared（1）流程，首先会进入tryAcquireShared 流程。如果有写锁占据，那么tryAcquireShared返回-1表示失败tryAcquireShared返回值表示

- -1表示失败
- 0表示成功，但后继节点不会继续唤醒
- 正数表示成功，而且数值是还有几个后继节点需要唤醒，读写锁返回1

![image-20220115183743989](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115183743989.png)

3）这时会进入sync.doAcquireShared（1）流程，首先也是调用addWaiter添加节点，不同之处在于节点被设置为Node.SHARED模式而非Node.EXCLUSIVE模式，注意此时t2仍处于活跃状态

![image-20220115183953200](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115183953200.png)

4）t2会看看自己的节点是不是老二，如果是，还会再次调用tryAcquireShared（1）来尝试获取锁

5）如果没有成功，在doAcquireShared内for（；）循环一次，把前驱节点的waitStatus改为-1，再for（；）循环一次尝试tryAcquireShared（1）如果还不成功，那么在parkAndCheckInterruptO处park

![image-20220115185256378](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115185256378.png)

![image-20220115184057128](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115184057128.png)



**t3r.lock，t4w.lock**

这种状态下，假设又有t3加读锁和t4加写锁，这期间tl仍然持有锁，就变成了下面的样子

![image-20220115185347820](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115185347820.png)

**t1w.unlock**

这时会走到写锁的sync.release（1）流程，调用sync.tryRelease（1）成功，变成下面的样子

![image-20220115185541578](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115185541578.png)

接下来执行唤醒流程sync.unparkSuccessor，即让老二恢复运行，这时t2在doAcquireShared 内 parkAndCheckInterrupt）处恢复运行

这回再来一次for（；；）执行tryAcquireShared成功则让读锁计数加一

![image-20220115190344145](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115190344145.png)

这时t2已经恢复运行，接下来t2调用setHeadAndPropagate（node，1），它原本所在节点被置为头节点

![image-20220115190434454](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115190434454.png)

事情还没完，在setHeadAndPropagate方法内还会检查下一个节点是否是shared，如果是则调用doReleaseShared0将head的状态从-1改为0并唤醒老二，这时3在doAcquireShared 内 parkAndCheckInterruptO处恢复运行

![image-20220115191100112](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115191100112.png)

这回再来一次for（；）执行tryAcquireShared成功则让读锁计数加一

![image-20220115191142771](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115191142771.png)

这时t3已经恢复运行，接下来t3调用setHeadAndPropagate（node，1），它原本所在节点被置为头节点

![image-20220115191444288](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115191444288.png)

下一个节点不是shared了，因此不会继续唤醒t4所在节点

**t2r.unlock，t3 r.unlock t2**

进入sync.releaseShared（1）中，调用tryReleaseShared（1）让计数减一，但由于计数还不为零

![image-20220115191745022](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115191745022.png)

t3进入sync.releaseShared（1）中，调用tryReleaseShared（1）让计数减一，这回计数为零了，进入doReleaseShared）将头节点从-1改为0并唤醒老二，即

#### StampedLock

该类自JDK8加入，是为了进一步优化读性能，它的特点是在使用读锁、写锁时都必须配合【戳】使用

加解读锁

```java
1ong stamp=1ock.readLock(); 
lock.unlockRead(stamp);
```

加解锁

```
long stamp=lock.writeLock();
1ock. unlockwrite(stamp);
```

乐观读，StampedLock 支持tryoptimisticRead（）方法（乐观读），读取完毕后需要做一次戳校验如果校验通过，表示这期间确实没有写操作，数据可以安全使用，如果校验没通过，需要重新获取读锁，保证数据安全。

```
long stamp=lock.tryoptimisticRead（）；
//验戳
if（！lock.validate（stamp））{
//锁升级
}
```

注意

- StampedLock不支持条件变量I
- StampedLock不支持可重

### Semaphore

信号量，用来限制能同时访问共享资源的线程上限。

![image-20220115194703219](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220115194703219.png)

- 使用Semaphore限流，在访问高峰期时，让请求线程阻塞，高峰期过去再释放许可，当然它只适合限制单机线程数量，并且仅是限制线程数，而不是限制资源数（例如连接数，请对比Tomcat LimitLatch的实现）
- 用Semaphore实现简单连接池，对比[享元模式]下的实现（用wait notify），性能和可读性显然更好，注意下面的实现中线程数和数据库连接数是相等的



#### Semaphore原理

##### 加锁解锁流程

Semaphore 有点像一个停车场，permits就好像停车位数量，当线程获得了permits就像是获得了停车位，然后停车场显示空余车位减一

刚开始，permits（state）为3，这时5个线程来获取资源

![image-20220116092801858](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116092801858.png)

假设其中Thread-1，Thread-2，Thread-4cas 竞争成功，而Thread-0和Thread-3竞争失败，进入AQS队列park阻塞

![image-20220116093125976](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116093125976.png)

这时Thread-4释放了permits，状态如下

![image-20220116093331109](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116093331109.png)

接下来Thread-0竞争成功，permits 再次设置为0，设置自己为head节点，断开原来的head 节点，unpark 接下来的Thread-3节点，但由于permits是0，因此Thread-3在尝试不成功后再次进入park状态

### CountdownLatch

用来进行线程同步协作，等待所有线程完成倒计时。

其中构造参数用来初始化等待计数值，await）用来等待计数归零，countDown0用来让计数减一

不能使计数复原。

![image-20220116094845026](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116094845026.png)

![image-20220116095816894](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116095816894.png)

### **CyclicBarrier**

循环棚栏，用来进行线程协作，等待线程满足某个计数。构造时设置计数个数】，每个线程执行到某个需要同步”的时刻调用await）方法进行等待，当等待的线程数满足[计数个数】时，继续执行

![image-20220116112024303](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116112024303.png)

### 线程安全集合类概述

![image-20220116112334234](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116112334234.png)

线程安全集合类可以分为三大类：

- 遗留的线程安全集合如Hashtable，Vector

- 使用Collections装饰的线程安全集合，如：

  - Collections.synchronizedCollection 
  - Collections.synchronizedList
  - Collections.synchronizedMap
  - Collections.synchronizedSet
  - Collections.synchronizedNavigableMap
  - Collections.synchronizedNavigableset
  - Collections.synchronizedSortedMap
  - Collections.synchronizedSortedSet 

  java.util.concurrent.*

重点介绍java.util.concurrent.*下的线程安全集合类，可以发现它们有规律，里面包含三类关键词：
Blocking、CopyOnWrite、Concurrent

- Blocking大部分实现基于锁，并提供用来阻塞的方法
- CopyOnWrite之类容器修改开销相对较重
- Concurrent 类型的容器
  - 内部很多操作使用cas优化，一般可以提供较高吞吐量
  - 弱一致性
    - 遍历时弱一致性，例如，当利用迭代器遍历时，如果容器发生修改，迭代器仍然可以继续进行遍历，这时内容是旧的
    - 求大小弱一致性，size操作未必是100%准确
    - 读取弱一致性

遍历时如果发生了修改，对于非安全容器来讲，使用fail-fast机制也就是让遍历立刻失败，抛出ConcurrentModificationException，不再继续遍历

### ConcurrenthashMap

![image-20220116114757417](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116114757417.png)

### ConcurrentHashMap原理

#### 1、JDK 7 HashMap并发死链问题

注意

- 要在JDK7下运行，否则扩容机制和hash的计算方法都变了
- 以下测试代码是精心准备的，不要随便改动

![image-20220116115350674](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116115350674.png)

![image-20220116115557705](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116115557705.png)

![image-20220116115629425](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116115629425.png)

![image-20220116120813964](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116120813964.png)

![image-20220116121319142](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116121319142.png)

![image-20220116121908145](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116121908145.png)

![image-20220116122154849](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116122154849.png)

![image-20220116122218571](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116122218571.png)

![image-20220116122236962](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116122236962.png)

![image-20220116122323038](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116122323038.png)

小结

- 究其原因，是因为在多线程环境下使用了非线程安全的map集合
- JDK8虽然将扩容算法做了调整，不再将元素加入链表头（而是保持与扩容前一样的顺序），但仍不意味着能够在多线程环境下能够安全扩容，还会出现其它问题（如扩容丢数据）

#### 2、JDK 8 ConcrrentHashMap

##### 重要属性和内部类

![](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116123241789.png)

##### **重要方法**

![image-20220116123325714](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116123325714.png)

##### 构造器分析

可以看到实现了懒惰初始化，在构造方法中仅仅计算了table的大小，以后在第一次使用时才会真正创建

![image-20220116123849871](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116123849871.png)

##### get流程

![image-20220116124605701](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116124605701.png)

##### Put流程

以下数组简称（table），链表简称（bin）

![image-20220116125615374](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116125615374.png)

![image-20220116125711192](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116125711192.png)

![image-20220116130029210](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116130029210.png)

![image-20220116130114844](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116130114844.png)

##### initTable方法

![image-20220116130308628](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116130308628.png)

##### addCount方法

![image-20220116130641689](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116130641689.png)

![image-20220116130902490](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116130902490.png)

##### Size计算流程

size 计算实际发生在put，remove改变集合元素的操作之中

- 没有竞争发生，向baseCount 累加计数
- 有竞争发生，新建counterCells，向其中的一个cell累加计数
  - counterClls初始有两个cel
  - 如果计数竞争比较激烈，会创建新的cell 来累加计数

![image-20220116131151683](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116131151683.png)

#### 3、jdk 7 ConcurrentHashMap

它维护了一个segment数组，每个segiment 对应一把锁

- 优点：如果多个线程访问不同的segment，实际是没有冲突的，这与jdk8中是类似的
- 缺点：Segments数组默认大小为16，这个容量初始化指定后就不能改变了，并且不是懒惰初始化

##### 构造器分析

![image-20220116131922761](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116131922761.png)

![image-20220116131954546](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116131954546.png)

![image-20220116132039332](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116132039332.png)

![image-20220116132230043](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116132230043.png)

##### put流程

![image-20220116132404362](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116132404362.png)

segment继承了可重入锁（ReentrantLock），它的put方法为

![image-20220116132654149](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116132654149.png)

![image-20220116132803293](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116132803293.png)

##### rehash 流程

发生在put中，因为此时已经获得了锁，因此rehash时不需要考虑线程安全

![image-20220116133130893](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116133130893.png)

![image-20220116133457901](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116133457901.png)

![image-20220116133746154](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116133746154.png)

##### get流程

![image-20220116133832482](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116133832482.png)

##### Size计算流程

- 计算元素个数前，先不加锁计算两次，如果前后两次结果如一样，认为个数正确返回
- 如果不一样，进行重试，重试次数超过3，将所有segment锁住，重新计算个数返回

![image-20220116134201403](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116134201403.png)

![image-20220116134431565](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116134431565.png)

### LinkedBlockingQueue原理

#### 1.基本的入队出队

![image-20220116134816066](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116134816066.png)

![image-20220116140545400](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116140545400.png)

![image-20220116173508785](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116173508785.png)

**出队**

![image-20220116173608799](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116173608799.png)

![image-20220116174009475](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116174009475.png)

![image-20220116174128399](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116174128399.png)

![image-20220116174334691](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116174334691.png)

#### 2、加锁分析

一高明之处一在于用了两把锁和dummy节点

- 用一把锁，同一时刻，最多只允许有一个线程（生产者或消费者，二选一）执行

- 用两把锁，同一时刻，可以允许两个线程同时（一个生产者与一个消费者）执行

  - 消费者与消费者线程仍然串行
  - 生产者与生产者线程仍然串行

  线程安全分析

- 当节点总数大于2时（包括dummy节点），putLock保证的是last节点的线程安全，takeLock保证的是head 节点的线程安全。两把锁保证了入队和出队没有竞争

- 当节点总数等于2时（即一个dummy节点，一个正常节点）这时候，仍然是两把锁锁两个对象，不会竞争

- 当节点总数等于1时（就一个dummy节点）这时take线程会被notEmpty条件阻塞，有竞争，会阻塞

![image-20220116175055546](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116175055546.png)

put操作

![image-20220116180024832](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116180024832.png)

take操作

![image-20220116180050140](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116180050140.png)

#### 性能比较

主要列举LinkedBlockingQueue 与ArmayBlockingQueue的性能比较

- Linked支持有界，Array 强制有界
- Linked 实现是链表，Array实现是数组
- Linked是懒惰的，而Array 需要提前初始化Node数组
- Linked每次入队会生成新Node，而Array的Node是提前创建好的
- Linked 两把锁，Array一把锁

### ConcurrentLinkedQueue

ConcurrentLinkedQueue的设计与LinkedBlockingQueue非常像，也是

- 两把【锁】，同一时刻，可以允许两个线程同时（一个生产者与一个消费者）执行

- dummy节点的引入让两把【锁】将来锁住的是不同对象，避免竞争

- 只是这【锁】使用了cas来实现

  事实上，ConcurrentLinkedQueue应用还是非常广泛的

例如之前讲的Tomcat的Connector 结构时，Acceptor作为生产者向Poller消费者传递事件信息时，正是采用了ConcurentLinkedQueue将SocketChannel给Poller使用

![image-20220116180437903](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116180437903.png)

### CopyOnWriteArrayList

CopyonuriteArrayset是它的马甲

底层实现采用了写入时拷贝的思想，增删改操作会将底层数组拷贝一份，更改操作在新数组上执行，这时不影响其它线程的并发读，读写分离。

以新增为例：

![image-20220116180658590](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116180658590.png)

这里的源码版本是Java11，在Java1.8中使用的是可重入锁而不是synchronized

其它读操作并未加锁，例如：

![image-20220116181010378](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116181010378.png)

适合[读多写少】的应用场景

![image-20220116181101564](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116181101564.png)

![image-20220116181557223](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220116181557223.png)