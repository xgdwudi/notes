# 目录

前言

1. java NIO概述
1.1 阻塞 IO
1.2 非阻塞 IO(NIO)
1.3 NIO 概述
2. Channel
2.1 FileChannel
2.2 其他常用方法
2.3 Socket通道
2.3.1 ServerSocketChannel
2.3.2 SocketChannel
2.3.3 DatagramChannel
2.4 Scatter/Gather
2.5 以上代码参数详解
2.6 模板总结
3. Buffer
3.1 基本用法
3.2 三个重要属性
3.3 方法详解
3.4 其他方法
3.5 缓冲区
3.5.1 缓冲区分片
3.5.2 只读缓冲区
3.5.3 直接缓冲区
3.5.4 内存映射文件 I/O
4. Selector
4.1 常用方法
4.2 实战案例
4.3 代码参数详解
4.4 步骤总结
5. Pipe
5.1 常用方法
5.3 实战代码
6. FileLock
6.1 常用方法
6.2 代码实战
7. Java NIO（其他）
7.1 Path
7.2 Files
7.3 AsynchronousFileChannel
前言
在了解本课程的时候，要提前链接java的基础内容
可看博主的内容进行回顾

java零基础从入门到精通（全）
javaSE从入门到精通的十万字总结（一）
javaSE从入门到精通的十万字总结（完结）
本博文主要通过以下视频，进行笔记总结和汇总
【尚硅谷】2021新版Java NIO详细教程（一套带你掌握IO API/javanio）

其源码如下：

在了解完java的一些基础后，java nio的一些基本概念如下

## 1.1 java NIO概述

替代java io的一个操作
面向缓冲区也可以基于通道操作
更高效的进行文件的读写操作
1.1 阻塞 IO
读或者写数据的时候，会阻塞直到数据能够正常的读或者写入
在传统的方法中，服务器为客户端建立一个线程，这种模式带了如果线程增加，大量线程会造成服务器的开销
为了解决这种问题，采用了线程池，并设置线程池的上限，但超出线程池的上限的线程就会访问不上

## 1.2 非阻塞 IO(NIO)

非阻塞指的是 IO 事件本身不阻塞，是获取 IO 事件的 select()方法是需要阻塞等待的，区别是阻塞的 IO 会阻塞在 IO 操作上, NIO 阻塞在事件获取上,没有事件就没有 IO，select()阻塞的时候 IO 还没有发生,何谈 IO 的阻塞。本质是延迟io操作，真正发生io的时候才执行，而不是发生的时候在阻塞。用Selector负责去监听多个通道，注册感兴趣的特定 I/O 事件，之后系统进行通知

当有读或写等任何注册的事件发生时，可以从 Selector 中获得相应
的 SelectionKey，同时从 SelectionKey 中可以找到发生的事件和该事件所发生的具体的 SelectableChannel，以获得客户端发送过来的数据

IO	NIO
面向流	面向缓冲区
阻塞IO	非阻塞IO
无	选择器

## 1.3 NIO 概述

Java NIO 由以下几个核心部分组成，还有其他组件（pipe、filelock）

Channel（双向的，既可以用来进行读操作，又可以用来进行写操作）
主要有如下：
FileChannel（IO）、DatagramChannel（UDP ）、
SocketChannel （TCP中Server ）和 ServerSocketChannel（TCP中Client）
Buffer
主要有如下：
ByteBuffer, CharBuffer, DoubleBuffer, FloatBuffer,
IntBuffer, LongBuffer, ShortBuffer
Selector（处理多个 Channel）

2. Channel
可以进行读取和写入，或者进行读写操作，全双工
操作的数据源可以多种，比如文件、网络socket
Channel 用于在字节缓冲区和位于通道另一侧的实体（通常是一个文件或套接字）之间有效地传输数据
从通道读取数据到缓冲区，从缓冲区写入数据到通道
Java NIO 的通道类似流，但又有些不同：

既可以从通道中读取数据，又可以写数据到通道。但流的读写通常是单向的。
通道可以异步地读写。
通道中的数据总是要先读到一个 Buffer，或者总是要从一个 Buffer 中写入
主要是接口实现，不同操作系统不同接口实现，通过代码也可以看到其代码为接口



    public interface Channel extends Closeable {
    /**
     * Tells whether or not this channel is open.
     *
     * @return {@code true} if, and only if, this channel is open
     */
    public boolean isOpen();
    
    /**
     * Closes this channel.
     *
     * <p> After a channel is closed, any further attempt to invoke I/O
     * operations upon it will cause a {@link ClosedChannelException} to be
     * thrown.
     *
     * <p> If this channel is already closed then invoking this method has no
     * effect.
     *
     * <p> This method may be invoked at any time.  If some other thread has
     * already invoked it, however, then another invocation will block until
     * the first invocation is complete, after which it will return without
     * effect. </p>
     *
     * @throws  IOException  If an I/O error occurs
     */
    public void close() throws IOException;
    }


实现接口主要有以下几个常用类：

FileChannel 从文件中读写数据。
DatagramChannel 能通过 UDP 读写网络中的数据。
SocketChannel 能通过 TCP 读写网络中的数据。
ServerSocketChannel 可以监听新进来的 TCP 连接，像 Web 服务器那样。对每一个新进来的连接都会创建一个 SocketChannel。
2.1 FileChannel
主要是文件IO
最常用的一个类

以下是FileChannel常用的方法

