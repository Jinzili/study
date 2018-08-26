package com.jinzl;

public class ThreadDemo {

    public static class ThreadExtend extends Thread{

        @Override
        public void run() {
            System.out.println("继承Thread方式使用多线程...");
        }
    }

    public static class ThreadImplements implements Runnable{

        @Override
        public void run() {
            System.out.println("实现Runnable方式使用多线程...");
        }
    }

    public static void main(String[] args) {
        ThreadExtend threadExtend = new ThreadExtend();
        threadExtend.start();

        Thread threadImplements = new Thread(new ThreadImplements());
        threadImplements.start();

    }
}
