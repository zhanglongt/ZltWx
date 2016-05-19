package com.yfw.zlt.zltwx.mode;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zlt on 2016/5/13.
 */
public class Login {
    public static class Attr{
        public static final String KEY = "key";
        public static final String USERNAME = "username";
        public static final String USERID = "userid";
    }
    private String key;
    private String username;
    private String userid;

    public Login() {
    }

    public Login(String key, String username, String userid) {
        this.key = key;
        this.username = username;
        this.userid = userid;
    }

    public static Login newInstanceList(String json){
        Login bean=null;
        try {
            JSONObject obj = new JSONObject(json);
            if(obj.length()> 0){
                String key = obj.optString(Attr.KEY);
                String username = obj.optString(Attr.USERNAME);
                String userid = obj.optString(Attr.USERID);
                bean = new Login(key, username, userid);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
