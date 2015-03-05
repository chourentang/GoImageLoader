package com.jack.imageloader.task;

import android.renderscript.RenderScript;

import java.util.Comparator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 一个线程池，用于处理提交的任务
 * Created by jack on 15-2-14.
 */
public class ThreadPoolProcessor {
    private ThreadPoolExecutor executor;
    private BlockingQueue<Runnable> queue;

    public ThreadPoolProcessor(int poolSize) {
        queue = new PriorityBlockingQueue<Runnable>(64, new Comparator<Runnable>() {
            @Override
            public int compare(Runnable lhs, Runnable rhs) {
                if(lhs instanceof PriorityTask && rhs instanceof PriorityTask) {
                    PriorityTask lpt = (PriorityTask)lhs;
                    PriorityTask rpt = (PriorityTask)rhs;
                    return lpt.compareTo(rpt);
                }
                return 0;
            }
        });

        executor = new PriorityThreadPoolExecutor(poolSize, poolSize, 0, TimeUnit.SECONDS
            , queue, PriorityThreadPoolExecutor.FIFO);
    }

    public Future<?> submit(Runnable task) {
        return executor.submit(task);
    }

    public void clear() {
        executor.purge();
    }

}
