package com.jack.imageloader.task;

import android.support.annotation.NonNull;

/**
 * 先进先出任务队列
 * Created by jack on 15-2-14.
 */
public class FIFOTask<T> extends PriorityTask<T> {
    private long priority;
    public static long counter;

    public FIFOTask(Runnable runnable, T result) {
    	super(runnable, result);
    	counter++;
        priority = counter;
    }

    @Override
    public long getPriority() {
        return priority;
    }

	@Override
    public int compareTo(@NonNull PriorityTask another) {
        if(another instanceof FIFOTask) {
            return priority > another.getPriority() ? 1 : -1;
        }
        return 0;
    }
}
