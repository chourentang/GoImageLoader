package com.jack.imageloader.demo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.jack.imageloader.cache.ImageCache;
import com.jack.imageloader.cache.ImageCache.ImageCacheParams;
import com.jack.imageloader.comm.Utils;
import com.jack.imageloader.demo.R;
import com.jack.imageloader.demo.vo.Images;
import com.jack.imageloader.demo.vo.Item;
import com.jack.imageloader.loader.ImageFetcher;

import java.util.ArrayList;
import java.util.List;

public class GridImageFragment extends Fragment implements OnItemClickListener{
	private final String TAG = "GridImageFragment";
	private final String IMAGE_CACHE_DIR = "thumbs";
	
	private int mImageThumbSize;
	private ImageFetcher mImageWorker;
	private GridView mGridView;
    private GridImageAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		
		mImageThumbSize = getResources().getDimensionPixelSize(
				R.dimen.image_thumbnail_size);
		ImageCacheParams cacheParams = new ImageCacheParams(IMAGE_CACHE_DIR);

        List<Item> listData = new ArrayList<>();

        for(int i = 0; i < Images.imageUrls.length; i++) {
            Item item = new Item();
            item.setDesc("this is Image :" + i);
            item.setUrl(Images.imageUrls[i]);
            listData.add(item);
        }

        cacheParams.memCacheSize = 1024 * 1024 * Utils
	        .getMemoryClass(getActivity()) / 3;

	    // The ImageWorker takes care of loading images into our ImageView children
	    // asynchronously
	    mImageWorker = new ImageFetcher(getActivity(), mImageThumbSize);
	    mImageWorker.setLoadingImage(R.drawable.empty_photo);
	    mImageWorker.setImageCache(ImageCache.findOrCreateCache(
	        getActivity(), cacheParams));

        mAdapter = new GridImageAdapter(getActivity(), listData);
        mAdapter.setImageFetcher(mImageWorker);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View v = inflater.inflate(R.layout.image_grid_fragment,
				container, false);
		mGridView = (GridView) v.findViewById(R.id.gridView);

		mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(this);

		return v;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.main_menu, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.clear_cache:
			final ImageCache cache = mImageWorker.getImageCache();
		      if (cache != null) {
		        mImageWorker.getImageCache().clearCaches();
		        // DiskLruCache.clearCache(getActivity(), ImageFetcher.HTTP_CACHE_DIR);
		        Toast.makeText(getActivity(), R.string.clear_cache_complete,
		            Toast.LENGTH_SHORT).show();
		      }
		      return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		mImageWorker.setExitTasksEarly(true);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mImageWorker.setExitTasksEarly(false);
	    mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		final Intent i = new Intent(getActivity(),
		        ImageDetailActivity.class);
		    i.putExtra(ImageDetailActivity.EXTRA_IMAGE, (int) id);
		    startActivity(i);
	}
	

}
