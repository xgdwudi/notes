# IO

IO是指Input/Output，即输入和输出。以内存为中心：

- Input指从外部读入数据到内存，例如，把文件从磁盘读取到内存，从网络读取数据到内存等等。

- Output指把数据从内存输出到外部，例如，把数据从内存写入到文件，把数据从内存输出到网络等等。

IO流是一种顺序读写数据的模式，它的特点是单向流动。数据类似自来水一样在水管中流动，所以我们把它称为IO流。
![图片](https://static.bookstack.cn/projects/liaoxuefeng-java/1aa58c9136d3eeb343ab38fdf3af1a1a.jpeg)

## InputStream / OutputStream

IO流以byte（字节）为最小单位，因此也称为字节流。例如，我们要从磁盘读入一个文件，包含6个字符，就相当于读入了6个字节的数据：

> 在Java中，InputStream代表输入字节流，OuputStream代表输出字节流，这是最基本的两种IO流。

## Reader / Writer

Java提供了Reader和Writer表示字符流，字符流传输的最小数据单位是char。

## 同步和异步

同步IO是指，读写IO时代码必须等待数据返回后才继续执行后续代码，它的优点是代码编写简单，缺点是CPU执行效率低。

而异步IO是指，读写IO时仅发出请求，然后立刻执行后续代码，它的优点是CPU执行效率高，缺点是代码编写复杂。

> Java标准库的包java.io提供了同步IO，而java.nio则是异步IO。上面我们讨论的InputStream、OutputStream、Reader和Writer都是同步IO的抽象类，对应的具体实现类，以文件为例，有FileInputStream、FileOutputStream、FileReader和FileWriter。

# File 对象

https://www.bookstack.cn/read/liaoxuefeng-java/babd27c3a49cfe54.md

# InputStream

InputStream就是Java标准库提供的最基本的输入流。它位于java.io这个包里。java.io包提供了所有同步IO的功能。

https://www.bookstack.cn/read/liaoxuefeng-java/738755d27f4b7380.md

# filter模式

https://www.bookstack.cn/read/liaoxuefeng-java/f56aa3f63d7528ab.md

# 操作Zip

ZipInputStream可以读取zip格式的流，ZipOutputStream可以把多份数据写入zip包；

配合FileInputStream和FileOutputStream就可以读写zip文件。

https://www.bookstack.cn/read/liaoxuefeng-java/21583ae46ec82403.md

# 读取classpath资源

把资源存储在classpath中可以避免文件路径依赖；

Class对象的getResourceAsStream()可以从classpath中读取指定资源；

根据classpath读取资源时，需要检查返回的InputStream是否为null。

# 序列化

序列化是指把一个Java对象变成二进制内容，本质上就是一个byte[]数组。

https://www.bookstack.cn/read/liaoxuefeng-java/0feb9ad7ce69141a.md

我们来看看如何把一个Java对象序列化。

一个Java对象要能序列化，必须实现一个特殊的java.io.Serializable接口

Serializable接口没有定义任何方法，它是一个空接口。我们把这样的空接口称为“标记接口”（Marker Interface），实现了标记接口的类仅仅是给自身贴了个“标记”，并没有增加任何方法。

可序列化的Java对象必须实现java.io.Serializable接口，类似Serializable这样的空接口被称为“标记接口”（Marker Interface）；

反序列化时不调用构造方法，可设置serialVersionUID作为版本号（非必需）；

Java的序列化机制仅适用于Java，如果需要与其它语言交换数据，必须使用通用的序列化方法，例如JSON。

# Reader

https://www.bookstack.cn/read/liaoxuefeng-java/a16feb4b4fbf188b.md

# Writer

https://www.bookstack.cn/read/liaoxuefeng-java/6f305d5ef136e6a6.md