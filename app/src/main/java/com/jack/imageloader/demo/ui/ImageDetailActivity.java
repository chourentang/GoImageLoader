package com.jack.imageloader.demo.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.jack.imageloader.cache.ImageCache;
import com.jack.imageloader.demo.R;
import com.jack.imageloader.demo.vo.Images;
import com.jack.imageloader.demo.vo.Item;
import com.jack.imageloader.loader.ImageFetcher;

import java.util.ArrayList;
import java.util.List;

public class ImageDetailActivity extends FragmentActivity implements View.OnClickListener {
	public static String IMAGE_CACHE_DIR = "images";
    public static final String EXTRA_IMAGE = "extra_image";

    private ImageFetcher imageFetcher;
    private ImagePagerAdapter imagePagerAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_detail_pager);

        final DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        final int height = displaymetrics.heightPixels;
        final int width = displaymetrics.widthPixels;
        final int longest = height > width ? height : width;

        imageFetcher = new ImageFetcher(this, width, height);
        imageFetcher.setImageCache(ImageCache.findOrCreateCache(this, IMAGE_CACHE_DIR));

        List<Item> listData = new ArrayList<>();

        for(int i = 0; i < Images.imageUrls.length; i++) {
            Item item = new Item();
            item.setDesc("this is Image :" + i);
            item.setUrl(Images.imageUrls[i]);
            listData.add(item);
        }

        imagePagerAdapter = new ImagePagerAdapter(getSupportFragmentManager(), listData);
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(imagePagerAdapter);
        viewPager.setPageMargin((int) getResources().getDimension(
                R.dimen.image_detail_pager_margin));

        // Set up activity to go full screen
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Set the current item based on the extra passed in to this activity
        final int extraCurrentItem = getIntent().getIntExtra(EXTRA_IMAGE,
                -1);
        if (extraCurrentItem != -1) {
            viewPager.setCurrentItem(extraCurrentItem);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.clear_cache:
                final ImageCache cache = imageFetcher.getImageCache();
                if (cache != null) {
                    imageFetcher.getImageCache().clearCaches();
                    // DiskLruCache.clearCache(this, ImageFetcher.HTTP_CACHE_DIR);
                    Toast.makeText(this, R.string.clear_cache_complete,
                            Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public ImageFetcher getImageFetcher() {
        return this.imageFetcher;
    }

    /**
     * The main adapter that backs the ViewPager. A subclass of
     * FragmentStatePagerAdapter as there could be a large number of items in the
     * ViewPager and we don't want to retain them all in memory at once but
     * create/destroy them on the fly.
     */
    private class ImagePagerAdapter extends FragmentStatePagerAdapter {
        private List<Item> mList;

        public ImagePagerAdapter(FragmentManager fm, List<Item> list) {
            super(fm);
            mList = list;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return ImageDetailFragment.newInstance(mList.get(position).getUrl());
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            final ImageDetailFragment fragment = (ImageDetailFragment) object;
            // As the item gets destroyed we try and cancel any existing work.
            fragment.cancelWork();
            super.destroyItem(container, position, object);
        }
    }

    /**
     * Set on the ImageView in the ViewPager children fragments, to enable/disable
     * low profile mode when the ImageView is touched.
     */
    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        final int vis = viewPager.getSystemUiVisibility();
        if ((vis & View.SYSTEM_UI_FLAG_LOW_PROFILE) != 0) {
            viewPager.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        } else {
            viewPager.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
        }
    }
}
