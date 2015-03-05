package com.jack.imageloader.demo.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class GridImageActivity extends FragmentActivity {
	private final String TAG = "GridImageActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(android.R.id.content, new GridImageFragment(), TAG);
		ft.commit();
	}
}
