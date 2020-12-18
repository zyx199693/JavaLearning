package Concurrent;

import java.util.HashMap;
import java.util.Map;

/**
 * 写锁降级到读锁
 */
public class ReadWriteLockTest4 {
    private Map<Thread, Integer> readingThreads = new HashMap<>();

    private int writeAccesses = 0;
    private int writeRequests = 0;
    private int readers = 0;
    private Thread writingThread = null;

    public synchronized void lockWrite() throws InterruptedException {
        Thread callingThread = Thread.currentThread();
        writeRequests++;
        while(!canGrantReadAccess(callingThread)) {
            wait();
        }
        writeRequests--;
        writeAccesses++;
        writingThread = callingThread;
    }

    private boolean canGrantReadAccess(Thread callingThread) {
        if(isWriter(callingThread)) return true;
        if(writingThread != null) return false;
        if(isReader(callingThread)) return true;
        if(writeRequests > 0) return false;
        return true;
    }

    private boolean isOnlyReader(Thread callingThread) {
        if(readingThreads.get(callingThread) != null && readers == 1) {
            return true;
        } else {
            return false;
        }
    }

    private boolean hasReaders() {
        return readingThreads.size() > 0;
    }

    private boolean isWriter(Thread callingThread) {
        return writingThread == callingThread;
    }

    private boolean isReader(Thread callingThread) {
        return readingThreads.containsKey(callingThread);
    }
}
