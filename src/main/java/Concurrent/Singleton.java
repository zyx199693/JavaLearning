package Concurrent;

public class Singleton {
    //将uniqueInstance采用volatile关键字修饰也很有必要
    //uniqueInstance = new Singleton();这段代码其实是分为三步执行：
    //1.为uniqueInstance分配内存空间
    //2.初始化uniqueInstance
    //3.将uniqueInstance指向分配的内存空间
    private volatile static Singleton uniqueInstance;

    private Singleton() {

    }

    public static Singleton getUniqueInstance() {
        //先判断对象是否已经实例过，没有实例化才进入加锁代码
        if(uniqueInstance == null) {
            //类对象加锁
            synchronized (Singleton.class) {
                if(uniqueInstance == null) {
                    uniqueInstance = new Singleton();
                }
            }
        }
        return uniqueInstance;
    }
}
