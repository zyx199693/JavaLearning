package Thread;

public class ThreadInterrupt {
    private static Object obj = new Object();

    public static class ResourceA implements Runnable {
        @Override
        public void run() {
            try{
                System.out.println("---开始---");
                //阻塞当前线程
                synchronized (obj) {
                    obj.wait();
                }
                System.out.println("---结束----");
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public static void main(String args[]) throws InterruptedException {
            Thread thread = new Thread(new ResourceA());
            thread.start();
            thread.sleep(1000);
            System.out.println("--interrupt开始---");
            thread.interrupt();
            System.out.println("---interrupt结束---");
        }
    }
}
