/**
 * ===========================================================================
 * Copyright Adam Sample code
 * All Rights Reserved
 * ===========================================================================
 * 
 * File Name: MainActivity.java
 * Brief: 
 * 
 * Author: AdamChen
 * Create Date: 2018/7/10
 */
package com.adam.app.databasedemo;

import android.app.Dialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.adam.app.databasedemo.dialog.DeleteDialog;
import com.adam.app.databasedemo.dialog.InsertDialog;
import com.adam.app.databasedemo.dialog.QueryDialog;
import com.adam.app.databasedemo.dialog.UpdateDialog;

import java.util.HashMap;

public class MainActivity extends ListActivity {

    private static final String[] iItems = { Item.INSERT, Item.UPDATE,
            Item.QUERY, Item.DELETE };

    private HashMap<String, Dialog> mMap = new HashMap<String, Dialog>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, iItems);

        this.setListAdapter(adapter);

        mMap.put(Item.INSERT, new InsertDialog(this));
        mMap.put(Item.UPDATE, new UpdateDialog(this));
        mMap.put(Item.QUERY, new QueryDialog(this));
        mMap.put(Item.DELETE, new DeleteDialog(this));
        
        
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mMap.clear();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        int itemPosition = position;

        String item = (String) l.getItemAtPosition(itemPosition);

        Dialog dialog = mMap.get(item);
        if (dialog != null) {
            dialog.show();
        }
    }

    private static abstract class Item {

        public static final String INSERT = "insert";
        public static final String UPDATE = "update";
        public static final String QUERY = "query";
        public static final String DELETE = "delete";
    }

}
