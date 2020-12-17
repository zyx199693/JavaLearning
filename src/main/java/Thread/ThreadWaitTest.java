package Thread;

public class ThreadWaitTest {
    private static volatile Object resourceA = new Object();
    private static volatile Object resourceB = new Object();

    public static class ResourceA implements Runnable {
        @Override
        public void run() {
            try{
                //获取resourceA的共享资源监视器锁
                synchronized (resourceA) {
                    System.out.println("获取线程A中resourceA的锁");
                    //获取resourceB的共享资源的监视器锁
                    synchronized (resourceB) {
                        System.out.println("获取线程A中resourceB的锁");

                        //线程A阻塞，并释放获取到的resourceA的锁
                        System.out.println("将线程A挂起，并释放resourceA的锁");
                        resourceA.wait();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class ResourceB implements Runnable {
        @Override
        public void run() {
            try{
                //休眠1s
                Thread.sleep(1000);

                //获取resourceA的共享资源监视器锁
                synchronized (resourceA) {
                    System.out.println("获取线程B中resourceA的锁");

                    synchronized (resourceB) {
                        System.out.println("获取线程B中resourceB的锁");

                        //线程B阻塞，并释放获取到的resourceA的锁
                        System.out.println("获取线程B中resourceB的锁");
                        resourceA.wait();
                    }
                }
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) throws InterruptedException {
        Thread a = new Thread(new ResourceA());
        Thread b = new Thread(new ResourceB());

        a.start();
        b.start();

        a.join();
        b.join();
    }
}
