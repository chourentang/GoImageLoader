package com.jack.imageloader.loader;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.widget.ImageView;

/**
 * Created by jack on 15-3-2.
 */
public class FadeInBitmapSetter extends BitmapSetter {
    private static final int FADE_IN_TIME = 200;

    public FadeInBitmapSetter(String url, Bitmap bitmap,
                               ImageView imageView) {
        super(url, bitmap, imageView);
    }

    @Override
    protected void setImageBitmap() {
        if(url.equals((String)imageView.getTag())) {
            Resources resources = imageView.getContext().getResources();
            // Transition drawable with a transparent drawable and the final bitmap
            TransitionDrawable td = new TransitionDrawable(new Drawable[]{
                    new ColorDrawable(android.R.color.transparent),
                    new BitmapDrawable(resources, bitmap)});
            imageView.setImageDrawable(td);
            td.startTransition(FADE_IN_TIME);
        }
    }
}
