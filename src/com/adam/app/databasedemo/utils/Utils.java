/**
 * ===========================================================================
 * Copyright Adam Sample code
 * All Rights Reserved
 * ===========================================================================
 * 
 * File Name: Utils.java
 * Brief: 
 * 
 * Author: AdamChen
 * Create Date: 2018/7/12
 */

package com.adam.app.databasedemo.utils;

import android.util.Log;

/**
 * <h1>Utils</h1>
 * 
 * @autor AdamChen
 * @since 2018/7/12
 */
public final class Utils {
    
    private static final String TAG = "DataBaseDemo";
    
    // prevent to instance
    private Utils() {}
    
    public static void Info(Object obj, String str) {
        Log.i(TAG, obj.getClass().getSimpleName() + ": " + str);
    }
    
    public static void Info(Class<?> clazz, String str) {
        Log.i(TAG, clazz.getSimpleName() + ": " + str);
    }

}