方法	描述
int read(ByteBuffer dst)	从Channel中读取数据到 ByteBuffer
long read(ByteBuffer[] dsts)	将Channel中的数据“分散”到 ByteBuffer[]
int write(ByteBuffer src)	将ByteBuffer中的数据写入到Channel
long write(ByteBuffer[] srcs)	将ByteBuffer[]中的数据“聚集”到Channel
long position()	返回此通道的文件位置
FileChannel position(long p）	设置此通道的文件位置
long size()	返回此通道的文件的当前大小
FileChannel truncate(long s)	将此通道的文件截取为给定大小
void force(boolean metaData)	强制将所有对此通道的文件更新写入到存储设备中
Buffer 通常的操作

将数据写入缓冲区
调用 buffer.flip() 反转读写模式
从缓冲区读取数据
调用 buffer.clear() 或 buffer.compact() 清除缓冲区内容
部分步骤代码展示：

先打开文件，无法直接打开一个
FileChannel，需要通过使用一个 InputStream、OutputStream 或RandomAccessFile 来获取一个 FileChannel
//创建FileChannel
RandomAccessFile aFile = new RandomAccessFile("b://1.txt","rw");
FileChannel channel = aFile.getChannel();
1
2
3
创建Buffer
ByteBuffer buf = ByteBuffer.allocate(1024);
1
从 FileChannel 读取数据
read()方法返回的 int 值表示了有多少字节被读到了 Buffer 中。如果返回-1，表示到了文件末尾
 int bytesRead = channel.read(buf);
1
FileChannel.write()方法向 FileChannel 写数据，该方法的参数是一个 Buffer。在 while 循环中调用的。因为无法保证 write()方法一次能向 FileChannel 写入多少字节，因此需要重复调用 write()方法，直到 Buffer 中已经没有尚未写入通道的字节
读数据主要的代码思路步骤是：

创建一个FileChannel
创建一个数据缓冲区
读取数据到缓冲区中
判断数据是否有，如果有，则取出，判断的依据是获取到的数据是否为-1，取出的数据要先反转读写操作，之后如果数据缓冲区还有，则取出，最后清除数据缓冲区后在判断是否缓冲区还有数据。关闭FileChannel
完整代码展示

public class FileChannelDemo1 {
    //FileChannel读取数据到buffer中
    public static void main(String[] args) throws Exception {
        //创建FileChannel
        RandomAccessFile aFile = new RandomAccessFile("b://1.txt","rw");
        FileChannel channel = aFile.getChannel();

        //创建Buffer
        ByteBuffer buf = ByteBuffer.allocate(1024);
    
        //读取数据到buffer中
        int bytesRead = channel.read(buf);
        while(bytesRead != -1) {
            System.out.println("读取了："+bytesRead);
            buf.flip();
            while(buf.hasRemaining()) {
                System.out.println((char)buf.get());
            }
            buf.clear();
            bytesRead = channel.read(buf);
        }
        aFile.close();
        System.out.println("结束了");
    }
}

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
写数据主要代码思路是：

创建一个FileChannel
创建一个数据缓冲区
创建要写入的数据对象，以及清空以下缓冲区（防止出错）
要写入的数据写入到缓冲区中
缓冲区读写反转
判断缓冲区是否有数据，将数据一个一个写入到FileChannel
关闭FileChannel
完整代码展示：

//FileChanne写操作
public class FileChannelDemo2 {

    public static void main(String[] args) throws Exception {
        // 打开FileChannel
        RandomAccessFile aFile = new RandomAccessFile("b://1.txt","rw");
        FileChannel channel = aFile.getChannel();
    
        //创建buffer对象
        ByteBuffer buffer = ByteBuffer.allocate(1024);
    
        String newData = "manongyanjiuseng";
        buffer.clear();
    
        //写入内容
        buffer.put(newData.getBytes());
    
        buffer.flip();
    
        //FileChannel完成最终实现
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
    
        //关闭
        channel.close();
    }
}
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
2.2 其他常用方法
以下举例的方法有：
position 
size 
truncate
force
transferTo 和 transferFrom

1
2
3
4
5
6
7
position方法：
需要在 FileChannel 的某个特定位置进行数据的读/写操作。可以通过调用position()方法获取 FileChannel 的当前位置。也可以通过调用 position(long pos)方法设置 FileChannel 的当前位置

注意这样设置会造成两个后果：
位置如果设置在文件结束符之后，读取数据的文件结束标志返回-1，而且写入数据的时候前面会有间隙，导致文件空洞

long pos = channel.position();
channel.position(pos +123);
1
2
size 方法
返回该实例所关联文件的大小
truncate 方法
截取一个文件。截取文件时，文件将中指定长度，后面的部分将被删除
而且截取的数据长度是以字节截取

force 方法
尚未写入磁盘的数据强制写到磁盘上
transferTo 和 transferFrom 方法
进行通道之间的传输
注意一个To与From的区别，一个主动一个被动
以下是transferFrom 的完整代码：

//通道之间数据传输
public class FileChannelDemo3 {

    //transferFrom()
    public static void main(String[] args) throws Exception {
        // 创建两个fileChannel
        RandomAccessFile aFile = new RandomAccessFile("b://1.txt","rw");
        FileChannel fromChannel = aFile.getChannel();
    
        RandomAccessFile bFile = new RandomAccessFile("b://2.txt","rw");
        FileChannel toChannel = bFile.getChannel();
    
        //fromChannel 传输到 toChannel
        long position = 0;
        long size = fromChannel.size();
        toChannel.transferFrom(fromChannel,position,size);
    
        aFile.close();
        bFile.close();
        System.out.println("over!");
    }
}

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
以下是transferTo的完整代码：

//通道之间数据传输
public class FileChannelDemo4 {

    //transferTo()
    public static void main(String[] args) throws Exception {
        // 创建两个fileChannel
        RandomAccessFile aFile = new RandomAccessFile("b://1.txt","rw");
        FileChannel fromChannel = aFile.getChannel();
    
        RandomAccessFile bFile = new RandomAccessFile("b://2.txt","rw");
        FileChannel toChannel = bFile.getChannel();
    
        //fromChannel 传输到 toChannel
        long position = 0;
        long size = fromChannel.size();
        fromChannel.transferTo(0,size,toChannel);
    
        aFile.close();
        bFile.close();
        System.out.println("over!");
    }
}
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
2.3 Socket通道
所有的 socket 通道类(DatagramChannel、
SocketChannel 和 ServerSocketChannel)都继承了位于 java.nio.channels.spi 包中的 AbstractSelectableChannel。这意味着我们可以用一个 Selector 对象来执行socket 通道的就绪选择

非阻塞模式中，有更多的伸缩性和灵活性。使用socket，可以用一个线程或者多个线程就可以管理成百上千个socket，管理功能强大，而且没有性能损失，可以通过 Selector监听多个通道

DatagramChannel 和 SocketChannel 实现定义读和写功能的接口而ServerSocketChannel 不实现。ServerSocketChannel 负责监听传入的连接和创建新的 SocketChannel 对象，它本身从不传输数据。究其代码是因为它实现的接口比较少

socket通道可以被重复使用，socket不可以被重复使用

设置socket的通道模式为阻塞还是非阻塞，可以通过AbstractSelectableChannel.java 中实现的 configureBlocking()方法。传递参数值为 true 则设为阻塞模式，参数值为 false 值设为非阻塞模式

2.3.1 ServerSocketChannel
本身不传入数据，主要是为了监听，可以在非阻塞模式下运行
没有bind()绑定方法，通过对等的socket来绑定端口并进行监听
ServerSocketChannel的对象.socket().bind();
有accept()方法，返回SocketChannel 类型，对象为空则没有链接，反之则。可以在非阻塞下运行
其它 Socket 的 accept()方法会阻塞返回一个 Socket 对象。如果
ServerSocketChannel 以非阻塞模式被调用，当没有传入连接在等待时，ServerSocketChannel.accept( )会立即返回 null。正是这种检查连接而不阻塞的能力实现了可伸缩性并降低了复杂性。可选择性也因此得到实现。我们可以使用一个选择器实例来注册 ServerSocketChannel 对象以实现新连接到达时自动通知的功能

具体的代码思路步骤为：

打开ServerSocketChannel
绑定端口号
设置非阻塞模式configureBlocking(false);
监听连接，通过accept
将其buffer归为0指针rewind()，并且写入buffer
关闭 ServerSocketChannel
具体通过代码演示
如下代码

public class ServerSocketChannelDemo {

    public static void main(String[] args) throws Exception {
        //端口号
        int port = 8888;
    
        //buffer
        ByteBuffer buffer = ByteBuffer.wrap("hello atguigu".getBytes());
    
        //ServerSocketChannel
        ServerSocketChannel ssc = ServerSocketChannel.open();
        //绑定
        ssc.socket().bind(new InetSocketAddress(port));
    
        //设置非阻塞模式
        ssc.configureBlocking(false);
    
        //监听有新链接传入
        while(true) {
            System.out.println("Waiting for connections");
            SocketChannel sc = ssc.accept();
            if(sc == null) { //没有链接传入
                System.out.println("null");
                Thread.sleep(2000);
            } else {
                System.out.println("Incoming connection from: " + sc.socket().getRemoteSocketAddress());
                buffer.rewind(); //指针0
                sc.write(buffer);
                sc.close();
            }
        }
    }
}

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
通过浏览器点击127.0.0.1:8888，就会出现监听的提示

2.3.2 SocketChannel
用于连接到TCP的套接字
实现多路复用
面向缓冲区
主要的特征有：
（1）对于已经存在的 socket 不能创建 SocketChannel
（2）SocketChannel 中提供的 open 接口创建的 Channel 并没有进行网络级联，需要使用 connect 接口连接到指定地址
（3）未进行连接的 SocketChannle 执行 I/O 操作时，会抛出
NotYetConnectedException
（4）SocketChannel 支持两种 I/O 模式：阻塞式和非阻塞式
（5）SocketChannel 支持异步关闭。如果 SocketChannel 在一个线程上 read 阻塞，另一个线程对该 SocketChannel 调用 shutdownInput，则读阻塞的线程将返回-1 表示没有读取任何数据；如果 SocketChannel 在一个线程上 write 阻塞，另一个线程对该SocketChannel 调用 shutdownWrite，则写阻塞的线程将抛出AsynchronousCloseException
（6）
SocketChannel 支持设定参数
SO_SNDBUF 套接字发送缓冲区大小
SO_RCVBUF 套接字接收缓冲区大小
SO_KEEPALIVE 保活连接
O_REUSEADDR 复用地址
SO_LINGER 有数据传输时延缓关闭 Channel (只有在非阻塞模式下有用)
TCP_NODELAY 禁用 Nagle 算法
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
部分代码解释

创建 SocketChannel
SocketChannel socketChannel = SocketChannel.open(new 
InetSocketAddress("www.baidu.com", 80));

//或者

SocketChannel socketChanne2 = SocketChannel.open();
socketChanne2.connect(new InetSocketAddress("www.baidu.com", 80));
1
2
3
4
5
6
7
连接校验
socketChannel.isOpen(); // 测试 SocketChannel 是否为 open 状态
socketChannel.isConnected(); //测试 SocketChannel 是否已经被连接
socketChannel.isConnectionPending(); //测试 SocketChannel 是否正在进行
连接
socketChannel.finishConnect(); //校验正在进行套接字连接的 SocketChannel
是否已经完成连接
1
2
3
4
5
6
读写模式（有阻塞和非阻塞）
设置 SocketChannel 的读写模式。false 表示非阻塞，true 表示阻塞
socketChannel.configureBlocking(false);
1
读写
//阻塞读
SocketChannel socketChannel = SocketChannel.open(
 new InetSocketAddress("www.baidu.com", 80));
ByteBuffer byteBuffer = ByteBuffer.allocate(16);
socketChannel.read(byteBuffer);
socketChannel.close();
System.out.println("read over");


//非阻塞读
SocketChannel socketChannel = SocketChannel.open(
 new InetSocketAddress("www.baidu.com", 80));
socketChannel.configureBlocking(false);
ByteBuffer byteBuffer = ByteBuffer.allocate(16);
socketChannel.read(byteBuffer);
socketChannel.close();
System.out.println("read over");
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
完整代码如下

public class SocketChannelDemo {

    public static void main(String[] args) throws Exception {
        //创建SocketChannel
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("www.baidu.com", 80));

//        SocketChannel socketChanne2 = SocketChannel.open();
//        socketChanne2.connect(new InetSocketAddress("www.baidu.com", 80));

        //设置阻塞和非阻塞
        socketChannel.configureBlocking(false);
    
        //读操作
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        socketChannel.read(byteBuffer);
        socketChannel.close();
        System.out.println("read over");
    
    }

}

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
2.3.3 DatagramChannel
SocketChannel 对应 Socket，ServerSocketChannel 对应ServerSocket
DatagramChannel 对应的是 DatagramSocket 对象，DatagramChannel （如UDP/IP）是无连接的，可以发送单独的数据报给不同的目的地址。同样，可以接收来自任意地址的数据包。
具体涉及的参数代码：

