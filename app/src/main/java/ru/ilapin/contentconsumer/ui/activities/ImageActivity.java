/*
 * @file ImageActivity.java
 * @author ilapin
 *
 * Copyright (c) 2004-2015. Parallels IP Holdings GmbH. All rights reserved.
 * http://www.parallels.com
 */
package ru.ilapin.contentconsumer.ui.activities;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;

import ru.ilapin.contentconsumer.R;
import ru.ilapin.contentconsumer.providers.CitiesContract;

public class ImageActivity extends ActionBarActivity {

	private static final String TAG = "ImageActivity";

	private ImageView mImageView;
	private ProgressBar mProgressBar;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);

		mImageView = (ImageView) findViewById(R.id.image_view);
		mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

		mImageView.setVisibility(View.GONE);
		mProgressBar.setVisibility(View.VISIBLE);

		new GetImageTask().execute();
	}

	private class GetImageTask extends AsyncTask<Void, Void, Bitmap> {

		private final Handler mHandler = new Handler(Looper.getMainLooper());

		@Override
		protected Bitmap doInBackground(final Void... params) {
			try {
				final AssetFileDescriptor fileDescriptor = getContentResolver()
						.openAssetFileDescriptor(CitiesContract.Cities.IMAGE_URI, "r");

				return BitmapFactory.decodeStream(fileDescriptor.createInputStream());
			} catch (final IOException e) {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(ImageActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
					}
				});
			}

			return null;
		}

		@Override
		protected void onPostExecute(final Bitmap bitmap) {
			mImageView.setImageBitmap(bitmap);

			mImageView.setVisibility(View.VISIBLE);
			mProgressBar.setVisibility(View.GONE);
		}
	}
}
