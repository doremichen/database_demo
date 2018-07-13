/**
 * ===========================================================================
 * Copyright Adam Sample code
 * All Rights Reserved
 * ===========================================================================
 * 
 * File Name: DataBaseHanlder.java
 * Brief: 
 * 
 * Author: AdamChen
 * Create Date: 2018/7/13
 */

package com.adam.app.databasedemo.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import com.adam.app.databasedemo.utils.Utils;

/**
 * <h1>MyData</h1>
 * 
 * @autor AdamChen
 * @since 2018/7/13
 */
public enum DataBaseHanlder {

    INSTANCE;

    private ContentResolver mResolver;

    public void init(Context context) {
        mResolver = context.getContentResolver();
    }

    /**
     * 
     * <h1>AddData</h1> add data to database
     * 
     * @param context
     * @param data
     * @return Uri
     * 
     */
    public Uri AddData(String name) {

        // Check input value
        if ((name == null) || TextUtils.isEmpty(name)) {
            throw new RuntimeException("Invalid name");
        }

        // Defines ContentValues to contain the new values to insert
        ContentValues cv = new ContentValues();
        cv.put(MyDataProvider.COLUMN_NAME, name);

        Uri newUri = mResolver.insert(MyDataProvider.MYDATA_URI, cv);

        return newUri;

    }

    /**
     * 
     * <h1>UpdateData</h1> update the specified data in database
     * 
     * @param context
     * @param id
     * @param name
     * @return int
     * 
     */
    public int UpdateData(int id, String name) {

        // Defines ContentValues to contain the values to update
        ContentValues cv = new ContentValues();
        cv.put(MyDataProvider.COLUMN_NAME, name);
        
        String selection = MyDataProvider.COLUMN_ID + " =? ";
        String[] selectionArgs = {Long.toString(id)};
        
        int numOfUpdate = mResolver.update(MyDataProvider.MYDATA_URI, cv, selection, selectionArgs);
        
        return numOfUpdate;

    }

    /**
     * 
     * <h1>QueryData</h1> query all data in database
     * 
     * @param context
     * @param data
     * @param name
     * @return Cursor
     * 
     */
    public Cursor QueryData(String name) {

        // Check input value
        if (name == null) {
            throw new RuntimeException("Invalid name");
        }

        // Start query use the cursor
        // A "projection" defines the columns that will be returned for each row
        String[] projection = { MyDataProvider.COLUMN_ID,
                MyDataProvider.COLUMN_NAME };

        // Defines the selection clause
        String selectionCause = null;

        // Defines the selection contain arguments
        String[] selectionArgs = { "" };

        if (!TextUtils.isEmpty(name)) {
            Utils.Info(this, "not empty name");
            selectionCause = MyDataProvider.COLUMN_NAME + " = ?";
            selectionArgs[0] = name;
        } else {
            selectionArgs = null;
        }

        // Query process
        Cursor cursor = mResolver.query(MyDataProvider.MYDATA_URI, projection,
                selectionCause, selectionArgs, null);

        return cursor;

    }

    /**
     * 
     * <h1>DeleteData</h1> delete data in database by the specified id
     * 
     * @param context
     * @param id
     * @return int
     * 
     */
    public int DeleteData(int id) {

        String selection = MyDataProvider.COLUMN_ID + " =? ";
        String[] selectionArgs = {Long.toString(id)};
        
        // Delete data
        int numOfDelete = mResolver.delete(MyDataProvider.MYDATA_URI, selection, selectionArgs);
        
        return numOfDelete;
    }

}