打开 DatagramChannel

DatagramChannel server = DatagramChannel.open();
server.socket().bind(new InetSocketAddress(10086));
1
2
接收数据

ByteBuffer receiveBuffer = ByteBuffer.allocate(64);
receiveBuffer.clear();
SocketAddress receiveAddr = server.receive(receiveBuffer);
1
2
3
发送数据

DatagramChannel server = DatagramChannel.open();
ByteBuffer sendBuffer = ByteBuffer.wrap("client send".getBytes());
server.send(sendBuffer, new InetSocketAddress("127.0.0.1",10086));
1
2
3
连接
read()和 write()只有在 connect()后才能使用，否则会抛异常

client.connect(new InetSocketAddress("127.0.0.1",10086));
int readSize= client.read(sendBuffer);
server.write(sendBuffer);
1
2
3
具体发送的完整代码

//发送的实现
@Test
public void sendDatagram() throws Exception {
    //打开 DatagramChannel
    DatagramChannel sendChannel = DatagramChannel.open();
    InetSocketAddress sendAddress =
            new InetSocketAddress("127.0.0.1",9999);

    //发送
    while(true) {
        ByteBuffer buffer = ByteBuffer.wrap("发送数据".getBytes("UTF-8"));
        sendChannel.send(buffer,sendAddress);
        System.out.println("已经完成发送");
        Thread.sleep(1000);
    }
}
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
具体接收的完整代码
接收代码的时候
其缓冲区一开始要先清除，在将其接收
之后缓冲区的数据读写转换
在将其输出
注意此处的输出语句要用toString()

