# mysql（关系性数据库）
1. 实现数据持久化
2. 使用完整的管理系统统一管理，易于查询
# 数据库概念：
- DB 数据库（database）: 存储数据的仓库。它保存了一系列有组织的数据。保存一组有组织数据的容器
- DBMS:  数据库管理系统（Database Management System），数据库是通过DBMS创建和操作的容器![](../images/Snipaste_2021-03-07_20-32-28.png)
- SQL  结构化查询语言  （Structure Query Language）:专门用来与DBMS通信的语言。
1. DBMS 分为两类
   1. 基于文件共享系统的DBMS
   2. 基于客户机-服务器的DBMS(mysql,oracle)![](../images/Snipaste_2021-03-07_21-02-50.png)
2. mysql 启动停止
   1. net stop mysql(服务名称) 
   2. net start mysql(服务名称) 
   3. mysql -h localhost -P 3306 -u root -p123456 （进入mysql）![](../images/Snipaste_2021-03-07_21-13-45.png)
3. mysql的常见命令<br/>
![](../images/Snipaste_2021-03-07_21-27-41.png)

4. mysql 的语法规范<br/>![](../images/Snipaste_2021-03-07_21-31-45.png)
5. DQL 语言  Data Query Language  查询语言
   1. ![](../images/Snipaste_2021-03-08_17-20-12.png)
   2. 字符串连接CONCAT(str,str)
   3. IFNULL（commission_id,0） 判断是否为空，为空返回0
   4. ![](../images/Snipaste_2021-03-08_17-35-28.png)
   5. 转义escape ![](../images/Snipaste_2021-03-08_18-10-38.png)
   6.  ![](../images/Snipaste_2021-03-08_18-18-49.png)
   7.  ![](../images/Snipaste_2021-03-08_18-25-08.png)
   8.  ![](../images/Snipaste_2021-03-08_18-27-21.png)
   9.  ![](../images/Snipaste_2021-03-08_18-28-56.png)
   10. ![](../images/Snipaste_2021-03-08_19-05-15.png)  
   11. 可以根据表达式排序 ![](../images/Snipaste_2021-03-08_19-11-02.png)  
   12. 可以根据别名排序   
   13. 可以根据函数排序   ![](../images/Snipaste_2021-03-08_19-12-51.png)
   14. 可以根据多个字段排序   ![](../images/Snipaste_2021-03-08_19-14-11.png)
   15.  ![](../images/Snipaste_2021-03-08_19-16-59.png)
   16.  常见函数   
        1.   ![](../images/Snipaste_2021-03-08_19-24-17.png) ![](../images/Snipaste_2021-03-08_19-25-50.png)
        2.   字符函数：LENGTH获取参数值得字节个数  SELECT LENGTH("JOHN")
        3.   CONCAt 拼接字符串CONCAt（str,str）
        4.   upper,lower   upper（str） 大写  lower(str)小写
        5.   substr、substring sql 语言中索引都是从1开始得 substr("asdasd",6) ![](../images/Snipaste_2021-03-08_20-24-11.png)
        6.   instr select INSTR("123456张三","张三")；  返回字串在大串里面得索引
        7.   trim 去除字符串前后得空格 trim("  asdas ")   trim('a' from "asdasda")  去除前后得a
        8.   lpad lpad('asda',10,'*')   用指定得字符实现左填充指定长度
        9.   rpad rpad('asda',10,'*')   用指定得字符实现右填充指定长度
        10.  replace 替换  replace('zhasnags','zha','ad')
   17. 数学函数
       1.  round   四舍五入  round(1.25) 四舍五入 round(1.253,2) 小数点后保留几位
       2.  ceil 向上取整，返回大于等于该参数得最小整数
       3.  floor 向下取整，返回《=该参数得最大整数
       4.  truncate 截断  truncate(1.65,1)  小数点后保留几位
       5.  mod 取余 mod(10,3) ![](../images/Snipaste_2021-03-08_20-44-12.png)
   18. 日期函数
       1.  now() 返回当前系统日期+时间
       2.  curdate（） 返回当权系统日期 ，不办含时间
       3.  curtime() 返回当前的时间
       4.  可以获取指定的部分  ![](../images/Snipaste_2021-03-08_20-50-13.png)
       5.  ![](../images/Snipaste_2021-03-08_20-52-28.png) ![](../images/Snipaste_2021-03-08_20-53-06.png)
       6.  DATEDIFF("2020.1.1","2019.1.6")   求两个日期相差的天数
   19. 其他函数
       1.  select version();
       2.  select DATABASE();
       3.  SELECT USER();
   20. 流程控制函数
       1.   if 函数:if else效果  select if(10《5，'1','2');
       2.   case 函数的使用1  swith case 的效果
            1.   <br/>![](../images/Snipaste_2021-03-08_21-05-12.png) ![](../images/Snipaste_2021-03-08_21-09-24.png)
            2.   类似于多重if ![](../images/Snipaste_2021-03-08_21-12-21.png)
   21.  分组函数  功能用作统计使用，又称为聚合函数或者统计函数或组函数  sum求和 avg平均值 max最大值 min最小值 count计算个数
        1.   ps: <br/> ![](../images/Snipaste_2021-03-08_21-26-01.png) ![](../images/Snipaste_2021-03-08_21-37-13.png) ![](../images/Snipaste_2021-03-08_21-40-16.png) ![](../images/Snipaste_2021-03-08_21-44-20.png)
   22. 分组查询
       1.  ![](../images/Snipaste_2021-03-08_21-56-44.png) ![](../images/Snipaste_2021-03-08_21-58-54.png) ![](../images/Snipaste_2021-03-08_22-05-25.png) ![](../images/Snipaste_2021-03-08_22-13-08.png) ![](../images/ Snipaste_2021-03-08_22-14-02.png) ![](../images/Snipaste_2021-03-08_22-22-07.png)
   23. 连接查询 多表查询
       1.  笛卡尔乘积现象： 表1 有m行 表2 有 n行 结果=m*n行 发生原因：没有有效的连接条件  如何避免： 添加有效的连接条件
       2.  ![](../images/Snipaste_2021-03-08_22-36-32.png)
       3.  sql92标准
           1.  等值连接 select name,boyname from boys,beauty where beauty.boyfriend_id = boys.id 为表起别名，提高语句的简洁度，区分多个重名字段 当起别名后，不能使用原来的表名去限定![](../images/Snipaste_2021-03-08_22-41-28.png) ![](../images/Snipaste_2021-03-09_14-11-20.png)
           2.  非等值连接
           3.  自连接 ![](../images/Snipaste_2021-03-09_20-30-00.png)
       4. sql99
          1. ![](../images/Snipaste_2021-03-09_21-03-58.png)
          2. 内连接 ![](../images/Snipaste_2021-03-09_21-13-41.png)
          3. ![](../images/Snipaste_2021-03-09_21-21-47.png)
          4. 交叉链接
    24.  子查询 ![](../images/Snipaste_2021-03-10_21-32-56.png) ![](../images/Snipaste_2021-03-10_21-36-39.png) ![](../images/Snipaste_2021-03-10_22-13-52.png)  exists(相关子查询)是否存在 返回 1（true） 或者 0(false)  ![](../images/Snipaste_2021-03-10_22-24-41.png)
    25.  分页查询 ![](../images/Snipaste_2021-03-10_22-32-10.png)
    26.  https://www.bilibili.com/video/BV1xW411u7ax?p=96&spm_id_from=pageDriver
6. DML 语言  Data 操作单词 Language 增删改语言 
7. DDL 语言  Data define Language   定义库表语言
8. DTL 语言  Data transaction Control 事务控制语言


