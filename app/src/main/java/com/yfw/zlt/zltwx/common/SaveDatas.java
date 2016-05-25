package com.yfw.zlt.zltwx.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zlt on 2016/5/23.
 */
public class SaveDatas {
    /**
     * 保存Preference的name
     */
    public static final String PREFERENCE_NAME = "local_userinfo";
    private static SharedPreferences mSharedPreferences;
    private static SaveDatas mPreferenceUtils;
    private static SharedPreferences.Editor editor;

    public SaveDatas(Context cxt) {
        mSharedPreferences = cxt.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
    }
    /**
     * 单例模式，获取instance实例
     *
     * @param cxt
     * @return
     */
    public static SaveDatas getInstance(Context cxt) {
        if (mPreferenceUtils == null) {
            mPreferenceUtils = new SaveDatas(cxt);
        }
        editor = mSharedPreferences.edit();
        return mPreferenceUtils;
    }

    /**
     * 保存数据
     * @param str_name
     * @param str_value
     */
    public void setUserInfo(String str_name, String str_value) {

        editor.putString(str_name, str_value);
        editor.commit();
    }

    /**
     * 取数据
     * @param str_name
     * @return
     */
    public String getUserInfo(String str_name) {

        return mSharedPreferences.getString(str_name, "");

    }

    /**
     * 删除数据
     */
    public void delete(){
        editor.remove("nick");
        //editor.clear();
        editor.commit();
    }
}
