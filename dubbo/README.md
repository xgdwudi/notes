---
title: dubbo
date: 2022/01/25 18:12:25  
tags:
- 分布式
- java
- dubbo
categories: alibaba dubbo
thumbnail: https://tse1-mm.cn.bing.net/th/id/R-C.f6b063fc11b68f3915ca57e4cbaae8ff?rik=AShoWUQ8eSX%2fag&riu=http%3a%2f%2fimg.blog.csdn.net%2f20170326202443875%3fwatermark%2f2%2ftext%2faHR0cDovL2Jsb2cuY3Nkbi5uZXQvSmF2YVdlYlJvb2tpZQ%3d%3d%2ffont%2f5a6L5L2T%2ffontsize%2f400%2ffill%2fI0JBQkFCMA%3d%3d%2fdissolve%2f70%2fgravity%2fCenter&ehk=dDplFvAfoCZ9Xv%2bRBr6MW4KXTYIBMRU5COiunAJ9b04%3d&risl=&pid=ImgRaw&r=0
cover: https://tse1-mm.cn.bing.net/th/id/R-C.f6b063fc11b68f3915ca57e4cbaae8ff?rik=AShoWUQ8eSX%2fag&riu=http%3a%2f%2fimg.blog.csdn.net%2f20170326202443875%3fwatermark%2f2%2ftext%2faHR0cDovL2Jsb2cuY3Nkbi5uZXQvSmF2YVdlYlJvb2tpZQ%3d%3d%2ffont%2f5a6L5L2T%2ffontsize%2f400%2ffill%2fI0JBQkFCMA%3d%3d%2fdissolve%2f70%2fgravity%2fCenter&ehk=dDplFvAfoCZ9Xv%2bRBr6MW4KXTYIBMRU5COiunAJ9b04%3d&risl=&pid=ImgRaw&r=0
---



# 概述

## Dubbo概述

·Dubbo是阿里巴巴公司开源的一个高性能、轻量级的 Java RPC框架。

- Dubbo是阿里巴巴公司开源的一个高性能、轻量级的 Java rPc框架。
- 致力于提供高性能和透明化的RPC远程服各调用方案，以及SOA服务治理方案。
- 官网http://dubbo.apache.org

## Dubbo架构

![image-20220123142624585](https://raw.githubusercontent.com/xgdwudi/images/master/img/image-20220123142624585.png)

节点角色说明：

- Provider：暴露服务的服务提供方
- Container：服务运行容器
- Consumer：调用远程服务的服务消费方
- Registry：服务注册与发现的注册中心
- Monitor：统计服务的调用次数和调用时间的监控中心

# Dubbo快速安装

## Zookeeper安装

本此采用虚拟机docker安装（暂不做笔记）

## Dubbo快速入门

实现步骤：

①创建服务提供者 Provider模块

②创建服务消费者 Consumer模块

③在服务提供者模块编写 UserServicelmpl提供服务

④在服务消费者中的 UserController远程调用User Servicelmpl提供的服务

⑤分别启动两个服务，测试

**sevice模块pom文件**

**暂时先放着吧**