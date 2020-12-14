package Concurrent;

public class Counter {
    public int count = 0;
    public synchronized void add(int value) {

            count += value;

    }
}

class CounterThread extends Thread {
    private Counter counter = null;

    CounterThread() {
        super();
    }
    CounterThread(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for(int i = 0; i < 10; i++) {
            counter.add(1);
            System.out.println(Thread.currentThread().getName() + " counter is:" + counter.count);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        Counter counter = new Counter();
        CounterThread counterThread1 = new CounterThread(counter);
        CounterThread counterThread2 = new CounterThread(counter);

        counterThread1.start();
        counterThread2.start();
    }
}
