package Concurrent;

import apple.laf.JRSUIUtils;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    TreeNode parent = null;
    List children = new ArrayList();

    public synchronized void addChild(TreeNode child) {
        if(!this.children.contains(child)) {
            this.children.add(child);
            child.setParentOnly(this);
        }
    }

    public synchronized void addChildOnly(TreeNode child) {
        if(!this.children.contains(child)) {
            this.children.add(child);
        }
    }

    public synchronized void setParent(TreeNode parent) {
        this.parent = parent;
        parent.addChildOnly(this);
    }

    public synchronized void setParentOnly(TreeNode parent) {
        this.parent = parent;
    }

    public static class MyThread1 extends Thread {
        private TreeNode parent = null;
        private TreeNode child = null;

        public void setParent(TreeNode parent) {
            this.parent = parent;
        }

        public void setChild(TreeNode child) {
            this.child = child;
        }

        @Override
        public void run() {
            parent.addChild(child);
        }
    }

    public static class MyThread2 extends Thread {
        private TreeNode parent = null;
        private TreeNode child = null;

        public void setParent(TreeNode parent) {
            this.parent = parent;
        }

        public void setChild(TreeNode child) {
            this.child = child;
        }

        @Override
        public void run() {
            child.setParent(parent);
        }
    }
    public static void main(String args[]) {
        MyThread1 thread1 = new MyThread1();
        MyThread2 thread2 = new MyThread2();

        TreeNode parent = new TreeNode();
        TreeNode child = new TreeNode();

        thread1.setChild(child);
        thread1.setParent(parent);

        thread2.setChild(child);
        thread2.setParent(parent);

        int i = 0;
        while(i < 10) {
            thread1.start();
            thread2.start();
            i++;
        }
    }
}


