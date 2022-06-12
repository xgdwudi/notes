# Spring框架（源码高级篇）

## Bean的创建的生命周期

UserService.class  ->无参构造->对象->依赖注入-> 初始化前(@PostConstruct)-> 初始化（afterPropertiesSet） -->初始化后-->放入Map(单例池)-->Bean对象

