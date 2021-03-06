package com.jack.imageloader.demo.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.jack.imageloader.comm.Logger;
import com.jack.imageloader.demo.R;
import com.jack.imageloader.demo.vo.Item;
import com.jack.imageloader.loader.ImageFetcher;

import java.util.List;

/**
 * image list adapter
 * Created by jack on 15-3-1.
 */
public class GridImageAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Item> listData;
    private ImageFetcher imageFetcher;

    public GridImageAdapter(Context context, List<Item> listData) {
        this.inflater = LayoutInflater.from(context);
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Holder holder;
        final Item item = listData.get(position);
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.image_grid_item, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (Holder)convertView.getTag();
        }
        Logger.warn(null, "get view: " + position);
        imageFetcher.loadImage(item.getUrl(), holder.mThumb);

        AbsListView.LayoutParams params =
                new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 160);
        convertView.setLayoutParams(params);

        return convertView;
    }

    static class Holder {
        ImageView mThumb;

        public Holder(View view) {
            mThumb = (ImageView)view.findViewById(R.id.image);
        }
    }

    public void setImageFetcher(ImageFetcher imageFetcher) {
        this.imageFetcher = imageFetcher;
    }

    public void setExitTasksEarly(boolean exitTasksEarly) {
        imageFetcher.setExitTasksEarly(exitTasksEarly);
    }
}
