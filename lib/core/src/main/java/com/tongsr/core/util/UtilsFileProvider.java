package com.tongsr.core.util;

import android.app.Application;

import androidx.core.content.FileProvider;

/**
 * <pre>
 *     author: blankj
 *     blog  : <a href="http://blankj.com">http://blankj.com</a>
 *     time  : 2020/03/19
 *     desc  :
 * </pre>
 */
public class UtilsFileProvider extends FileProvider {

    @Override
    public boolean onCreate() {
        //noinspection ConstantConditions
        Utils.init((Application) getContext().getApplicationContext());
        return true;
    }
}
