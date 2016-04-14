package com.iss.android.wearable.datalayer;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.provider.UserDictionary;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.net.URI;

public class CheckDatabaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_check_database);

        TextView emptyBox = (TextView) this.findViewById(R.id.empty);

        Uri CONTENT_URI = ISSContentProvider.CONTENT_URI;

        String mSelectionClause = "";
        String[] mSelectionArgs = {};
        String[] mProjection = {ISSContentProvider._ID, ISSContentProvider.TIMESTAMP, ISSContentProvider.VALUE1};
        String mSortOrder = ISSContentProvider.TIMESTAMP + " DESC";

        // Does a query against the table and returns a Cursor object
        Cursor mCursor = getContentResolver().query(
                CONTENT_URI,                       // The content URI of the database table
                mProjection,                       // The columns to return for each row
                mSelectionClause,                  // Either null, or the word the user entered
                mSelectionArgs,                    // Either empty, or the string the user entered
                mSortOrder);                       // The sort order for the returned rows

        // Some providers return null if an error occurs, others throw an exception
        if (null == mCursor) {
            emptyBox.setVisibility(View.VISIBLE);
        // If the Cursor is empty, the provider found no matches
        } else if (mCursor.getCount() < 1) {
            emptyBox.setVisibility(View.VISIBLE);
        } else {
            // Insert code here to do something with the results
            emptyBox.setVisibility(View.INVISIBLE);
        }

        String[] mWordListColumns =
                {
                        ISSContentProvider.TIMESTAMP,
                        ISSContentProvider.VALUE1
                };

        int[] mWordListItems = { R.id.timestamp_entry, R.id.value_entry};

        SimpleCursorAdapter mCursorAdapter = new SimpleCursorAdapter(
                getApplicationContext(),               // The application's Context object
                R.layout.activity_check_database_row,                  // A layout in XML for one row in the ListView
                mCursor,                               // The result from the query
                mWordListColumns,                      // A string array of column names in the cursor
                mWordListItems,                        // An integer array of view IDs in the row layout
                0);                                    // Flags (usually none are needed)

        ListView mListView = (ListView) findViewById(R.id.list);
        mListView.setAdapter(mCursorAdapter);
    }
}
