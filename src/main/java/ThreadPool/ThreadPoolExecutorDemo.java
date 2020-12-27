package ThreadPool;

import Concurrent.MyThread;

import java.util.Date;
import java.util.concurrent.*;

public class ThreadPoolExecutorDemo {
    public static class MyRunnable implements Runnable {
        private String command;

        public MyRunnable(String s) {
            this.command = s;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " Start. Time = " + new Date());
            processCommand();
            System.out.println(Thread.currentThread().getName() + " End. Time = " + new Date());

        }

        private void processCommand() {
            try {
                Thread.sleep(3000);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "MyRunnable{" +
                    "command='" + command + '\'' +
                    '}';
        }
    }

    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 10;
    private static final int QUEUE_CAPACITY = 100;
    private static final Long KEEP_ALIVE_TIME = 1L;

    public static void main(String[] args) {
        //通过使用ThreadPoolExecutor构造函数自定义创建线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                new ThreadPoolExecutor.CallerRunsPolicy());

        for(int i = 0; i < 6; i++) {
            //创建WorkerThread对象（WorkerThread类实现了Runnable接口）
            Runnable worker = new MyRunnable("" + i);
            executor.execute(worker);
        }

        executor.shutdown();
        while(!executor.isTerminated()) {

        }
        System.out.println("Finished all threads");
    }
}
