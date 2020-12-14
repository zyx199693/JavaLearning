package Concurrent;

import java.util.concurrent.ThreadPoolExecutor;

public class ThreadLocalExample {
    public static class MyRunnable implements Runnable {
        private ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>() {
            @Override
            protected Integer initialValue() {
                return 100;
            }
        };

        @Override
        public void run() {
//            threadLocal.set((int) (Math.random() * 100D));

            try{
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(threadLocal.get());
        }

        public static void main(String args[] ){
            MyRunnable sharedRunnableInstance = new MyRunnable();

            Thread thread1 = new Thread(sharedRunnableInstance);
            Thread thread2 = new Thread(sharedRunnableInstance);

            thread1.start();
            thread2.start();

//            thread1.join();
//            thread2.join();
        }
    }
}
