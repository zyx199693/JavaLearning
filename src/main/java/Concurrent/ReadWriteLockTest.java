package Concurrent;

/**
 * 读写锁的简单实现
 * 读-读共存，读-写不共存，写-写不共存
 */
public class ReadWriteLockTest {
    private int readers = 0;    //读操作线程数
    private int writers = 0;    //写操作线程数
    private int writeRequests = 0;

    public synchronized void lockRead() throws InterruptedException {
        while(writers > 0 || writeRequests > 0) {
            wait();
        }
        readers++;
    }

    public synchronized void unlockRead() {
        readers--;
        notifyAll();
    }

    public synchronized void lockWrite() throws InterruptedException{
        writeRequests++;
        while(readers > 0 || writers > 0) {
            wait();
        }
        writeRequests--;
        writers++;
    }

    public synchronized void unlockWrite() {
        writers--;
        notifyAll();
    }
}
