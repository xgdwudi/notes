import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @version 1.0
 * 注意：本内容仅限于西安城市发展资源信息有限公司内部传阅，禁止外泄以及用于其他的商业目
 * @ClassName Threads
 * @Description TODO
 * @Aurhor xu
 * @Ddte 2021/2/25 9:30
 **/
public class Threads {
    public static void main(String[] args) throws InterruptedException {
//        Thread myThread = new MyThread();
//        Thread myThread = new MyThread();
//        myThread.start();
//        Thread thread = new Thread(() -> {
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("adds");
//        });
//        Thread myThread = new MyThread();
//        Thread myThreads = new MyThreads();
//        myThread.start();
//        myThreads.start();
//        myThread.join();
//        myThreads.join();
//        System.out.println(ADDS.count);
//
//        for (int i = 0; i < 100; i++) {
//
//        }

//        ExecutorService executorService =new ThreadPoolExecutor(4, 4,
//                0L, TimeUnit.MILLISECONDS,
//                new LinkedBlockingQueue<Runnable>())
//        for (int i = 0; i < 5; i++) {
//            executorService.submit(new MyThread());
//        }
//        executorService.shutdown();
//        System.out.println(ADDS.atomicInteger.get());
        System.out.println(asdasd());
    }
    public static int asdasd(){
        try {
//            int i=1/0;
            return 6;
        }catch (Exception e){
            return 1;
        }finally {
            return 2;
        }
    }
}
class ADDS{
    public static final Object lock = new Object();
    public static int count  = 0;
    public static AtomicInteger atomicInteger = new AtomicInteger();
}
class MyThread extends Thread{

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//            for (int i = 0; i < 100; i++) {
//                synchronized (ADDS.lock) {
//                 System.out.println(ADDS.atomicInteger.incrementAndGet());
//            }
//        }
    }
}
class MyThreads extends Thread{

    @Override
    public void run() {
            for (int i = 0; i < 100; i++) {
                System.out.println(ADDS.atomicInteger.incrementAndGet());
//                synchronized (ADDS.lock){
//                ADDS.count--;
//            }
           }
    }
}
class MyThread02 implements Runnable{

    @Override
    public void run() {
        for (int i = 0; i < 100000; i++) {
           ADDS.count--;
        }
    }
}
