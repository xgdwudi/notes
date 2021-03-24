## mysql 高级
### mysql 的架构介绍
#### mysql的简介
https://www.bilibili.com/video/BV1KW411u7vy?spm_id_from=333.788.b_636f6d6d656e74.19

1. ![](../images/Snipaste_2021-03-24_10-17-32.png)
2. mysql的配置文件
   1. ![](../images/Snipaste_2021-03-24_10-31-55.png)
   2. ![](../images/Snipaste_2021-03-24_10-33-37.png)
   3. ![](../images/Snipaste_2021-03-24_10-34-29.png)
   4. ![](../images/Snipaste_2021-03-24_10-36-27.png)
   5. ![](../images/Snipaste_2021-03-24_10-55-50.png)
   6. ![](../images/Snipaste_2021-03-24_10-56-45.png)
   7. ![](../images/Snipaste_2021-03-24_11-04-43.png)
   8. ![](../images/Snipaste_2021-03-24_11-11-16.png)
3. 索引  
   1. 单值索引  只给某一个表的某一个字段去建立索引 create index idx_user_name on user(name);
   2. 复合索引   create index idx_user_name_email on user(name,email);
4. join 查询
   1. ![](../images/Snipaste_2021-03-24_11-27-42.png)
   2. ![](../images/Snipaste_2021-03-24_11-29-11.png)
5. 索引
   1. 什么是索引
   2. 索引的目的
      1. ![](../images/Snipaste_2021-03-24_13-21-38.png)