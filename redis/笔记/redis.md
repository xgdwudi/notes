# nosql 数据库
1. 是什么 ![](../images/Snipaste_2021-03-12_10-07-42.png)
2. 与传统数据库的区别 ![](../images/Snipaste_2021-03-12_10-15-09.png)
3. nosql数据模型  ![](../images/Snipaste_2021-03-15_11-10-54.png)
4. nosql数据四大分类  ![](../images/Snipaste_2021-03-15_11-17-34.png)
5. 分布式数据库中CAP原理CAP+BASE 
   1. ![](../images/Snipaste_2021-03-15_11-22-43.png)
   2. ![](../images/Snipaste_2021-03-15_11-25-36.png)
   3. ![](../images/Snipaste_2021-03-15_11-31-00.png)
   4. ![](../images/Snipaste_2021-03-15_11-35-58.png)
## redis REmote DIctionary Server(远程字典服务器)
1. redis 
   1. 切换库 select 5 一共16个库
   2. 查看所有的键在某个库 keys *,查看前面有k的键 keys k?
   3. 查看当前库的大小 dbsize 
   4. 清除当前库 Flushdb 
   5. 通杀所有库： Flushall
   6. redis的索引从0开始
2. redis 的数据类型
   1. ![](../images/Snipaste_2021-03-15_15-03-46.png)
3. redis 常见数据类型操作命令 http://redisdoc.com
4. key
   1. set k1 v1 存值
   2. get k1 根据键取值
   3. exits k1 判断是否存在
   4. move k3 2 将某个键移到那个库
   5. ![](../images/Snipaste_2021-03-15_15-11-32.png)
5. string   
   1. ![](../images/Snipaste_2021-03-15_15-24-43.png)
6. list
   1. ![](../images/Snipaste_2021-03-15_15-27-28.png)
   2. ![](../images/Snipaste_2021-03-15_15-31-27.png)
   3. ![](../images/Snipaste_2021-03-15_15-50-51.png)
7. set 
   1. ![](../images/Snipaste_2021-03-15_15-59-08.png)
8. hash
   1. ![](../images/Snipaste_2021-03-15_16-02-28.png)
   2. ![](../images/Snipaste_2021-03-15_16-09-29.png)
9. Zset
   1.  ![](../images/Snipaste_2021-03-15_16-10-44.png)
   2.  ![](../images/Snipaste_2021-03-15_16-13-26.png)
10. redis配置文件
    1.  redis.conf 
    2.  ![](../images/Snipaste_2021-03-15_16-45-14.png)
    3.  ![](../images/Snipaste_2021-03-15_16-47-30.png)
    4.  ![](../images/Snipaste_2021-03-15_16-49-03.png)
    5.  ![](../images/Snipaste_2021-03-15_16-49-41.png)
    6.  ![](../images/Snipaste_2021-03-15_16-52-03.png)
    7.  ![](../images/Snipaste_2021-03-15_16-52-46.png)
11. redis 的持久化
    1.  rdb (redis database)
        1.  ![](../images/Snipaste_2021-03-15_16-58-53.png)
        2.  ![](../images/Snipaste_2021-03-16_19-30-31.png)
        3.  ![](../images/Snipaste_2021-03-16_19-45-21.png)
        4.  ![](../images/Snipaste_2021-03-16_19-47-08.png)  save命令可以直接保存
        5.  ![](../images/Snipaste_2021-03-16_19-52-42.png)
        6.  ![](../images/Snipaste_2021-03-16_19-55-08.png)
        7.  ![](../images/Snipaste_2021-03-16_19-58-45.png)
        8.  ![](../images/Snipaste_2021-03-17_14-50-23.png)
        9.  ![](../images/Snipaste_2021-03-17_14-58-04.png)
        10. ![](../images/Snipaste_2021-03-17_14-59-11.png)
    2.  aof (append only file)
        1.  ![](../images/Snipaste_2021-03-17_15-04-36.png)
        2.  aof文件出错后 ![](../images/Snipaste_2021-03-17_15-20-40.png)
        3.  ![](../images/Snipaste_2021-03-17_15-24-50.png)
        4.  rewrite ![](../images/Snipaste_2021-03-17_15-30-09.png)
        5.  ![](../images/Snipaste_2021-03-17_15-33-00.png)
        6.  ![](../images/Snipaste_2021-03-17_15-33-31.png)
        7.  ![](../images/Snipaste_2021-03-17_15-41-29.png)
        8.  ![](../images/Snipaste_2021-03-17_15-46-14.png)
        9.  ![](../images/Snipaste_2021-03-17_15-46-48.png)
    3.  两者区别
        1.  ![](../images/Snipaste_2021-03-17_15-53-09.png)
        2.  ![](../images/Snipaste_2021-03-17_15-53-09.png)
        3.  ![](../images/Snipaste_2021-03-17_16-05-28.png)
    4.  https://www.bilibili.com/video/BV1oW411u75R?p=18&spm_id_from=pageDriver
