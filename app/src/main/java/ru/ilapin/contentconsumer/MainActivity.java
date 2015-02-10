package ru.ilapin.contentconsumer;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.ilapin.contentconsumer.providers.CitiesContract;


public class MainActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor> {

	private static final String TAG = "MainActivity";

	private RecyclerView mRecyclerView;
	private CitiesAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

		mAdapter = new CitiesAdapter(this);
		mRecyclerView.setAdapter(mAdapter);

		getSupportLoaderManager().initLoader(0, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(final int id, final Bundle args) {
		Log.d(TAG, "onCreateLoader");
		return new CursorLoader(
				this,
				CitiesContract.Cities.CONTENT_URI,
				null,
				null,
				null,
				null
		);
	}

	@Override
	public void onLoadFinished(final Loader<Cursor> loader, final Cursor data) {
		Log.d(TAG, "onLoadFinished");
		mAdapter.setCursor(data);
	}

	@Override
	public void onLoaderReset(final Loader<Cursor> loader) {
		Log.d(TAG, "onLoaderReset");
		mAdapter.setCursor(null);
	}

	private static class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder> {

		private Cursor mCursor;
		private final Context mContext;

		private CitiesAdapter(Context context) {
			this.mContext = context;
		}

		public void setCursor(Cursor cursor) {
			this.mCursor = cursor;
			notifyDataSetChanged();
		}

		@Override
		public CitiesAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
			return new ViewHolder(LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, parent, false));
		}

		@Override
		public void onBindViewHolder(final CitiesAdapter.ViewHolder holder, final int position) {
			if (mCursor != null) {
				mCursor.moveToPosition(position);
				holder.textView.setText(mCursor.getString(mCursor.getColumnIndex(CitiesContract.Cities.NAME)));
			}
		}

		@Override
		public int getItemCount() {
			if (mCursor == null) {
				return 0;
			} else {
				return mCursor.getCount();
			}
		}

		public class ViewHolder extends RecyclerView.ViewHolder {

			public TextView textView;

			public ViewHolder(final View itemView) {
				super(itemView);

				textView = (TextView) itemView.findViewById(android.R.id.text1);
			}
		}
	}
}
