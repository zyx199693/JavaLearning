package ThreadPool;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolPerformance {
    public static final int maxCount = 1000;

    public static void main(String args[]) throws InterruptedException {
//        ThreadPerformanceTest();

        ThreadPoolPerformanceTest();

        ThreadPerformanceTest();

//        ThreadPoolPerformanceTest();
    }

    private static void ThreadPoolPerformanceTest() throws InterruptedException {
        long stime = System.currentTimeMillis();
        ThreadPoolExecutor tp = new ThreadPoolExecutor(10, 10, 0, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>());
        for(int i = 0; i < maxCount; i++) {
            tp.execute(new PerformanceRunnable());
        }
        tp.shutdown();
        tp.awaitTermination(1, TimeUnit.SECONDS);
        long etime = System.currentTimeMillis();
        System.out.printf("线程池执行时长：%d毫秒", (etime - stime));
        System.out.println();
    }

    private static void ThreadPerformanceTest() {
        long stime = System.currentTimeMillis();
        for(int i = 0; i < maxCount; i++) {
            Thread td = new Thread(new PerformanceRunnable());
            td.start();
            try {
                td.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long etime = System.currentTimeMillis();
        System.out.printf("线程执行时长：%d毫秒", (etime - stime));
        System.out.println();
    }

    static class PerformanceRunnable implements Runnable {
        @Override
        public void run() {
            for(int i = 0; i < maxCount; i++) {
                long num = i * i + i;
            }
        }
    }
}
