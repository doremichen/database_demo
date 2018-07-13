/**
 * ===========================================================================
 * Copyright Adam Sample code
 * All Rights Reserved
 * ===========================================================================
 * 
 * File Name: InsertDialog.java
 * Brief: 
 * 
 * Author: AdamChen
 * Create Date: 2018/7/10
 */

package com.adam.app.databasedemo.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adam.app.databasedemo.R;
import com.adam.app.databasedemo.data.DataBaseHanlder;
import com.adam.app.databasedemo.utils.Utils;

/**
 * <h1>InsertDialog</h1>
 * 
 * @autor AdamChen
 * @since 2018/7/10
 */
public class InsertDialog extends Dialog implements
        android.view.View.OnClickListener {

    private Button mOk;
    private EditText mInput;

    public InsertDialog(Activity act) {
        super(act);
        
        DataBaseHanlder.INSTANCE.init(act.getApplicationContext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.Info(this, "onCreate enter");
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.dialog_insert);

        mInput = (EditText)this.findViewById(R.id.edit_name);
        
        mOk = (Button) this.findViewById(R.id.btn_ok);
        mOk.setOnClickListener(this);
    }

    
    @Override
    public void onClick(View v) {

        String inputStr = mInput.getText().toString();
        
        if (TextUtils.isEmpty(inputStr)) {
            Toast.makeText(getContext(), "Please input the valid data",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        
        
        // insert data in the database
        Uri newUri = DataBaseHanlder.INSTANCE.AddData(inputStr);
        String str = newUri.getPath().toString();
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
        
        // clear edit string
        mInput.setText("");

        this.dismiss();
    }

}
