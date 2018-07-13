/**
 * ===========================================================================
 * Copyright Adam Sample code
 * All Rights Reserved
 * ===========================================================================
 * 
 * File Name: DeleteDialog.java
 * Brief: 
 * 
 * Author: AdamChen
 * Create Date: 2018/7/12
 */

package com.adam.app.databasedemo.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adam.app.databasedemo.R;
import com.adam.app.databasedemo.data.DataBaseHanlder;

/**
 * <h1>DeleteDialog</h1>
 * 
 * @autor AdamChen
 * @since 2018/7/12
 */
public class DeleteDialog extends Dialog implements OnClickListener {

    private Button mOk;

    private EditText mInputId;

    public DeleteDialog(Activity act) {
        super(act);

        DataBaseHanlder.INSTANCE.init(act.getApplicationContext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.dialog_delete);

        mInputId = (EditText) this.findViewById(R.id.edit_id);

        mOk = (Button) this.findViewById(R.id.btn_ok);
        mOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String strId = mInputId.getText().toString();

        if (strId.isEmpty()) {
            Toast.makeText(getContext(), "Please input the valid data",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        int id = Integer.valueOf(strId).intValue();

        // delete data in the database
        int num = DataBaseHanlder.INSTANCE.DeleteData(id);

        Toast.makeText(getContext(), "Delete number: " + num,
                Toast.LENGTH_SHORT).show();

        // clear edit string
        mInputId.setText("");

        this.dismiss();
    }

}
