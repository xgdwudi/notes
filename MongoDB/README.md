---
title: mongodb
date: 2022/01/05 20:33:25  
tags:
- database
- mongodb
- nosql
categories: mongodb数据库
thumbnail: https://gitee.com/xu_guo_dong/images/raw/master/img/image-20220104140851242.pngg
cover: https://gitee.com/xu_guo_dong/images/raw/master/img/image-20220104140851242.png
---



# mongodb简介

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
- 