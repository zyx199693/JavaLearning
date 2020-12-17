package Concurrent;

/**
 * lock implementation with nested monitor lockout problem
 * 嵌套管程锁死的lock实现
 */
public class LockTest2 {
    public static class Lock {
        protected MonitorObject monitorObject = new MonitorObject();
        protected  boolean isLock = false;

        public void lock() throws InterruptedException {
            synchronized (this) {
                while(isLock) {
                    synchronized (this.monitorObject) {
                        this.monitorObject.wait();
                    }
                }
                isLock = true;
            }
        }

        public void unlock() {
            synchronized (this) {
                this.isLock = false;
                synchronized (this.monitorObject) {
                    this.monitorObject.notify();
                }
            }
        }
    }
}

class MonitorObject {

}