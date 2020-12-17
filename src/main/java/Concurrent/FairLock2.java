package Concurrent;

import java.util.ArrayList;
import java.util.List;

//Fair lock implementation with nest monitor lockout problem
public class FairLock2 {
    private boolean isLocked = false;
    private Thread lockingThread = null;
    private List<QueueObject> waitingThreads = new ArrayList();

    public void lock() throws InterruptedException {
        QueueObject queueObject = new QueueObject();

        synchronized (this) {
            waitingThreads.add(queueObject);

            while(isLocked || waitingThreads.get(0) != queueObject) {
                synchronized (queueObject) {
                    try {
                        queueObject.wait();
                    } catch (InterruptedException e) {
                        waitingThreads.remove(queueObject);
                        e.printStackTrace();
                    }
                }
            }
            waitingThreads.remove(queueObject);
            isLocked = true;
            lockingThread = Thread.currentThread();
        }
    }

    public synchronized void unlock() {
        if(this.lockingThread != Thread.currentThread()) {
            throw new IllegalMonitorStateException(
                    "Calling thread has not locked this lock"
            );
        }
        isLocked = false;
        lockingThread = null;
        if(waitingThreads.size() > 0) {
            QueueObject queueObject = waitingThreads.get(0);
            synchronized (queueObject) {
                queueObject.notify();
            }
        }
    }
}