//接收的实现
@Test
public void receiveDatagram() throws Exception {
    //打开DatagramChannel
    DatagramChannel receiveChannel = DatagramChannel.open();
    InetSocketAddress receiveAddress = new InetSocketAddress(9999);
    //绑定
    receiveChannel.bind(receiveAddress);

    //buffer
    ByteBuffer receiveBuffer = ByteBuffer.allocate(1024);
    
    //接收
    while(true) {
        receiveBuffer.clear();
    
        SocketAddress socketAddress = receiveChannel.receive(receiveBuffer);
    
        receiveBuffer.flip();
    
        System.out.println(socketAddress.toString());
    
        System.out.println(Charset.forName("UTF-8").decode(receiveBuffer));
    }
}
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
2.4 Scatter/Gather
以下两种方式都是按照顺序读取或者写入

分散（scatter）从 Channel 中读取是指在读操作时将读取的数据写入多个 buffer中。

ByteBuffer header = ByteBuffer.allocate(128);
ByteBuffer body = ByteBuffer.allocate(1024);
ByteBuffer[] bufferArray = { header, body };
channel.read(bufferArray);
1
2
3
4
聚集（gather）写入 Channel 是指在写操作时将多个 buffer 的数据写入同一个Channel

ByteBuffer header = ByteBuffer.allocate(128);
ByteBuffer body = ByteBuffer.allocate(1024);
//write data into buffers
ByteBuffer[] bufferArray = { header, body };
channel.write(bufferArray);
1
2
3
4
5
2.5 以上代码参数详解
相信大家看完以上博客
可能对少许代码功能参数有些不懂
博主对其深究，看了其中一些代码源码参数以及功能如下

ByteBuffer wrap()其代码主要功能为将字节数组包装到缓冲区中
源码如下：

public static ByteBuffer wrap(byte[] array) {
    return wrap(array, 0, array.length);
}


//或者是设置其前后参数的源码

public static ByteBuffer wrap(byte[] array,int offset, int length)
    {
        try {
            return new HeapByteBuffer(array, offset, length, null);
        } catch (IllegalArgumentException x) {
            throw new IndexOutOfBoundsException();
        }
    }
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
在调用该代码的时候，如果是中文，需要加入ByteBuffer.wrap("发送数据".getBytes("UTF-8"));

绑定端口号，主要通过这个函数InetSocketAddress
这个函数中可以提供ip和端口号，也可以直接提供端口号绑定等

源码如下：

public InetSocketAddress(String hostname, int port) {
        checkHost(hostname);
        InetAddress addr = null;
        String host = null;
        try {
            addr = InetAddress.getByName(hostname);
        } catch(UnknownHostException e) {
            host = hostname;
        }
        holder = new InetSocketAddressHolder(host, addr, checkPort(port));
    }
1
2
3
4
5
6
7
8
9
10
11
发送数据的源码要和端口号绑定

源码如下：

public abstract int send(ByteBuffer src, SocketAddress target)throws IOException;
1
在调用的时候只需要将其buffer 和ip地址参数写入即可

创建缓冲区设置其字节数字
源码如下：

 public static ByteBuffer allocate(int capacity) {
        if (capacity < 0)
            throw createCapacityException(capacity);
        return new HeapByteBuffer(capacity, capacity, null);
    }
1
2
3
4
5
具体调用的例子可以通过如下ByteBuffer receiveBuffer = ByteBuffer.allocate(1024);

具体转换的读写的接口为flip()

源码如下

//子类源码是调用父类的源码
ByteBuffer flip() {
        super.flip();
        return this;
    }

//父类源码
public Buffer flip() {
        limit = position;
        position = 0;
        mark = -1;
        return this;
    }
1
2
3
4
5
6
7
8
9
10
11
12
13
2.6 模板总结
一般接收其缓冲区的数据的时候
都是固定的格式

清除缓冲区之后开始接收
读写转换
之后可判断数据是否还有在将其写入（读写反过来也同样）
输出的时候主要要不要代toString()
buffer.clear();

//写入内容
buffer.put(newData.getBytes());

buffer.flip();

//FileChannel完成最终实现
while (buffer.hasRemaining()) {
    channel.write(buffer);
}

1
2
3
4
5
6
7
8
9
10
11
12
或者是

readBuffer.clear();

connChannel.read(readBuffer);

readBuffer.flip();
System.out.println(Charset.forName("UTF-8").decode(readBuffer));
1
2
3
4
5
6
以上代码都是实战中的例子，例子的代码思路都是大同小异

3. Buffer
通道进行交互。数据是从通道读入缓冲区，从缓冲区写入到通道中
在 NIO 库中，所有数据都是用缓冲区处理的
3.1 基本用法
使用 Buffer 读写数据，四个步骤
（1）写入数据到 Buffer
（2）调用 flip()方法
（3）从 Buffer 中读取数据
（4）调用 clear()方法或者 compact()方法

读数据的完整例子

