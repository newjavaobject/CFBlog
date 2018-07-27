package com.cf.blog.thread;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 减少上下文切换的方法：
 * 1 无锁并发编程、
 * 2 CAS算法（如：Atomic包）、
 * 3 使用最少线程
 * 4 协程(单线程实现多任务调度)
 */
public class java并发编程的艺术 {
    private static String 变量A = "A";
    private static String 变量B = "B";

    @Test
    //死锁例子：通过dump出线程信息，查看哪个线程出现问题
    //避免死锁方法：避免一个线程同时获取多个锁；
    //            使用lock.tryLock(timeout)
    public void deadLockTest() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            synchronized (变量A) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + " --> 变量A被锁。");
                synchronized (变量B) {
                    System.out.println("111111111111");
                }
            }
        });
        Thread t2 = new Thread(() -> {
            synchronized (变量B) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + " --> 变量B被锁。");
                synchronized (变量A) {
                    System.out.println("2222222222222");
                }
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

    private AtomicInteger atomicInteger = new AtomicInteger();
    private int i = 0;
    /**
     * CAS原子操作三个问题：
     *  1、ABA问题：使用版本号解决
     *  2、循环时间长：CAS自旋长时间不成功
     */
}
