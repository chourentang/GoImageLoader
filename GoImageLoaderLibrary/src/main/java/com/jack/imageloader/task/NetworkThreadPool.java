package com.jack.imageloader.task;

import java.util.concurrent.Future;

/**
 * A thread pool to do network task
 * @author chouren
 */
public class NetworkThreadPool {
	private static ThreadPoolProcessor processor = new ThreadPoolProcessor(3);
	
	/**
	 * submit a network task
	 */
	public static Future<?> submitTask(Runnable task) {
	    return processor.submit(task);
	  }
}
