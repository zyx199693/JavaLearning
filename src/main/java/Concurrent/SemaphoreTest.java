package Concurrent;

/**
 * 信号量的简单实现
 */
public class SemaphoreTest {
    public static void main(String args[]) throws InterruptedException {
        Semaphore semaphore = new Semaphore();
        SendingThread sender = new SendingThread(semaphore);
        ReceivingThread receiver = new ReceivingThread(semaphore);

        receiver.start();
        Thread.sleep(1000);
        sender.start();
    }

    public static class Semaphore {
        private boolean signal = false;
        public synchronized void take() {
            this.signal = true;
            this.notifyAll();
        }
        public synchronized void release() throws InterruptedException {
            while(!signal) wait();
            signal = false;
        }
    }

    public static class SendingThread extends Thread{
        Semaphore semaphore = null;

        public SendingThread(Semaphore semaphore) {
            this.semaphore = semaphore;
        }

        public void run() {
            while(true) {
                //do something, then signal
                System.out.println("sender has do something...");
                this.semaphore.take();
            }
        }
    }

    public static class ReceivingThread extends Thread{
        Semaphore semaphore = null;

        public ReceivingThread(Semaphore semaphore) {
            this.semaphore = semaphore;
        }

        public void run() {
            while(true) {
                try {
                    this.semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("receiver already receive the signal...");
                //receive signal, then do something...
            }
        }
    }
}
