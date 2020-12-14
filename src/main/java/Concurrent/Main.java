package Concurrent;

public class Main {
    public static void main(String args[]) {
        new Thread(new MemoryModel()).start();
        new Thread(new MemoryModel()).start();
    }
}
