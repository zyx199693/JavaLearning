package Concurrent;

import java.util.HashMap;
import java.util.Map;

/**
 * 读锁升级到写锁
 */
public class ReadWriteLockTest3 {
    private Map<Thread, Integer> readingThreads = new HashMap<>();

    private int writeAccesses = 0;
    private int writeRequests = 0;
    private int readers = 0;
    private Thread writingThread = null;

    public synchronized void lockWrite() throws InterruptedException {
        Thread callingThread = Thread.currentThread();
        writeRequests++;
        while(!canGrantWriteAccess(callingThread)) {
            wait();
        }
        writeRequests--;
        writeAccesses++;
        writingThread = callingThread;
    }

    private boolean canGrantWriteAccess(Thread callingThread) {
        if(isOnlyReader(callingThread)) return true;
        if(hasReaders()) return false;
        if(writingThread == null) return true;
        if(!isWriter(callingThread)) return false;
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
}
