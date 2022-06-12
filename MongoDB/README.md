---
title: mongodb
date: 2022/01/05 20:33:25  
tags:
- database
- mongodb
- nosql
categories: mongodb数据库
thumbnail: https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220107125941342.png
cover: https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220107125941342.png
---



# mongodb简介

[官方地址](https://docs.mongodb.com/manual/tutorial/query-documents/)

- Mongodb是为了快速开发互联网Web应用而设计的数据库系统。
- Mongodb的设计目标是极简、灵活、作为Web应用栈的一部分。
- Mongodb的数据模型是面向文档的，所谓文档是一种类似于JSON的结构，简单理解MongoDB这个数据库中存的是各种河阳的JSON。（BSON）

<!-- more -->

## 三个概念

- 数据库（database）

-数据库是一个仓库，在仓库中可以存放集合。

- 集合（collection）

集合类似于数组，在集合中可以存放文档。

- 文档（document）

文档数据库中的最小单位，我们存储和操作的内容都是文档。

在Mongodb中，数据库和集合都不需要手动创建；

当我们去创建文档是，如果文档所在的集合或数据库不存在会自动创建数据库和集合

## 下载

- [下载地址](https://www.mongodb.org/dl/win32/)

- Mongodb的版本偶数版本为稳定版，奇数版本为开发版
- 3.2之后在也没有win32版本

windows需要配置环境变量

- 安装
- 配置环境变量
  - c:\program Files\MongoDb\Server\3.2\bin

- 在c盘根目录
  - 创建一个文件夹 data
  - 在data中创建一个文件夹db
- 打开cmd窗口
  - 输入mongod 启动mongodb服务器
- 在打开一个cmd窗口
  - 输入mongo 连接,出现   > 
- 指定mongodb的数据库目录
  - mongod --dbpath  目录  --port 10086      (可以通过这个去指定端口) 
- 配置系统服务,可以在自动在后台启动

## 基本指令

```sql
show dbs; 
show database;// 显示当前所有的数据库
use 数据库名称; // 进入test数据库
db 表示当前的数据库
show collections  // 显示数据库中所有的集合
```

数据库的增删改查工作

```
向数据库中插入文档
db.<collection>.insert(doc)
	- 向集合中插入一个文档
	想test数据库中,对集合stus插入一个新的学生对象，{name:"张三",age:14,gender:"男"}
	db.stus.insert({name:"张三",age:14,gender:"男"})
db.<collection>.find()
   	查询当前集合中的所有的文档
批量增加
db.<collection>.insert([
	{},
	{}
]);
- 当我们向集合中插入文档时，如果没有给文档指定_id属性，则数据库会自动为文档添加_id，改属性用来作为文档的唯一标识

objectId()  生成唯一标识
- 插入一个文档对象
db.collection.insertOne();
- 插入多个文档对象
db.collection.insertMany();
_id 我们可以自己指定，如果我们指定了数据库就不会在添加了，如果自己指定_id 也必须确保它的唯一性


```

```sql
- 文档的查找 find() 用来查询集合中所有符合条件的文档
- find()中可以接收一个对象作为条件参数
	{} 表示查询集合中的所有文档
	{字段名：值}  查询属性是指定值的文档
db.collection.find();
db.collection.find({age:12});
- 返回符合条件的第一个对象
db.collection.findOne({age:28});
- 返回符合条件的条数
db.collection.find({}).count();
db.collection.find({}).length();
```

```
- 修改
- update() 默认情况下会使用新对象来替换旧的对象（默认情况下只改一个，通过修改配置 mul）
- 如果需要修改执行的属性，而不是替换需要使用“修改操作符”来完成
	- $set 可以用来修改文档中的指定属性
	- $unset 删除文档指定属性
 db.collection.update(查询条件，新对象);
 db.collection.update({name:"names"}，{age:12});
 db.collection.update({"_id":"5922"},
 {$set:{
 name:"shaheshang"
 }}
 );
 - $push 用于向数组中添加一个新的元素
 - $addToSet 向数组中添加一个新元素，如果数组中已经存在了该元素，则不会添加
 - 同时修改多个符合条件的文档，和上面的
 db.collection.updateMany();
 
 - 修改一个符合条件的文档
 db.collection.updateOne();
 - 替换一个文档
 db.collection.replaceOne()
```

```
- 删除文档
db.collection.deleteOne();
- 删除多个文档
db.collection.deleteMany();
- 移除 可以删除符合条件的所有文档,如第二个参数只传递一个true,则只删除一个
- 如果只传递一个空参，则会清空集合，如真清空，性能较差
db.collection.remove();
- 删除集合
db.stus.drop();
- 删除数据库
db.dropDatabase();
```

```
- 循环添加20000条数据
for(var i = 1; i <20000;i++){
	db.numbers.insert({num:i});
}

var arr = [];
for(var i = 1; i <20000;i++){
	arr.push(i);
}
db.numbers.insert(arr);
```

```sql
- 查看numbers集合中的第21条到30条数据
db.numbers.find().skip(20).limit(10);

db.numbers.find().limit(10).skip(10);

- 查询numbers ,num 大于 5000的文档
db.numbers.find({num:{$gt:500}});
db.numbers.find({num:{$eq:500}});


- 查询numbers ,num 小于 50的文档 大于40
db.numbers.find({num:{$gr:40,$lt:50}})
```

## 文档之间的关系

### 一对一

通过内嵌文档的形式

```
- 一个丈夫对应一个妻子
db.wifeAndHusband.insert(
    [
        {
            name: "张三",
            husband: {
                name: "李四"
            }
        },
        {
            name: "王五",
            husband: {
                name: "赵四"
            }
        }
    ]
)
```



### 一对多、多对一

```
- 父母对应多个孩子

db.users.insert([
    {
        username: "swk"
    },
    {
        username: "zbj"
    }
]);

db.users.find();

db.order.insert([{
   list:["apple","balance","dayali"],
	 userId:ObjectId("61d7bd1dca5100008d0013d4")
	 }
]);

var user_id = db.users.findOne({username:"swk"})._id;

db.order.findOne({userId:user_id});
```

### 多对多

```
- 分类  -商品
- 老师  -学生
```

## 排序

查询文档时，默认是按照_id排序的（升序）

sort 可以用来指定文档排序的规则,需要传递一个对象，指定排序规则1 为升序，-1为降序

limits skip sort可以以任意顺序调用

```
db.emp.find({}).sort({sql:1});
```

指定查询出的字段

```
- 在查询时，可以在第二个参数的位置来设置查询结果，投影   1为显示投影  0为不显示
db.emp.find({},{ename:1,_id:0});
```

# Mongoose

因是java学习，此处暂略