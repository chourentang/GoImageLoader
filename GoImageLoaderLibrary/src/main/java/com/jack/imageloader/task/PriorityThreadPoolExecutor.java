package com.jack.imageloader.task;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PriorityThreadPoolExecutor extends ThreadPoolExecutor {
    public static final int FIFO = 1;
    public static final int LIFO = 0;
    /* the order can be FIFO or LIFO */
    private static int order;

	public PriorityThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
			RejectedExecutionHandler handler, int order) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
				threadFactory, handler);
        this.order = order;
	}
	
	public PriorityThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
            int order) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        this.order = order;
	}
	
	public PriorityThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
            long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue,
            RejectedExecutionHandler handler, int order) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
        this.order = order;
	}
	
	public PriorityThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
            long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue,
            int order) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        this.order = order;
	}
	
	
	@Override
	protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
        if(order == LIFO)
		    return new LIFOTask<T>(runnable, value);
        else
            return new FIFOTask<T>(runnable, value);
	}

}