12. redis 事务
    1.  ![](../images/Snipaste_2021-03-17_16-07-11.png)
    2.  ![](../images/Snipaste_2021-03-17_16-11-54.png)
    3.  ![](../images/Snipaste_2021-03-17_16-12-57.png)
    4.  编译时报错 全体连坐 运行时报错 冤头寨主 对事务支持为部分支持
    5.  ![](../images/Snipaste_2021-03-17_16-45-39.png)
        1.  悲观锁 整张表全锁
        2.  乐观锁 不会上锁，行内容加入version ![](../images/Snipaste_2021-03-17_17-04-12.png)
    6. 特性：![](../images/Snipaste_2021-03-17_17-05-04.png)
13. redis的发布订阅机制
    1.  ![](../images/Snipaste_2021-03-17_17-07-50.png)
    2.  命令：![](../images/Snipaste_2021-03-17_17-09-55.png)
14. redis 的复制机制
    1.  主从复制，读写分离 ![](../images/Snipaste_2021-03-17_17-13-03.png)
    2.  读写分离，容灾恢复
    3.  配从不配主 ![](../images/Snipaste_2021-03-17_17-15-40.png)
    4.  命令：info replicayion 查看redis信息
        1.  一株2从
            1.  SLAVEOF 地址 端口  从机备份数据
            2.  把主机的数据从头录到尾
            3.  只有主机才能写，从机负责读
            4.  当主机挂掉之后，从机原地待命，主机恢复之后，恢复正常
            5.  当从机挂掉之后，重启后需要重新连接（除非写进配置文件redis.conf）
        2. 薪火相传
           1. ![](../images/Snipaste_2021-03-17_17-40-00.png)
        3. 反客为主
           1. 当从机挂掉之后，从机slaveof no one 让从机成为新的领导
           2. 以前的主机回来后也不会成位新的主机
           3. 别的从机需要重新设置主机
           4. ![](../images/Snipaste_2021-03-17_17-48-03.png)
    5. 复制原理
       1. ![](../images/Snipaste_2021-03-17_17-49-29.png)
    6. 哨兵模式（） 反客为主的自动版
       1. ![](../images/Snipaste_2021-03-17_19-16-44.png)
       2. ![](../images/Snipaste_2021-03-17_19-17-11.png)
       3. ![](../images/Snipaste_2021-03-17_19-19-18.png)
       4. 当以前主机挂掉之后，会从从机中投票选出主机，当以前的主机恢复后，会作为从机去备份主机的数据
       5. 缺点 ![](../images/Snipaste_2021-03-17_19-27-43.png)
15. 缓存穿透
    1.  含义：缓存穿透是指缓存和数据库中都没有的数据，而用户不断发起请求，如发起为id为“-1”的数据或id为特别大不存在的数据。这时的用户很可能是攻击者，攻击会导致数据库压力过大。
    2.  解决方案：接口层增加校验，如用户鉴权校验，id做基础校验，id<=0的直接拦截；
从缓存取不到的数据，在数据库中也没有取到，这时也可以将key-value对写为key-null，缓存有效时间可以设置短点，如30秒（设置太长会导致正常情况也没法使用）。这样可以防止攻击用户反复用同一个id暴力攻击
16.   缓存击穿
      1.    缓存击穿是指缓存中没有但数据库中有的数据（一般是缓存时间到期），这时由于并发用户特别多，同时读缓存没读到数据，又同时去数据库去取数据，引起数据库压力瞬间增大，造成过大压力
      2.    解决方案：

设置热点数据永远不过期。
加互斥锁，互斥锁参考代码如下：
          说明：
          1）缓存中有数据，直接走上述代码13行后就返回结果了
         2）缓存中没有数据，第1个进入的线程，获取锁并从数据库去取数据，没释放锁之前，其他并行进入的线程会等待100ms，再重新去缓存取数据。这样就防止都去数据库重复取数据，重复往缓存中更新数据情况出现。

          3）当然这是简化处理，理论上如果能根据key值加锁就更好了，就是线程A从数据库取key1的数据并不妨碍线程B取key2的数据，上面代码明显做不到这点。
17. 缓存雪崩
    1.    缓存雪崩是指缓存中数据大批量到过期时间，而查询数据量巨大，引起数据库压力过大甚至down机。和缓存击穿不同的是，缓存击穿指并发查同一条数据，缓存雪崩是不同数据都过期了，很多数据都查不到从而查数据库。
    2.    解决方案：缓存数据的过期时间设置随机，防止同一时间大量数据过期现象发生。
如果缓存数据库是分布式部署，将热点数据均匀分布在不同得缓存数据库中。
设置热点数据永远不过期。