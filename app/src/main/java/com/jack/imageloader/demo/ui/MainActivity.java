package com.jack.imageloader.demo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.jack.imageloader.cache.ImageCache;
import com.jack.imageloader.demo.R;

public class MainActivity extends ActionBarActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    
    private void init() {
        Button mLoadGridImage = (Button)findViewById(R.id.load_grid_image);
        Button mLoadListImage = (Button)findViewById(R.id.load_list_image);
    	mLoadGridImage.setOnClickListener(this);
    	mLoadListImage.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.clear_cache:
                ImageCache imageCache = new ImageCache(this, "thumbs");
                imageCache.clearCaches();
                Toast.makeText(this, R.string.clear_cache_complete,
                    Toast.LENGTH_SHORT).show();
            break;
        }
        return false;
    }
    
    @Override
    public void onClick(View v) {
    	switch (v.getId()) {
		case R.id.load_grid_image:
			startActivity(GridImageActivity.class);
			break;
		case R.id.load_list_image:
			startActivity(ListImageActivity.class);
			break;
		default:
			break;
		}
    }
    
    private void startActivity(Class<?> activity) {
    	Intent intent = new Intent(this, activity);
    	startActivity(intent);
    }
}
