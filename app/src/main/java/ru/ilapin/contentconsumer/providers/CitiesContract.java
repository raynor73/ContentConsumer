package ru.ilapin.contentconsumer.providers;

import android.net.Uri;

/**
 * Created by Raynor on 01.02.2015.
 */
public final class CitiesContract {

	public static final String AUTHORITY = "ru.ilapin.recyclerviewandcontentprovider.provider";
	public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);

	private CitiesContract() {}

	public static final class Cities {

		private Cities() {}

		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/city";
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/city";

		public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, "cities");
		public static final Uri CAPITALS_CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, "capitals");

		public static final String _ID = "_ID";
		public static final String NAME = "name";
		public static final String CAPITAL = "capital";
	}
}
