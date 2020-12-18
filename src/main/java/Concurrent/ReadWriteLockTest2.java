package Concurrent;

import java.util.HashMap;
import java.util.Map;

/**
 * 可重入的读写锁
 */
public class ReadWriteLockTest2 {
    private Map<Thread, Integer> readingThreads = new HashMap<>();
    private int writers = 0;
    private int writerRequests = 0;
    private int writeAccesses = 0;
    private Thread writingThread = null;

    public synchronized void lockRead() throws InterruptedException {
        Thread callingThread = Thread.currentThread();
        while(! canGrantReadAccess(callingThread)) {
            wait();
        }
        readingThreads.put(callingThread,
                readingThreads.getOrDefault(callingThread, 0) + 1);
    }

    public synchronized void unlockRead() {
        Thread callingThread = Thread.currentThread();
        int accessCount = readingThreads.get(callingThread);
        if(accessCount == 1) {
            readingThreads.remove(callingThread);
        } else {
            readingThreads.put(callingThread, accessCount - 1);
        }
        notifyAll();
    }

    public synchronized void lockWrite() throws InterruptedException{
        writerRequests++;
        Thread callingThread = Thread.currentThread();
        while(!canGrantWriteAccess(callingThread)) {
            wait();
        }
        writingThread = callingThread;
        writerRequests--;
        writeAccesses++;
        writers = 1;
    }

    public synchronized void unlockWrite() {
        writeAccesses--;
        if(writeAccesses == 0) {
            writingThread = null;
        }
        notifyAll();
    }

    private boolean canGrantReadAccess(Thread callingThread) {
        if(writers > 0) return false;
        if(writerRequests > 0) return false;
        if(!readingThreads.containsKey(callingThread)) return false;
        return true;
    }

    private boolean canGrantWriteAccess(Thread callingThread) {
        if(hasReaders()) return false;
        if(writingThread == null) return true;
        if(!isWriter(callingThread)) return false;
        return true;
    }

    private boolean hasReaders() {
        return readingThreads.size() > 0;
    }

    private boolean isWriter(Thread callingThread) {
        return writingThread == callingThread;
    }
}
