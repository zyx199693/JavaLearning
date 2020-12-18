package Concurrent;

import java.util.HashMap;
import java.util.Map;

/**
 * 可重入的ReadWriteLock的完整实现
 */
public class ReadWriteLockTest5 {
    private Map<Thread, Integer> readingThreads = new HashMap<>();

    private int writeAccesses = 0;
    private int writeRequests = 0;
    private Thread writingThread = null;

    public synchronized void lockRead() throws InterruptedException {
        Thread callingThread = Thread.currentThread();
        while(!canGrantReadAccess(callingThread)) {
            wait();
        }
        readingThreads.put(callingThread,
                readingThreads.getOrDefault(callingThread, 0) + 1);
    }

    public synchronized void unlockRead() {
        Thread callingThread = Thread.currentThread();
        if(!isReader(callingThread)) {
            throw new IllegalMonitorStateException(
                    "Calling Thread does not hold a read lock on this ReadWriteLock"
            );
        }
        int accessCount = getReadAccessCount(callingThread);
        if(accessCount == 1) {
            readingThreads.remove(callingThread);
        } else {
            readingThreads.put(callingThread, accessCount - 1);
        }
        notifyAll();
    }

    public synchronized void lockWrite() throws InterruptedException {
        writeRequests++;
        Thread callingThread = Thread.currentThread();
        while(!canGrantWriteAccess(callingThread)) {
            wait();
        }
        writeRequests--;
        writeAccesses++;
        writingThread = callingThread;
    }

    public synchronized void unlockWrite() {
        Thread callingThread = Thread.currentThread();
        if(!isWriter(callingThread)) {
            throw new IllegalMonitorStateException(
                    "Calling Thread does not hold the write lock on this ReadWriteLock"
            );
        }
        writeAccesses--;
        if(writeAccesses == 0) {
            writingThread = null;
        }
        notifyAll();
    }

    public boolean canGrantReadAccess(Thread callingThread){
        if(isWriter(callingThread)) return true;
        if(hasWriter()) return false;
        if(isReader(callingThread)) return true;
        if(hasWriteRequests()) return false;
        return true;
    }

    public boolean canGrantWriteAccess(Thread callingThread) {
        if(isOnlyRead(callingThread)) return true;
        if(hasReaders()) return false;
        if(writingThread == null) return true;
        if(!isWriter(callingThread)) return false;
        return true;
    }

    public boolean isReader(Thread callingThread) {
        return readingThreads.get(callingThread) != null;
    }

    public boolean isWriter(Thread callingThread) {
        return writingThread == callingThread;
    }

    public boolean hasWriter() {
        return writingThread != null;
    }

    public boolean hasReaders() {
        return readingThreads.size() > 0;
    }

    public boolean hasWriteRequests() {
        return writeRequests > 0;
    }

    public boolean isOnlyRead(Thread callingThread) {
        return readingThreads.size() == 1 &&
                readingThreads.get(callingThread) != null;
    }

    public int getReadAccessCount(Thread callingThread) {
        return readingThreads.get(callingThread);
    }
}
