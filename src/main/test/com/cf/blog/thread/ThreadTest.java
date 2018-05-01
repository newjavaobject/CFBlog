package com.cf.blog.thread;

import org.junit.Test;

/**
 * Created by chenzhiyu on 2018/4/23.
 * 实战Java高并发程序设计.pdf --> 113页
 *
 * 提升锁性能：减小锁持有时间
 *          减小锁粒度 ConcurrentHashMap
 *          读写锁替代独占锁
 *          锁分离 LinkedBlockingQueue
 */
public class ThreadTest {
    /* 启动线程 */
    @Test
    public void newThreadTest(){
        Thread thread = new Thread();
        thread.start();//新建一个线程执行run()方法
        thread.run();//不会新建线程，在当前线程中直接调用run()方法
    }
}
