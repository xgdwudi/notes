# 概述

![image-20210922210615921](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922210615921.png)

![image-20210922210649338](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922210649338.png)

![image-20210922210719865](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922210719865.png)

![image-20210922210750981](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922210750981.png)

# channel

![image-20210922211343817](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922211343817.png)

![image-20210922211435879](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922211435879.png)

![image-20210922211506037](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922211506037.png)

![image-20210922211556597](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922211556597.png)

![image-20210922211614613](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922211614613.png)

![image-20210922211737640](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922211737640.png)

```java
 public static void main(String[] args) throws IOException {
        //创建 fileChannel
        RandomAccessFile aFile =
                new RandomAccessFile("D:\\照片\\1.txt","rw");
        FileChannel channel = aFile.getChannel();
        //创建Buffer
        ByteBuffer buf = ByteBuffer.allocate(1024);
        // 读取数据到buffer
        int read = channel.read(buf);
        while (read!=-1){
            System.out.println("读取了："+read);
            buf.flip();
            while (buf.hasRemaining()){
                System.out.println((char) buf.get());
            }
            buf.clear();
            read = channel.read(buf);
        }
        aFile.close();
        System.out.println("结束了");
    }
```

![image-20210922213021477](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922213021477.png)

![image-20210922213054092](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922213054092.png)

![image-20210922213212462](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922213212462.png)

```java
  public static void main(String[] args) throws IOException {
        RandomAccessFile aFile = new RandomAccessFile("D:\\照片\\1.txt", "rw");
        FileChannel channel = aFile.getChannel();
        ByteBuffer buf = ByteBuffer.allocate(1024);
        buf.clear();
        String  newData = "你好啊";
        buf.put(newData.getBytes());
        buf.flip();
        while (buf.hasRemaining()){
            channel.write(buf);
        }
        channel.close();
    }
```

![image-20210922213957379](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922213957379.png)

![image-20210922214045618](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922214045618.png)

![image-20210922214058483](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922214058483.png)

![image-20210922214126784](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922214126784.png)

![image-20210922214215401](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922214215401.png)

```jade
  public static void main(String[] args) throws IOException {
        RandomAccessFile from = new RandomAccessFile("D:\\照片\\1.txt", "rw");
        FileChannel channelFrom = from.getChannel();

        RandomAccessFile to = new RandomAccessFile("D:\\照片\\2.txt", "rw");
        FileChannel channelTo = to.getChannel();

//         from 传输到to
        long position = 0;
        long size = channelFrom.size();
        channelTo.transferFrom(channelFrom, position, size);

        from.close();
        to.close();
    }
```

## socket 

![image-20210922215027571](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922215027571.png)

![image-20210922215246918](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922215246918.png)

![image-20210922215437635](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922215437635.png)

![image-20210922215458863](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922215458863.png)

![image-20210922215533996](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922215533996.png)

### ServerSocketChannel

![image-20210922215712802](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922215712802.png)

![image-20210922215804780](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922215804780.png)

![image-20210922220908356](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922220908356.png)

![image-20210922220922191](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922220922191.png)

![image-20210922221032705](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922221032705.png)

```java
public static void main(String[] args) throws IOException, InterruptedException {
        // 端口号设置
        int port = 8888;
        
        // buffer
        ByteBuffer buf = ByteBuffer.wrap("hello,nihao".getBytes());

//        serverScoketChannel
        ServerSocketChannel open = ServerSocketChannel.open();
//        绑定
        open.socket().bind(new InetSocketAddress(port));
        //设置非阻塞
        open.configureBlocking(false);
//        监听是否有新的链接传入
        while (true){
            System.out.println("waiting");
            SocketChannel accept = open.accept();
            if(accept==null){
                System.out.println("没有link传入。。。");
                Thread.sleep(1000);
            }else {
                System.out.println("inconing connnection from :"+ accept.socket().getRemoteSocketAddress());
                buf.rewind();
                accept.write(buf);
                accept.close();
            }
        }
    }
```



### SocketChannel

![image-20210922221136108](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922221136108.png)

![image-20210922221157534](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922221157534.png)

![image-20210922221237324](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922221237324.png)

![image-20210922221703167](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922221703167.png)

![image-20210922221710499](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922221710499.png)

![image-20210922221738293](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922221738293.png)

![image-20210922221824264](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922221824264.png)

```java
    public static void main(String[] args) throws IOException {
        SocketChannel open = SocketChannel.open(new InetSocketAddress("www.baidu.com", 80));
//        SocketChannel open1 = SocketChannel.open();
//        open1.connect(new InetSocketAddress("www.baidu.com", 80));
//         设置阻塞和非阻塞
        open.configureBlocking(false);

//         读操作
        ByteBuffer allocate = ByteBuffer.allocate(1024);
        open.read(allocate);
        open.close();
        System.out.println("read over");
    }
```

DatagramChannel

![image-20210922222148275](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922222148275.png)

![image-20210922222316469](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922222316469.png)

![image-20210922222327948](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922222327948.png)

![image-20210922222338776](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922222338776.png)

![image-20210922222404269](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922222404269.png)

![image-20210922222414138](https://gitee.com/xu_guo_dong/images/raw/master/img/image-20210922222414138.png)

```java
   //    发送的实现
    @Test
    public void sendDatagram() throws IOException, InterruptedException {
//        打开DatagramChannel
        DatagramChannel open = DatagramChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 9999);
        while (true) {
            ByteBuffer wrap = ByteBuffer.wrap("发哦送asdasfas".getBytes("UTF-8"));
            open.send(wrap, inetSocketAddress);
            System.out.println("完成发送。。。");
            Thread.sleep(3000);
        }
    }

    //    接受
    @Test
    public void receiveDatagram() throws IOException {
        DatagramChannel receiveDatagram = DatagramChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 9999);
//        绑定
        receiveDatagram.bind(inetSocketAddress);
        ByteBuffer allocate = ByteBuffer.allocate(1024);

        while (true) {
            allocate.clear();
            SocketAddress receive = receiveDatagram.receive(allocate);
            allocate.flip();
            System.out.println(receive.toString());
            System.out.println(Charset.forName("UTF-8").decode(allocate));

        }
    }

    //    链接 read 和 write
    @Test
    public void testConnect() throws IOException {
        DatagramChannel receiveDatagram = DatagramChannel.open();
        receiveDatagram.bind(new InetSocketAddress(9999));
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 9999);

//        链接
        receiveDatagram.connect(inetSocketAddress);
        ByteBuffer wrap = ByteBuffer.wrap("发哦vhgvhvhjvjgcfghfghf".getBytes("UTF-8"));
        receiveDatagram.write(wrap);

        ByteBuffer allocate = ByteBuffer.allocate(1024);

        while (true){
            allocate.clear();
            receiveDatagram.read(allocate);
            allocate.flip();
            System.out.println(Charset.forName("UTF-8").decode(allocate));
        }
    }
```

https://www.bilibili.com/video/BV1E64y1h7Z4?p=10&spm_id_from=pageDriver