package Concurrent;

public class MemoryModel implements Runnable {

    @Override
    public void run() {
        methodOne();
    }

    public void methodOne() {
        int localVariable1 = 45;

        MySharedObject localVariable2 = MySharedObject.sharedInstance;

        methodTwo();
    }


    public void methodTwo() {
        Integer localVariable1 = new Integer(99);
    }
}

class MySharedObject {
    public static final MySharedObject sharedInstance = new MySharedObject();

    public  Integer Object2 = new Integer(22);
    public Integer Object4 = new Integer(44);

    public long member1 = 12345;
    public long member2 = 34343;
}