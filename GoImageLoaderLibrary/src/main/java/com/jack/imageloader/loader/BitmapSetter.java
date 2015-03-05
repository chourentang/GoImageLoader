package com.jack.imageloader.loader;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * set image to ui
 * Created by jack on 15-3-2.
 */
public class BitmapSetter implements Runnable {
    protected String url;
    protected Bitmap bitmap;
    protected ImageView imageView;

    public BitmapSetter(String url, Bitmap bitmap, ImageView imageView) {
        this.url = url;
        this.bitmap = bitmap;
        this.imageView = imageView;
    }

    protected void setImageBitmap() {
        if(url.equals((String)imageView.getTag())) {
            imageView.setImageBitmap(bitmap);
        }

    }

    @Override
    public void run() {
        if(url != null && bitmap != null && imageView != null) {
            setImageBitmap();
        }
        url = null;
        bitmap = null;
        imageView = null;
    }
}
