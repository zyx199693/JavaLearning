package Concurrent;

import org.junit.Test;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class test_object_layout {

    @Test
    public void test_object_layout() {
        Object o = new Object();
        System.out.println(VM.current().details());
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }

    @Test
    public void test_syn_lock() throws InterruptedException{
        TimeUnit.SECONDS.sleep(5L);
        Object o = new Object();
        synchronized (o) {
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
        System.out.println("-------------------------");
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }

    @Test
    public void test_syn_heavy_lock() throws InterruptedException {
        Object o = new Object();
        //模拟多线程竞争
        for(int i = 0; i < 100; i++) {
            new Thread(() -> {
                synchronized (o) {
                    try {
                        TimeUnit.SECONDS.sleep(1L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            ).start();
        }
        TimeUnit.SECONDS.sleep(5L);
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        TimeUnit.SECONDS.sleep(100L);
        System.out.println(ClassLayout.parseInstance(o).toPrintable() );
    }
}
