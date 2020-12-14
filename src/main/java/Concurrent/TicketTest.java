package Concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

class SellTickets implements Runnable {
    private int tickets = 10;

    @Override
    public void run() {
        if(tickets > 0) {
            sell();
        } else {
            return;
        }
    }

    public synchronized void sell() {
        if(tickets > 0) {
            System.out.println(Thread.currentThread().getName() + "卖出第" + tickets + "张票");
            tickets--;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class TicketTest {
    public static void main(String args[]) {
        SellTickets sellTickets = new SellTickets();
        new Thread(sellTickets, "窗口1").start();
        new Thread(sellTickets, "窗口2").start();
        new Thread(sellTickets, "窗口3").start();

        System.out.println(Thread.currentThread().getName() + "结束");
    }
}
