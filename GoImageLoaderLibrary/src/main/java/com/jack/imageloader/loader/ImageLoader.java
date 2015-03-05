package com.jack.imageloader.loader;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.jack.imageloader.cache.ImageCache;
import com.jack.imageloader.comm.Logger;
import com.jack.imageloader.task.NetworkThreadPool;

import java.lang.ref.WeakReference;

/**
 * load images
 * Created by jack on 15-3-1.
 */
public abstract class ImageLoader {
    protected ImageCache mImageCache;
    private Bitmap mLoadingBitmap;
    private volatile boolean mExitTasksEarly = false;

    protected Activity mActivity;

    public ImageLoader(Activity activity) {
        this.mActivity = activity;
    }

    public void loadImage(String url, ImageView imageView) {
        if(url == null || imageView == null)  return;

        /* bind url to image to avoid image in listview to be disorder/blink */
        /* 为imageview绑定url，以免图片加载出现错乱和闪烁 */
        imageView.setTag(url);

        Bitmap bitmap = null;

        /* first fetch image from memory cache */
        if(mImageCache != null) {
            bitmap = mImageCache.getBitmapFromMemCache(url);
        }

        /* if hit the bitmap in memory, set it */
        if(bitmap != null) {
            Logger.warn(null, "hit memory cache :" + url);
            imageView.setImageBitmap(bitmap);
        /* if bitmap can not find in memory, download or find in disk cache */
        } else if(needNewTask(url, imageView)) {
            BitmapWorkerRunnable task = new BitmapWorkerRunnable(imageView, url);
            TaskDrawable taskDrawable = new TaskDrawable(mActivity.getResources(), mLoadingBitmap, task);
            imageView.setImageDrawable(taskDrawable);
            NetworkThreadPool.submitTask(task);
        }
    }

    /**
     * judge if need new task to fetch the imageview
     * @param url
     *      the url of the imageview
     * @param imageView
     *      thie imageview
     * @return
     *      return true if the task bind to the imageview has been canceled or there was no task
     *      bind to the imageview (如果绑定到imageview的任务取消或还没有任务绑定，返回true）
     *      return false if the task bind to the imageview is progressing(如果绑定到imageview的任务
     *      正在执行，返回false)
     */
    private boolean needNewTask(String url, ImageView imageView) {
        BitmapWorkerRunnable task = getTaskBindToImageView(imageView);
        if(task != null) {
            final String taskUrl = task.getUrl();
            /* this mean the task is in progress (判断当前任务是否在执行) */
            if(taskUrl != null && taskUrl.equals(url)) {
                Logger.info(null, "cancel task: " + url);
                return false;
            }
        }
        return true;
    }

    /**
     * get the task bind to the imageview
     * @param imageView
     *      the imageview
     * @return
     *      return null if no task bind to the imageview
     */
    private BitmapWorkerRunnable getTaskBindToImageView(ImageView imageView) {
        if(imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if(drawable instanceof TaskDrawable) {
                return ((TaskDrawable)drawable).getTask();
            }
        }
        return null;
    }


    private boolean isCancelled() {
        return false;
    }

    /* get the bitmap from net */
    protected abstract Bitmap processBitmap(String url);

    public void setLoadingImage(int resId) {
        mLoadingBitmap = BitmapFactory.decodeResource(
                mActivity.getResources(), resId);
    }

    public void setImageCache(ImageCache imageCache) {
        this.mImageCache = imageCache;
    }

    public void setExitTasksEarly(boolean exit) {
        this.mExitTasksEarly = exit;
    }

    public ImageCache getImageCache() {
        return mImageCache;
    }

    public boolean exitTasksEarly() {
        return mExitTasksEarly;
    }

    /**
     * BitmapDrawable with task bind to it
     */
    private class TaskDrawable extends BitmapDrawable {
        private BitmapWorkerRunnable task;

        public TaskDrawable(Resources res, Bitmap bitmap, BitmapWorkerRunnable task) {
            super(res, bitmap);
            this.task = task;
        }

        public BitmapWorkerRunnable getTask() {
            return this.task;
        }
    }

    /**
     * The actual Runnable that will process the image.
     */
    private class BitmapWorkerRunnable implements Runnable {
        private String url;
        private final WeakReference<ImageView> imageViewReference;

        private BitmapWorkerRunnable(ImageView imageView, String url) {
            imageViewReference = new WeakReference<>(imageView);
            this.url = url;
        }

        /**
         * Actual processing. This is assumed to be called from a thread pool or
         * something outside the UI thread.
         */
        @Override
        public void run() {
            Bitmap bitmap = null;

            // If the image cache is available and this task has not been cancelled by
            // another
            // thread and the ImageView that was originally bound to this task is
            // still bound back
            // to this task and our "exit early" flag is not set then try and fetch
            // the bitmap from
            // the cache
            if (mImageCache != null) {
                bitmap = mImageCache.getBitmapFromDiskCache(url);
            }

            // If the bitmap was not found in the cache and this task has not been
            // cancelled by
            // another thread and the ImageView that was originally bound to this task
            // is stillif(BuildConfig.DEBUG)
            // bound back to this task and our "exit early" flag is not set, then call
            // the main
            // process method (as implemented by a subclass)
            if (bitmap == null) {
                bitmap = processBitmap(url);
            }else {
                Logger.warn(null, "hit disk cache - " + url);
            }

            // If the bitmap was processed and the image cache is available, then add
            // the processed
            // bitmap to the cache for future use. Note we don't check if the task was
            // cancelled
            // here, if it was, and the thread is still running, we may as well add
            // the processed
            // bitmap to our cache as it might be used again in the future
            if (bitmap != null && mImageCache != null) {
                mImageCache.addBitmapToCache(url, bitmap);
            }

            if (isCancelled() || mExitTasksEarly) {
                bitmap = null;
            }

            if (bitmap != null) {
                BitmapSetter runnable = new FadeInBitmapSetter(url, bitmap,
                            getAttachedImageView());

                mActivity.runOnUiThread(runnable);
            }
        }

        private ImageView getAttachedImageView() {
            return imageViewReference.get();
        }

        public String getUrl() {
            return this.url;
        }
    }
}
