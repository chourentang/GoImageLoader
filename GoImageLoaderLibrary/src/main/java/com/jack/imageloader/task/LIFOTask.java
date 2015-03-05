package com.jack.imageloader.task;

/**
 * Created by jack on 15-2-14.
 */
public class LIFOTask<T> extends PriorityTask<T> {
    private long priority;
    private static long counter;

    public LIFOTask(Runnable runnable, T result) {
    	super(runnable, result);
    	counter++;
        priority = counter;
    }
    
    @Override
    public long getPriority() {
        return priority;
    }

    @SuppressWarnings("rawtypes")
	@Override
    public int compareTo(PriorityTask another) {
        if(another instanceof LIFOTask) {
            return priority > ((LIFOTask) another).getPriority() ? -1 : 1;
        }
        return 0;
    }
}
