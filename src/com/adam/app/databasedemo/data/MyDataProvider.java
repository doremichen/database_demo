/**
 * ===========================================================================
 * Copyright Adam Sample code
 * All Rights Reserved
 * ===========================================================================
 * 
 * File Name: MyDataProvider.java
 * Brief: 
 * 
 * Author: AdamChen
 * Create Date: 2018/7/12
 */

package com.adam.app.databasedemo.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.adam.app.databasedemo.utils.Utils;

/**
 * <h1>MyDataProvider</h1>
 * 
 * @autor AdamChen
 * @since 2018/7/12
 */
public class MyDataProvider extends ContentProvider {

    // Database information
    private static final String DATABASE_NAME = "Adam";
    private static final String TABLE_NAME = "MyTable";
    private static final int DATABSE_VERSION = 1;

    // Build the uri
    private static final String AUTHORITY = "com.adam.app.databasedemo.data.MyDataProvider";
    private static final String URL = "content://" + AUTHORITY + "/"
            + TABLE_NAME;
    static final Uri MYDATA_URI = Uri.parse(URL);

    // Index for Uri matcher
    static final int MYTABLE = 1;
    static final int MYTABLE_ID = 2;

    // Construct Uri matcher
    private static final UriMatcher URIMATCHER = new UriMatcher(
            UriMatcher.NO_MATCH);
    static {
        URIMATCHER.addURI(AUTHORITY, TABLE_NAME, MYTABLE);
        URIMATCHER.addURI(AUTHORITY, TABLE_NAME + "/#", MYTABLE_ID);
    }

    // The column name of database
    static final String COLUMN_ID = "_id";
    static final String COLUMN_NAME = "name";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT NOT NULL);";

    private SQLiteDatabase mWriteDatabase;
    private SQLiteDatabase mReadDatabase;

    /**
     * 
     * <h1>DatabaseHelper</h1> Helper class that actually creates and manages
     * the provider's underlying data repository.
     * 
     * @autor AdamChen
     * @since 2018/7/12
     */
    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABSE_VERSION);
            Utils.Info(this, "Constructor...");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Utils.Info(this, "onCreate enter");
            // Create TABLE
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Utils.Info(this, "onUpgrade enter");
            // Update database if exists
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

    }

    @Override
    public boolean onCreate() {

        Utils.Info(this, "onCreate enter");
        DatabaseHelper helper = new DatabaseHelper(this.getContext());

        mWriteDatabase = helper.getWritableDatabase();
        mReadDatabase = helper.getReadableDatabase();

        return (mWriteDatabase == null || mReadDatabase == null) ? false : true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        Utils.Info(this, "query enter");
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE_NAME);

        int uriType = URIMATCHER.match(uri);

        // Config uri
        switch (uriType) {
        case MYTABLE:
            break;
        case MYTABLE_ID:
            queryBuilder
                    .appendWhere(COLUMN_ID + "=" + uri.getLastPathSegment());
            break;
        default:
            throw new IllegalArgumentException("Unknown URI");
        }

        // Query data by queryBuilder
        Cursor c = queryBuilder.query(mReadDatabase, projection, selection,
                selectionArgs, null, null, sortOrder);

        // Notify
        c.setNotificationUri(this.getContext().getContentResolver(), uri);

        return c;
    }

    @Override
    public String getType(Uri uri) {
        Utils.Info(this, "getType enter");
        int uriType = URIMATCHER.match(uri);

        switch (uriType) {
        case MYTABLE:
            return "vnd.android.cursor.dir/vnd.mydata";
        case MYTABLE_ID:
            return "vnd.android.cursor.item/vnd.mydata";
        default:
            throw new UnsupportedOperationException("Not yet implemented");
        }

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Utils.Info(this, "insert enter");
        int uriType = URIMATCHER.match(uri);
        long id = 0;
        switch (uriType) {
        case MYTABLE:
            id = mWriteDatabase.insert(TABLE_NAME, null, values);
            break;
        default:
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        // Notify
        this.getContext().getContentResolver().notifyChange(uri, null);

        return Uri.parse(TABLE_NAME + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Utils.Info(this, "delete enter");
        int uriType = URIMATCHER.match(uri);
        int rowDelete = 0;

        switch (uriType) {
        case MYTABLE:
            rowDelete = mWriteDatabase.delete(TABLE_NAME, selection,
                    selectionArgs);
            break;
        case MYTABLE_ID:
            String id = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowDelete = mWriteDatabase.delete(TABLE_NAME, COLUMN_ID + "="
                        + id, null);
            } else {
                rowDelete = mWriteDatabase.delete(TABLE_NAME, COLUMN_ID + "="
                        + id + " and " + selection, selectionArgs);
            }
            break;
        default:
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        // Notify
        this.getContext().getContentResolver().notifyChange(uri, null);

        return rowDelete;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
            String[] selectionArgs) {
        Utils.Info(this, "update enter");
        int uriType = URIMATCHER.match(uri);
        int rowUpdate = 0;

        switch (uriType) {
        case MYTABLE:
            rowUpdate = mWriteDatabase.update(TABLE_NAME, values, selection,
                    selectionArgs);
            break;
        case MYTABLE_ID:
            String id = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowUpdate = mWriteDatabase.update(TABLE_NAME, values, COLUMN_ID
                        + "=" + id, null);
            } else {
                rowUpdate = mWriteDatabase.update(TABLE_NAME, values, COLUMN_ID
                        + "=" + id + " and " + selection, selectionArgs);
            }
            break;
        default:
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        // Notify
        this.getContext().getContentResolver().notifyChange(uri, null);

        return rowUpdate;
    }

}
