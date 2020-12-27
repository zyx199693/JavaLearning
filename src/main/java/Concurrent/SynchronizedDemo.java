package Concurrent;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class SynchronizedDemo {
    public synchronized void method() {
        System.out.println("synchronized方法");
    }
}
