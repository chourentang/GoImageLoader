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

package com.jack.imageloader.demo.vo;

import com.jack.imageloader.loader.ImageLoaderAdapter;

/**
 * Some simple test data to use for this sample app.
 */
public class Images {

  /**
   * This are PicasaWeb URLs and could potentially change. Ideally the PicasaWeb
   * API should be used to fetch the URLs.
   */
  public final static String[] imageUrls = new String[]{
          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1207/02/c0/12195456_1341200893057_800x600.jpg",
          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1207/02/c0/12195456_1341200893053_800x600.jpg",
          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1207/02/c0/12195456_1341200893054_800x600.jpg",
          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1207/02/c0/12195456_1341200893055_800x600.jpg",
          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1207/02/c0/12195456_1341200893056_800x600.jpg",
          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1207/02/c0/12195456_1341200893058_800x600.jpg",

          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1207/02/c0/12195456_1341200893059_800x600.jpg",
          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1207/02/c0/12195456_1341200893060_800x600.jpg",
          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1207/02/c0/12195456_1341200893061_800x600.jpg",
          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1207/02/c0/12195456_1341200893062_800x600.jpg",
          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1207/02/c0/12195456_1341200893052_800x600.jpg",

          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1502/10/c0/2967966_1423560526307_800x600.jpg",
          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1502/10/c0/2968063_1423560569122_800x600.jpg",
          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1502/10/c0/2968072_1423560565322_800x600.jpg",
          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1502/10/c0/2968062_1423560562330_800x600.jpg",
          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1502/10/c0/2967971_1423560559026_800x600.jpg",

          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1502/10/c0/2967960_1423560555794_800x600.jpg",
          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1502/10/c0/2967970_1423560550256_800x600.jpg",
          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1502/10/c0/2967951_1423560548201_800x600.jpg",
          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1502/10/c0/2967959_1423560546088_800x600.jpg",
          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1502/10/c0/2967969_1423560543205_800x600.jpg",

          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1502/10/c0/2967949_1423560540227_800x600.jpg",
          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1502/10/c0/2967958_1423560537447_800x600.jpg",
          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1502/10/c0/2967968_1423560534106_800x600.jpg",
          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1502/10/c0/2967948_1423560532149_800x600.jpg",
          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1502/10/c0/2967956_1423560529529_800x600.jpg",

          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1502/26/c0/3275071_1424937353915_800x600.jpg",
          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1502/26/c0/3275189_1424937411809_800x600.jpg",
          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1502/26/c0/3275274_1424937409477_800x600.jpg",
          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1502/26/c0/3275234_1424937401135_800x600.jpg",
          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1502/26/c0/3275188_1424937398917_800x600.jpg",

          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1502/26/c0/3275273_1424937395857_800x600.jpg",
          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1502/26/c0/3275233_1424937380508_800x600.jpg",
          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1502/26/c0/3275186_1424937374749_800x600.jpg",
          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1502/26/c0/3275111_1424937370574_800x600.jpg",
          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1502/26/c0/3275232_1424937361102_800x600.jpg",

          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1502/26/c0/3279072_1424942350189_800x600.jpg",
          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1502/26/c0/3279039_1424942369319_800x600.jpg",
          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1502/26/c0/3279018_1424942366788_800x600.jpg",
          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1502/26/c0/3279076_1424942364711_800x600.jpg",
          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1502/26/c0/3279038_1424942361747_800x600.jpg",

          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1502/26/c0/3279016_1424942360542_800x600.jpg",
          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1502/26/c0/3279075_1424942359121_800x600.jpg",
          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1502/26/c0/3279015_1424942357637_800x600.jpg",
          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1502/26/c0/3279074_1424942356091_800x600.jpg",
          "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1502/26/c0/3279014_1424942351983_800x600.jpg"
  };


  /**
   * Simple static adapter to use for images.
   */
  public final static ImageLoaderAdapter imageWorkerUrlsAdapter = new ImageLoaderAdapter() {
    @Override
    public Object getItem(int num) {
      return Images.imageUrls[num];
    }

    @Override
    public int getSize() {
      return Images.imageUrls.length;
    }
  };

  /**
   * Simple static adapter to use for image thumbnails.
   */
  public final static ImageLoaderAdapter imageThumbWorkerUrlsAdapter = new ImageLoaderAdapter() {
    @Override
    public Object getItem(int num) {
      return Images.imageUrls[num];
    }

    @Override
    public int getSize() {
      return Images.imageUrls.length;
    }
  };

}
