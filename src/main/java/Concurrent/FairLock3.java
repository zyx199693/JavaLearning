package Concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

//Fair Lock implementation with slipped conditions problem
public class FairLock3 {
    private boolean isLocked = false;
    private Thread lockingThread = null;
    private List<QueueObject> waitingThreads = new ArrayList<QueueObject>();

    public void lock() throws InterruptedException {
        QueueObject queueObject = new QueueObject();
        synchronized (this) {
            waitingThreads.add(queueObject);
        }
        boolean mustwait = true;
        while(mustwait) {
            synchronized (this) {
                mustwait = isLocked || waitingThreads.get(0) != queueObject;
            }
            synchronized (queueObject) {
                if(mustwait) {
                    try {
                        queueObject.wait();
                    } catch (InterruptedException e) {
                        waitingThreads.remove(queueObject);
                        throw  e;
                    }
                }
            }
        }
        synchronized (this) {
            waitingThreads.remove(queueObject);
            isLocked = true;
            lockingThread = Thread.currentThread();
        }
    }
}
