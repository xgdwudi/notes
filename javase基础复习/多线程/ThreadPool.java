import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
public class ThreadPool {
    public static void main(String[] args) {
        AtomicInteger integer = new AtomicInteger(0);
        ExecutorService executorService = new ThreadPoolExecutor(100, 300, 1L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 100; i++) {
                executorService.execute(()->{
                    for (int j = 0; j < 10; j++) {
                        integer.incrementAndGet();
                    }
                });
        }
        executorService.shutdown();
        System.out.println(integer.get());
    }
}
