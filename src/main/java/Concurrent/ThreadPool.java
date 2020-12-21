package Concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
//import java.util.concurrent.BlockingQueue;


/**
 * 线程池的简单实现
 */
public class ThreadPool {
    private BlockingQueue taskQueue = null;
    private List<PoolThread> threads = new ArrayList<>();
    private boolean isStopped = false;

    public ThreadPool(int noOfThreads, int maxNoofTasks) {
        taskQueue = new BlockingQueue(maxNoofTasks);
        for(int i = 0; i < noOfThreads; i++) {
//            threads.add(new PoolThread(taskQueue));
        }
        for(PoolThread thread : threads){
            thread.start();
        }
    }

    public synchronized void execute(Runnable task) throws InterruptedException {
        if(this.isStopped) throw
            new IllegalStateException("ThreadPool is stopped");
        this.taskQueue.enqueue(task);
    }

    public synchronized boolean stop() {
        this.isStopped = true;
        for(PoolThread thread : threads) {
            thread.toStop();
        }
        return true;
    }

    private AtomicBoolean locked = new AtomicBoolean(false);

    public boolean lock() {
        return locked.compareAndSet(false, true);
    }
}