@Test
public void buffer01() throws Exception {
    //FileChannel
    RandomAccessFile aFile =
            new RandomAccessFile("b://1.txt","rw");
    FileChannel channel = aFile.getChannel();

    //创建buffer大小
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    
    //读
    int bytesRead = channel.read(buffer);
    
    while(bytesRead != -1) {
        //read模式
        buffer.flip();
    
        while(buffer.hasRemaining()) {
            System.out.println((char)buffer.get());
        }
        buffer.clear();
        bytesRead = channel.read(buffer);
    }
    
    aFile.close();
}
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
写数据的完整例子

//创建buffer
IntBuffer buffer = IntBuffer.allocate(8);

//buffer放
for (int i = 0; i < buffer.capacity(); i++) {
    int j = 2*(i+1);
    buffer.put(j);
}

//重置缓冲区
buffer.flip();

//获取
while(buffer.hasRemaining()) {
    int value = buffer.get();
    System.out.println(value+" ");
}

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
3.2 三个重要属性
Buffer还有三个属性：Capacity、Position、limit

capacity 内存块固定大小值
一旦 Buffer 满了，需要将其清空，才能写入

position
写入数据的时候，初始值为0，慢慢会往下移动，最大值为-1表示满了
读入数据的时候， position=2 时表示已开始读入了 3 个 byte。ByteBuffer.flip()切换到读模式时 position 会被重置为 0

limit
limit 表示可对 Buffer 最多写入或者读取多少个数据

3.3 方法详解
分配字节数据
要想获得一个 Buffer 对象首先要进行分配。 每一个 Buffer 类都有一个 allocate 方法
比如

ByteBuffer buf = ByteBuffer.allocate(48);
1
写数据的两种方式
（1）从 Channel 写到 Buffer。
（2）通过 Buffer 的 put()方法写到 Buffer 里。

int bytesRead = inChannel.read(buf); //read into buffer

buf.put(127);
1
2
3
读写模式转换 flip()
flip 方法将 Buffer 从写模式切换到读模式。调用 flip()方法会将 position 设回 0，并将 limit 设置成之前 position 的值

从 Buffer 中读取数据
有两种方式：
（1）从 Buffer 读取数据到 Channel。
（2）使用 get()方法从 Buffer 中读取数据

//read from buffer into channel.
int bytesWritten = inChannel.write(buf);

byte aByte = buf.get();
1
2
3
4
3.4 其他方法
rewind()将 position 设回 0

clear()与 compact()都是清空数据，但有所区别：
一旦读完了所有的数据，就需要清空缓冲区，让它可以再次被写入。有两种方式能清空缓冲区：调用 clear()或 compact()方法。clear()方法会清空整个缓冲区。compact()方法只会清除已经读过的数据。任何未读的数据都被移到缓冲区的起始处，新写入的数据将放到缓冲区未读数据的后面

mark()与 reset()一个标记一个回到标记点
通过调用 Buffer.mark()方法，可以标记 Buffer 中的一个特定 position。之后可以通过调用 Buffer.reset()方法恢复到这个 position

buffer.mark();
//call buffer.get() a couple of times, e.g. during parsing.
buffer.reset(); //set position back to mark
1
2
3
3.5 缓冲区
缓冲区可以分为四种类型
分别为缓冲区分片、只读缓冲区、直接缓冲区、内存映射文件 I/O

3.5.1 缓冲区分片
根据现有的缓冲区对象来创建一个子缓冲区，即在现有缓冲区上切出一片来作为一个新的缓冲区，但现有的缓冲区与创建的子缓冲区在底层数组层面上是数据共享的，也就是说，子缓冲区相当于是现有缓冲区的一个视图窗口。调用 slice()方法可以创建一个子缓冲区

完整代码：

//缓冲区分片
@Test
public void b01() {
    ByteBuffer buffer = ByteBuffer.allocate(10);

    for (int i = 0; i < buffer.capacity(); i++) {
        buffer.put((byte)i);
    }
    
    //创建子缓冲区
    buffer.position(3);
    buffer.limit(7);
    ByteBuffer slice = buffer.slice();
    
    //改变子缓冲区内容
    for (int i = 0; i <slice.capacity() ; i++) {
        byte b = slice.get(i);
        b *=10;
        slice.put(i,b);
    }
    
    buffer.position(0);
    buffer.limit(buffer.capacity());
    
    while(buffer.remaining()>0) {
        System.out.println(buffer.get());
    }
}

3.5.2 只读缓冲区
可以读取它们，但是不能向它们写入数据。可以通过调用缓冲
区的 asReadOnlyBuffer()方法
与原缓冲区共享数据，只不过它是只读的。
如果原缓冲区的内容发生了变化，只读缓冲区的内容也随之发生变化

完整代码

//只读缓冲区
@Test
public void b02() {
    ByteBuffer buffer = ByteBuffer.allocate(10);

    for (int i = 0; i < buffer.capacity(); i++) {
        buffer.put((byte)i);
    }
    
    //创建只读缓冲区
    ByteBuffer readonly = buffer.asReadOnlyBuffer();
    
    for (int i = 0; i < buffer.capacity(); i++) {
        byte b = buffer.get(i);
        b *=10;
        buffer.put(i,b);
    }
    
    readonly.position(0);
    readonly.limit(buffer.capacity());
    
    while (readonly.remaining()>0) {
        System.out.println(readonly.get());
    }
}

3.5.3 直接缓冲区
加快 I/O 速度
要分配直接缓冲区，需要调用 allocateDirect()方法，而不是 allocate()方法，使用方式与普通缓冲区并无区别

下面展示缓冲区的复制代码
完整代码

//直接缓冲区
@Test
public void b03() throws Exception {
    String infile = "b://1.txt";
    FileInputStream fin = new FileInputStream(infile);
    FileChannel finChannel = fin.getChannel();

    String outfile = "b://2.txt";
    FileOutputStream fout = new FileOutputStream(outfile);
    FileChannel foutChannel = fout.getChannel();
    
    //创建直接缓冲区
    ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
    
    while (true) {
        buffer.clear();
        int r = finChannel.read(buffer);
        if(r == -1) {
            break;
        }
        buffer.flip();
        foutChannel.write(buffer);
    }
}
3.5.4 内存映射文件 I/O
内存映射文件 I/O 是一种读和写文件数据的方法，它可以比常规的基于流或者基于通道的 I/O 快的多

