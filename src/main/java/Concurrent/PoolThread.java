package Concurrent;

import java.util.concurrent.BlockingQueue;

/**
 * 线程池中用于执行任务的子线程
 */
public class PoolThread extends Thread{
    private BlockingQueue<Runnable> taskQueue = null;
    private boolean isStop = false;

    public PoolThread(BlockingQueue<Runnable> queue) {
        this.taskQueue = queue;
    }

    public void run() {
        while(!isStop) {
            try {
                Runnable runnable = taskQueue.take();
                runnable.run();
            } catch (Exception e) {
                //写日志或者报告异常
                //但线程池保持运行
            }
        }
    }

    public synchronized void toStop() {
        isStop = true;
        this.interrupt();   //打断线程池中线程的dequeue调用
    }

    public synchronized boolean isStopped() {
        return isStop;
    }
}
