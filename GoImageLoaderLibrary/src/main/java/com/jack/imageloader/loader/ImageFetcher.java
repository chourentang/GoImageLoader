/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jack.imageloader.loader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.imageloader.jack.goimageloaderlibrary.BuildConfig;
import com.jack.imageloader.cache.DiskLruCache;
import com.jack.imageloader.comm.Logger;
import com.jack.imageloader.comm.Utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A simple subclass of {@link ImageResizer} that fetches and resizes images
 * fetched from a URL.
 */
public class ImageFetcher extends ImageResizer {
    /**
     * Initialize providing a target image width and height for the processing
     * images.
     *
     * @param activity
     * @param imageWidth
     * @param imageHeight
     */
    public ImageFetcher(Activity activity, int imageWidth, int imageHeight) {
        super(activity, imageWidth, imageHeight);
        init(activity);
    }

    /**
     * Initialize providing a single target image size (used for both width and
     * height);
     *
     * @param activity
     * @param imageSize
     */
    public ImageFetcher(Activity activity, int imageSize) {
        super(activity, imageSize);
        init(activity);
    }

    private void init(Context context) {
        checkConnection(context);
    }

    /**
     * Simple network connection check.
     *
     * @param context
     */
    private void checkConnection(Context context) {
        final ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
            Toast.makeText(context, "No network connection found.",
                    Toast.LENGTH_LONG).show();
            Logger.debug(null, "checkConnection - no connection found");
        }
    }

    /**
     * The main process method, which will be called by the ImageWorker in the
     * AsyncTask background thread.
     *
     * @param data
     *          The data to load the bitmap, in this case, a regular http URL
     * @return The downloaded and resized bitmap
     */
    protected Bitmap processBitmap(String data) {
        Logger.warn(null, "download image - " + data);

        // Download a bitmap, write it to a file
        final File f = downloadBitmap(mActivity, data,
                mImageCache.getDiskCache());

        if (f != null) {
            // Return a sampled down version
            return decodeSampledBitmapFromFile(f.toString(), mImageWidth,
                    mImageHeight);
        }

        return null;
    }

    /**
     * Download a bitmap from a URL, write it to a disk and return the File
     * pointer. This implementation uses a simple disk cache.
     *
     * @param context
     *          The context to use
     * @param urlString
     *          The URL to fetch
     * @param cache
     *          The disk cache instance to get the download directory from.
     * @return A File pointing to the fetched bitmap
     */
    public static File downloadBitmap(Context context, String urlString,
                                      DiskLruCache cache) {
        final File cacheFile = new File(cache.createFilePath(urlString));

        if (cache.containsKey(urlString)) {
            if (BuildConfig.DEBUG) {
                Logger.debug(null, "downloadBitmap - found in http cache - "
                        + urlString);
            }
            return cacheFile;
        }

        if (BuildConfig.DEBUG) {
            Logger.debug(null, "downloadBitmap - downloading - " + urlString);
        }

        Utils.disableConnectionReuseIfNecessary();
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;

        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            final InputStream in = new BufferedInputStream(
                    urlConnection.getInputStream(), Utils.IO_BUFFER_SIZE);
            out = new BufferedOutputStream(new FileOutputStream(cacheFile),
                    Utils.IO_BUFFER_SIZE);

            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            cache.putFromFetcher(urlString);
            return cacheFile;

        } catch (final IOException e) {
            e.printStackTrace();
            Logger.debug(null, "Error in downloadBitmap - " + e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (out != null) {
                try {
                    out.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                    Logger.debug(null, "Error in downloadBitmap - " + e);
                }
            }
        }

        return null;
    }

}