static private final int start = 0;
static private final int size = 1024;

//内存映射文件io
 @Test
 public void b04() throws Exception {
     RandomAccessFile raf = new RandomAccessFile("b://1.txt", "rw");
     FileChannel fc = raf.getChannel();
     
     MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, start, size);
    
     mbb.put(0, (byte) 97);
     mbb.put(1023, (byte) 122);
     raf.close();
 }
4. Selector
通过一个Selector可以检查更多的通道
使用更少的线程来就可以来处理通道了， 相比使用多个线程，避免了线程上下文切换带来的开销
可选择通道：

不是所有的 Channel 都可以被 Selector 复用的。判断他是否继
承了一个抽象类 SelectableChannel。如果继承了SelectableChannel，则可以被复用，否则不能
一个通道可以被注册到多个选择器上，但对每个选择器而言只能被注册一次。通道和选择器之间的关系，使用注册的方式完成。SelectableChannel 可以被注册到Selector 对象上，在注册的时候，需要指定通道的哪些操作，是 Selector 感兴趣的
Channel 注册到 Selector：
Channel.register（Selector sel，int ops）一个通道注册到一个选择器时。
第一个参数，指定通道要注册的选择器。
第二个参数指定选择器需要查询的通道操作
供选择器查询的通道

可读 : SelectionKey.OP_READ
可写 : SelectionKey.OP_WRITE
连接 : SelectionKey.OP_CONNECT
接收 : SelectionKey.OP_ACCEPT
选择键：
（1）Channel 注册到后，并且一旦通道处于某种就绪的状态，就可以被选择器查询到。这个工作，使用选择器 Selector 的 select()方法完成。select 方法的作用，对感兴趣的通道操作，进行就绪状态的查询。
（2）Selector 可以不断的查询 Channel 中发生的操作的就绪状态。并且挑选感兴趣的操作就绪状态。一旦通道有操作的就绪状态达成，并且是 Selector 感兴趣的操作，就会被 Selector 选中，放入选择键集合中

4.1 常用方法
创建一个 Selector 对象：

// 1、获取 Selector 选择器
Selector selector = Selector.open();
1
2
注册 Channel 到 Selector：
与 Selector 一起使用时，Channel 必须处于非阻塞模式下

```
// 1、获取 Selector 选择器
Selector selector = Selector.open();
// 2、获取通道
ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
// 3.设置为非阻塞
serverSocketChannel.configureBlocking(false);
// 4、绑定连接
serverSocketChannel.bind(new InetSocketAddress(9999));
// 5、将通道注册到选择器上,并制定监听事件为：“接收”事件
serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
```


轮询查询就绪操作：
（1）通过 Selector 的 select（）方法，可以查询出已经就绪的通道操作，这些就绪的状态集合，包存在一个元素是 SelectionKey 对象的 Set 集合中。
（2）下面是 Selector 几个重载的查询 select()方法：

轮询查询就绪操作：
（1）通过 Selector 的 select（）方法，可以查询出已经就绪的通道操作，这些就绪的状态集合，包存在一个元素是 SelectionKey 对象的 Set 集合中。
（2）下面是 Selector 几个重载的查询 select()方法：

select():阻塞到至少有一个通道在你注册的事件上就绪了。
select(long timeout)：和 select()一样，但最长阻塞事件为 timeout 毫秒。
selectNow():非阻塞，只要有通道就绪就立刻返回。
select()方法返回的 int 值，表示有多少通道已经就绪，更准确的说，是自前一次 select方法以来到这一次 select 方法之间的时间段上，有多少通道变成就绪状态。

例如：首次调用 select()方法，如果有一个通道变成就绪状态，返回了 1，若再次调用select()方法，如果另一个通道就绪了，它会再次返回 1。如果对第一个就绪的channel 没有做任何操作，现在就有两个就绪的通道，但在每次 select()方法调用之间，只有一个通道就绪了。一旦调用 select()方法，并且返回值不为 0 时，在 Selector 中有一个 selectedKeys()方法，用来访问已选择键集合，迭代集合的每一个选择键元素，根据就绪操作的类型，完成对应的操作

Set selectedKeys = selector.selectedKeys();
Iterator keyIterator = selectedKeys.iterator();
while(keyIterator.hasNext()) {
 SelectionKey key = keyIterator.next();
 if(key.isAcceptable()) {
 // a connection was accepted by a ServerSocketChannel.
 } else if (key.isConnectable()) {
 // a connection was established with a remote server.
 } else if (key.isReadable()) {
 // a channel is ready for reading
 } else if (key.isWritable()) {
 // a channel is ready for writing
 }
 keyIterator.remove();
}

4.2 实战案例
服务端代码

//服务端代码
@Test
public void serverDemo() throws Exception {
    //1 获取服务端通道
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

    //2 切换非阻塞模式
    serverSocketChannel.configureBlocking(false);
    
    //3 创建buffer
    ByteBuffer serverByteBuffer = ByteBuffer.allocate(1024);
    
    //4 绑定端口号
    serverSocketChannel.bind(new InetSocketAddress(8080));
    
    //5 获取selector选择器
    Selector selector = Selector.open();
    
    //6 通道注册到选择器，进行监听
    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    
    //7 选择器进行轮询，进行后续操作
    while(selector.select()>0) {
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        //遍历
        Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();
        while(selectionKeyIterator.hasNext()) {
            //获取就绪操作
            SelectionKey next = selectionKeyIterator.next();
            //判断什么操作
            if(next.isAcceptable()) {
                //获取连接
                SocketChannel accept = serverSocketChannel.accept();
    
                //切换非阻塞模式
                accept.configureBlocking(false);
    
                //注册
                accept.register(selector,SelectionKey.OP_READ);
    
            } else if(next.isReadable()) {
                SocketChannel channel = (SocketChannel) next.channel();
    
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    
                //读取数据
                int length = 0;
                while((length = channel.read(byteBuffer))>0) {
                    byteBuffer.flip();
                    System.out.println(new String(byteBuffer.array(),0,length));
                    byteBuffer.clear();
                }
    
            }
    
            selectionKeyIterator.remove();
        }
    }
}
客户端代码

