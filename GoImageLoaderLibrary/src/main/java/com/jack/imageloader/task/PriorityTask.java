package com.jack.imageloader.task;

import java.util.concurrent.FutureTask;

/**
 * Task whith priority
 * Created by jack on 15-2-14.
 */
public abstract class PriorityTask<T> extends FutureTask<T> implements Comparable<PriorityTask> {

    public PriorityTask(Runnable runnable, T result) {
        super(runnable, result);
    }

    public abstract long getPriority();
}
