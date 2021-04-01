import java.util.concurrent.*;

/**
 *
 */
public class ThreadPool {
    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(4, 6, 1L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 9; i++) {
                executorService.execute(()->{
                    System.out.println(Thread.currentThread().getName() + "办理业务");
                });
        }
        executorService.shutdown();
    }
}
