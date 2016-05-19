package com.yfw.zlt.zltwx.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yfw.zlt.zltwx.R;

/**
 * Created by zlt on 2016/5/19.
 */
public class Fx01 extends Fragment {
    private String mTitle;
    private TextView tvTitle;

    public Fx01(String mTitle) {
        this.mTitle = mTitle;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fx01, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tvTitle= (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setText(mTitle);
    }

}
