package com.jack.imageloader.demo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jack.imageloader.cache.ImageCache;
import com.jack.imageloader.demo.R;
import com.jack.imageloader.demo.vo.Images;
import com.jack.imageloader.demo.vo.Item;
import com.jack.imageloader.loader.ImageFetcher;

import java.util.ArrayList;
import java.util.List;

/**
 * list image fragment
 * Created by jack on 15-3-1.
 */
public class ListImageFragment extends Fragment {
    private ImageAdapter mImageAdapter;
    private ImageFetcher imageFetcher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        List<Item> listData = new ArrayList<>();

        for(int i = 0; i < Images.imageUrls.length; i++) {
            Item item = new Item();
            item.setDesc("this is Image :" + i);
            item.setUrl(Images.imageUrls[i]);
            listData.add(item);
        }

        mImageAdapter = new ImageAdapter(getActivity(), listData);

        int size = getActivity().getResources().getDimensionPixelSize(
                R.dimen.image_thumbnail_size);
        ImageCache.ImageCacheParams cacheParams = new ImageCache.ImageCacheParams("thumbs");
        imageFetcher = new ImageFetcher(getActivity(), size);
        imageFetcher.setLoadingImage(R.drawable.empty_photo);
        imageFetcher.setImageCache(ImageCache.findOrCreateCache(
                getActivity(), cacheParams));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.image_list_fragment,
                container, false);
        ListView mListView = (ListView) v.findViewById(R.id.list);

        mImageAdapter.setImageFetcher(imageFetcher);
        mListView.setAdapter(mImageAdapter);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public void onPause() {
        super.onPause();
        mImageAdapter.setExitTasksEarly(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        mImageAdapter.setExitTasksEarly(false);
        mImageAdapter.notifyDataSetChanged();
    }


}