//客户端代码
@Test
public void clientDemo() throws Exception {
    //1 获取通道，绑定主机和端口号
    SocketChannel socketChannel =
            SocketChannel.open(new InetSocketAddress("127.0.0.1",8080));

    //2 切换到非阻塞模式
    socketChannel.configureBlocking(false);
    
    //3 创建buffer
    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    
    //4 写入buffer数据
    byteBuffer.put(new Date().toString().getBytes());
    
    //5 模式切换
    byteBuffer.flip();
    
    //6 写入通道
    socketChannel.write(byteBuffer);
    
    //7 关闭
    byteBuffer.clear();
}

public static void main(String[] args) throws IOException {
    //1 获取通道，绑定主机和端口号
    SocketChannel socketChannel =
            SocketChannel.open(new InetSocketAddress("127.0.0.1",8080));

    //2 切换到非阻塞模式
    socketChannel.configureBlocking(false);
    
    //3 创建buffer
    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    
    Scanner scanner = new Scanner(System.in);
    while(scanner.hasNext()) {
        String str = scanner.next();
    
        //4 写入buffer数据
        byteBuffer.put((new Date().toString()+"--->"+str).getBytes());
    
        //5 模式切换
        byteBuffer.flip();
    
        //6 写入通道
        socketChannel.write(byteBuffer);
    
        //7 关闭
        byteBuffer.clear();
    }

}
}


4.3 代码参数详解
查看其注册到selector中的源码

```java
  public final SelectionKey register(Selector sel, int ops)
        throws ClosedChannelException
    {
        return register(sel, ops, null);
    }

```

而且进行轮询的时候本身就是一个集合选择器了
查看其源码也是这样

public abstract Set<SelectionKey> selectedKeys();
1
遍历该集合的时候使用迭代器进行遍历

4.4 步骤总结
创建serverSocketChannel通道，监听端口，并且设置通道为非阻塞模式
创建selector选择器，将其channel注册到选择器上并且监听
调用selector的select方法，进行检测通道的就绪情况
调用selectkeys获取channel集合，便利该集合，判断其事件类型
5. Pipe
管道是 2 个线程之间的单向数据连接
一个 source 通道（读取）和一个sink 通道（写入）

5.1 常用方法
打开管道

Pipe pipe = Pipe.open();
1
写入管道
需要访问 sink 通道

Pipe.SinkChannel sinkChannel = pipe.sink();
1
读取数据
需要访问 source 通道

Pipe.SourceChannel sourceChannel = pipe.source();
1
5.3 实战代码
完整代码如下：

创建管道之后在创建sink管道
并且设置缓冲区将其写入
获取source管道并且读取数据
public class PipeDemo {

    public static void main(String[] args) throws IOException {
        //1 获取管道
        Pipe pipe = Pipe.open();
    
        //2 获取sink通道
        Pipe.SinkChannel sinkChannel = pipe.sink();
    
        //3 创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put("码农研究僧".getBytes());
        byteBuffer.flip();
    
        //4 写入数据
        sinkChannel.write(byteBuffer);
    
        //5 获取source通道
        Pipe.SourceChannel sourceChannel = pipe.source();
    
        //6 读取数据
        ByteBuffer byteBuffer2 = ByteBuffer.allocate(1024);
    
        //byteBuffer.flip();
    
        int length = sourceChannel.read(byteBuffer2);
    
        System.out.println(new String(byteBuffer2.array(),0,length));
    
        //7 关闭通道
        sourceChannel.close();
        sinkChannel.close();
    }

}

6. FileLock
文件锁是进程级别的，不是线程级别的

具体分为两大类：
**排它锁：**又叫独占锁。对文件加排它锁后，该进程可以对此文件进行读写，该进程独占此文件，其他进程不能读写此文件，直到该进程释放文件锁。

**共享锁：**某个进程对文件加共享锁，其他进程也可以访问此文件，但这些进程都只能读此文件，不能写。线程是安全的。只要还有一个进程持有共享锁，此文件就只能读，不能写

//创建 FileChannel 对象，文件锁只能通过 FileChannel 对象来使用
FileChannel fileChannel=new FileOutputStream("./1.txt").getChannel();
//对文件加锁
FileLock lock=fileChannel.lock();
//对此文件进行一些读写操作。
//.......
//释放锁
lock.release();
1
2
3
4
5
6
7
8
6.1 常用方法
lock是阻塞，trylock是非阻塞
获取文件锁的方法有如下：

lock() //对整个文件加锁，默认为排它锁。
lock(long position, long size, booean shared) //自定义加锁方式。
前 2 个参数指定要加锁的部分（可以只对此文件的部分内容加锁），第三个参数值指定是否是共享锁
tryLock() //对整个文件加锁，默认为排它锁。
tryLock(long position, long size, booean shared) //自定义加锁方式。
如果指定为共享锁，则其它进程可读此文件，所有进程均不能写此文件，如果某进程试图对此文件进行写操作，会抛出异常。
FileLock另外两个方法：

boolean isShared() //此文件锁是否是共享锁
boolean isValid() //此文件锁是否还有效
6.2 代码实战
path,StandardOpenOption.WRITE,StandardOpenOption.APPEND分别是路径，读写数据，追加数据

public class FileLockDemo1 {

    public static void main(String[] args) throws Exception {
        String input = "manongyanjiuseng";
        System.out.println("input:"+input);
    
        ByteBuffer buffer = ByteBuffer.wrap(input.getBytes());
    
        String filePath = "b//1.txt";
        Path path = Paths.get(filePath);
    
        FileChannel channel =
                FileChannel.open(path,
                        StandardOpenOption.WRITE,StandardOpenOption.APPEND);
        channel.position(channel.size()-1);
    
        //加锁
        FileLock lock = channel.lock(0L,Long.MAX_VALUE,true);
        System.out.println("是否共享锁："+lock.isShared());
    
        channel.write(buffer);
        channel.close();
    
        //读文件
        readFile(filePath);
    }
    
    private static void readFile(String filePath) throws Exception {
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String tr = bufferedReader.readLine();
        System.out.println("读取出内容：");
        while(tr != null) {
            System.out.println(" "+tr);
            tr = bufferedReader.readLine();
        }
        fileReader.close();
        bufferedReader.close();
    }
}

7. Java NIO（其他）
此处包括了path和files等

