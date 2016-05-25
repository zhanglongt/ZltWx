package com.yfw.zlt.zltwx.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.yfw.zlt.zltwx.MainFragmentManager;
import com.yfw.zlt.zltwx.R;
import com.yfw.zlt.zltwx.common.SaveDatas;
import com.yfw.zlt.zltwx.ui.activity.mine.LoginActivity;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

/**
 * Created by zlt on 2016/5/19.
 */
public class WxFragment extends Fragment {
    TextView dian;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.xfragment, container, false);
        initView(view);
        return view;

    }
    private void initView(View view){
        //dian= (TextView) view.findViewById(R.id.dian);
        //notMoreClick();
    }
    /**
     * 3秒内不允许按钮多次点击
     */
    int i=1;
    private void notMoreClick(){//需添加rxbinding jar包
        RxView.clicks(dian)
                .throttleFirst(3, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Toast.makeText(getActivity(),"第" + i + "次点击", Toast.LENGTH_SHORT).show();
                        i++;
                    }
                });
    }

}
