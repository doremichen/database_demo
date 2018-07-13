/**
 * ===========================================================================
 * Copyright Adam Sample code
 * All Rights Reserved
 * ===========================================================================
 * 
 * File Name: UpdateDialog.java
 * Brief: 
 * 
 * Author: AdamChen
 * Create Date: 2018/7/10
 */

package com.adam.app.databasedemo.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adam.app.databasedemo.R;
import com.adam.app.databasedemo.data.DataBaseHanlder;

/**
 * <h1>UpdateDialog</h1>
 * 
 * @autor AdamChen
 * @since 2018/7/10
 */
public class UpdateDialog extends Dialog implements
        android.view.View.OnClickListener {

    private Button mOk;

    private EditText mInputName;
    private EditText mInputId;

    public UpdateDialog(Activity act) {
        super(act);

        DataBaseHanlder.INSTANCE.init(act.getApplicationContext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.dialog_update);

        mInputName = (EditText) this.findViewById(R.id.edit_name);
        mInputId = (EditText) this.findViewById(R.id.edit_id);

        mOk = (Button) this.findViewById(R.id.btn_ok);
        mOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String strName = mInputName.getText().toString();
        String strId = mInputId.getText().toString();

        if (strName.isEmpty() || strId.isEmpty()) {
            Toast.makeText(getContext(), "Please input the valid data",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        int id = Integer.valueOf(strId).intValue();

        // update data in the database
        int num = DataBaseHanlder.INSTANCE.UpdateData(id, strName);

        Toast.makeText(getContext(), "Update number: " + num,
                Toast.LENGTH_SHORT).show();

        // clear edit string
        mInputName.setText("");
        mInputId.setText("");

        this.dismiss();
    }

}