7.1 Path
一个路径可以指向一个文件或一个目录。路径可以是绝对路径，也可以是相对路径
使用 java.nio.file.Path 实例必须创建一个 Path 实例。可以使用 Paths 类(java.nio.file.Paths)中的静态方法 Paths.get()来创建路径实例
也是当前的绝对路径

Path path = Paths.get("b://1.txt");
1
也可以创建相对路径，使用 Paths.get(basePath, relativePath)方法创建一个相对路径

Path path = Paths.get("b://txt”，“1.txt");
1
使路径标准化。标准化意味着它将移除所有在路径字符串的中间的.和…代码，并解析路径字符串所引用的路径Path.normalize()
String originalPath = "b://projects//..//yygh-project";

Path path1 = Paths.get(originalPath);
System.out.println("path1 = " + path1);

Path path2 = path1.normalize();
System.out.println("path2 = " + path2);
1
2
3
4
5
6
7
7.2 Files
Files.createDirectory()根据 Path 实例创建一个新目录

Path path = Paths.get("b://1");
try {
    Path directory = Files.createDirectory(path);
} catch (IOException e) {
    e.printStackTrace();
}
1
2
3
4
5
6
复制，一个路径拷贝一个文件到另外一个目录
第三个参数可加可不加，Files.copy()方法的第三个参数。如果目标文件已经存在，这个参数指示 copy()方法覆盖现有的文件
Path path1 = Paths.get("b://1.txt");
Path path2 = Paths.get("b://2.txt");
try {
    Path copy = Files.copy(path1, path2, StandardCopyOption.REPLACE_EXISTING);
} catch (IOException e) {
    e.printStackTrace();
}
1
2
3
4
5
6
7
移动文件既可以移动到不同的目录，也可以在相同的操作中更改它的名称
Files.move()的第三个参数。这个参数告诉 Files.move()方法来覆盖目标路径上的任何现有文件
Path sourcePath = Paths.get("b://1.txt");
Path destinationPath = Paths.get("b://2.txt");

try {
    Files.move(sourcePath, destinationPath,
            StandardCopyOption.REPLACE_EXISTING);
} catch (IOException e) {
    //移动文件失败
    e.printStackTrace();
}
1
2
3
4
5
6
7
8
9
10
删除一个文件或者目录
Path path = Paths.get("b://1.txt");
try {
    Files.delete(path);
} catch (IOException e) {
    // 删除文件失败
    e.printStackTrace();
}

1
2
3
4
5
6
7
8
7.3 AsynchronousFileChannel
异步地将数据写入文件

静态方法 open()创建
第二个参数是一个或多个打开选项，它告诉 AsynchronousFileChannel 在文件上执行什么操作。在本例中，我们使用StandardOpenOption.READ 选项，表示该文件将被打开阅读
读取数据
第一种方式是调用返回Future 的 read()方法
第二种方法是调用 read()方法，该方法将一个 CompletionHandler 作为参数
（1）创建了一个 AsynchronousFileChannel
（2）创建一个 ByteBuffer，它被传递给 read()方法作为参数，以及一个 0 的位置。
（3）在调用 read()之后，循环，直到返回的 isDone()方法返回 true。
（4）读取操作完成后，数据读取到 ByteBuffer 中，然后打印到 System.out 中

@Test
public void readAsyncFileChannelFuture() throws Exception {
    //1 创建AsynchronousFileChannel
    Path path = Paths.get("b://1.txt");
    AsynchronousFileChannel fileChannel =
            AsynchronousFileChannel.open(path, StandardOpenOption.READ);

    //2 创建Buffer
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    
    //3 调用channel的read方法得到Future
    Future<Integer> future = fileChannel.read(buffer, 0);
    
    //4 判断是否完成 isDone,返回true
    while(!future.isDone());
    
    //5 读取数据到buffer里面
    buffer.flip();
//        while(buffer.remaining()>0) {
//            System.out.println(buffer.get());
//        }
    byte[] data = new byte[buffer.limit()];
    buffer.get(data);
    System.out.println(new String(data));
    buffer.clear();

}
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
（1）读取操作完成，将调用 CompletionHandler 的 completed()方法。
（2）对于 completed()方法的参数传递一个整数，它告诉我们读取了多少字节，以及传递给 read()方法的“附件”。“附件”是 read()方法的第三个参数。在本代码中，它是 ByteBuffer，数据也被读取。
（3）如果读取操作失败，则将调用 CompletionHandler 的 failed()方法

@Test
public void readAsyncFileChannelComplate() throws Exception {
    //1 创建AsynchronousFileChannel
    Path path = Paths.get("b://1.txt");
    AsynchronousFileChannel fileChannel =
            AsynchronousFileChannel.open(path, StandardOpenOption.READ);

    //2 创建Buffer
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    
    //3 调用channel的read方法得到Future
    fileChannel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
        @Override
        public void completed(Integer result, ByteBuffer attachment) {
            System.out.println("result: "+result);
    
            attachment.flip();
            byte[] data = new byte[attachment.limit()];
            attachment.get(data);
            System.out.println(new String(data));
            attachment.clear();
        }
    
        @Override
        public void failed(Throwable exc, ByteBuffer attachment) {
    
        }
    });
}
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
通过 Future 写数据

@Test
public void writeAsyncFileFuture() throws IOException {
    //1 创建AsynchronousFileChannel
    Path path = Paths.get("b://1.txt");
    AsynchronousFileChannel fileChannel =
            AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);

    //2 创建Buffer
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    
    //3 write方法
    buffer.put("atguigu ".getBytes());
    buffer.flip();
    Future<Integer> future = fileChannel.write(buffer, 0);
    
    while(!future.isDone());
    
    buffer.clear();
    System.out.println("write over");
}
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
通过 CompletionHandler 写数据

@Test
public void writeAsyncFileComplate() throws IOException {
    //1 创建AsynchronousFileChannel
    Path path = Paths.get("b://1.txt");
    AsynchronousFileChannel fileChannel =
            AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);

    //2 创建Buffer
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    
    //3 write方法
    buffer.put("atguigujavajava".getBytes());
    buffer.flip();
    
    fileChannel.write(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
        @Override
        public void completed(Integer result, ByteBuffer attachment) {
            System.out.println("bytes written: " + result);
        }
    
        @Override
        public void failed(Throwable exc, ByteBuffer attachment) {
    
        }
    });
    
    System.out.println("write over");
}
