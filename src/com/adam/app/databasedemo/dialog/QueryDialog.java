/**
 * ===========================================================================
 * Copyright Adam Sample code
 * All Rights Reserved
 * ===========================================================================
 * 
 * File Name: QueryDialog.java
 * Brief: 
 * 
 * Author: AdamChen
 * Create Date: 2018/7/12
 */

package com.adam.app.databasedemo.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.adam.app.databasedemo.R;
import com.adam.app.databasedemo.data.DataBaseHanlder;
import com.adam.app.databasedemo.utils.Utils;

/**
 * <h1>QueryDialog</h1>
 * 
 * @autor AdamChen
 * @since 2018/7/12
 */
public class QueryDialog extends Dialog implements OnClickListener {

    private Button mOk;
    private ListView mList;

    public QueryDialog(Activity act) {
        super(act);

        DataBaseHanlder.INSTANCE.init(act.getApplicationContext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.Info(this, "onCreate enter");
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.dialog_query);

        mList = (ListView) this.findViewById(R.id.list_item);

        mOk = (Button) this.findViewById(R.id.btn_ok);
        mOk.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Utils.Info(this, "onStart enter");

        // Load data from database and add data to the list
        loadData();
    }

    private void loadData() {

        // Defines a list of columns to retrieve from the Cursor and load into
        // an output row
        String[] myDataColumns = { "_id", "name" };

        int[] myDataItems = { android.R.id.text1, android.R.id.text2 };

        Cursor cursor = DataBaseHanlder.INSTANCE.QueryData("");

        // Creates a new SimpleCursorAdapter
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(
                this.getContext(),
                android.R.layout.simple_expandable_list_item_2, cursor,
                myDataColumns, myDataItems, 0);

        mList.setAdapter(cursorAdapter);
    }

    @Override
    public void onClick(View v) {

        this.dismiss();
    }

}
