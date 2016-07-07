package com.yfw.zlt.zltwx.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yfw.zlt.zltwx.R;
import com.yfw.zlt.zltwx.http.BaseProtocol;
import com.yfw.zlt.zltwx.http.MyHttpClient;
import com.yfw.zlt.zltwx.ui.activity.other.Ds;

import rx.functions.Action1;

/**
 * Created by zlt on 2016/5/19.
 */
public class MineFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.minefragment, container, false);
        initView(view);
        return view;

    }
    private void initView(View view){
        String url="http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html";
        new BaseProtocol().createObservable(url, MyHttpClient.METHOD_GET,null)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Ds ds = new Gson().fromJson(s, Ds.class);
                       String Postid= ds.getT1348647909107().get(0).getPostid();
//                        Log.i("ii","ss:"+s);
//                        Log.i("ii","Postid:"+Postid);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

    }
}
