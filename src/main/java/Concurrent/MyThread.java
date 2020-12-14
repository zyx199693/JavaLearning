package Concurrent;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class MyThread implements Runnable {
    NotThreadSafe notThreadSafe = null;

    public MyThread(NotThreadSafe notThreadSafe) {
        this.notThreadSafe = notThreadSafe;
    }

    @Override
    public void run() {
        notThreadSafe.add("abc");
    }
}

class NotThreadSafe{
    StringBuilder stringBuilder = new StringBuilder("abc");

    public void add(String str) {
        stringBuilder.append(str);
    }
}
