package com.cf.blog.jvm;

/**
 * Created by chenzhiyu on 2018/4/24.
 */
public class MemoryFenPeiTest {
    /**
     * JVM参数：-Xms20m -Xmx20m -Xmn10m -XX:SurvivorRatio=8 -XX:+PrintGCDetails
     */
    private static final int _1MB = 1024 * 1024;
    public static void main(String[] args) {
        byte[] a1 = new byte[2 * _1MB];
        byte[] a2 = new byte[2 * _1MB];
        byte[] a3 = new byte[2 * _1MB];
        byte[] a4 = new byte[4 * _1MB];
    }
}
