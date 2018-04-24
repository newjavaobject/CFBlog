package com.cf.blog.forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 * Created by chenzhiyu on 2018/4/24.
 */
public class ForkJoinTest {

}

class CountTask extends RecursiveTask{
    private static final int THRESHOLD = 10000;
    private long start;
    private long end;
    public CountTask(long start, long end){
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long sum = 0;
        boolean conCompute = (end - start) < THRESHOLD;
        if (conCompute) {
            for (long i = start; i <= end; i++) {
                sum += i;
            }
        }

        return null;
    }
}
