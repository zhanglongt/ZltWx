package com.yfw.zlt.zltwx.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yfw.zlt.zltwx.R;
import com.yfw.zlt.zltwx.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zlt on 2016/5/19.
 */
public class FxFragment extends Fragment implements ViewPager.OnPageChangeListener{
    private LinearLayout titles;
    private CustomViewPager customViewPager;
    //页面列表
    private List<Fx01> fragments=new ArrayList();
    //标题
    private String[] mtitles;
    //标题列表
    List<String> titleList=new ArrayList<String>();
    // 每一个标题的宽度
    private int titleWidth;
    private static int curr = 0;
    private FragmentManager fragmentManager;

    public FxFragment(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fxfragment, container, false);
         initView(view);
        return view;

    }
    private void initView(View view){
        titles= (LinearLayout) view.findViewById(R.id.titles);
        customViewPager= (CustomViewPager) view.findViewById(R.id.viewPage);
        mtitles=getResources().getStringArray(R.array.titles);// 获取标题资源
        titleWidth=getActivity().getWindowManager().getDefaultDisplay().getWidth()/mtitles.length;
        for(int i=0; i<mtitles.length; i++){
            titleList.add(mtitles[i]);
            // 创建fragment
            Fx01 fx01=new Fx01(mtitles[i]);
            fragments.add(fx01);
            // title添加标题
            TextView tv = new TextView(getActivity());
            tv.setText(mtitles[i]);
            if (i == curr) {
                tv.setTextColor(Color.CYAN);
            }
            tv.setTextSize(18);
            tv.setLayoutParams(new LinearLayout.LayoutParams(titleWidth,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.CENTER);
            // 标题点击事件
            tv.setOnClickListener(new MyClick(i));
            titles.addView(tv, i);
        }
            customViewPager.setAdapter(new MyAdapter(fragmentManager));
            customViewPager.setCurrentItem(curr);
            customViewPager.setOnPageChangeListener(this);
//        for(int i=0;i<titleList.size();i++){
//            // title添加标题
//            TextView tv = new TextView(getContext());
//            tv.setText(mtitles[i]);
//            tv.setTextSize(18);
//            tv.setLayoutParams(new LinearLayout.LayoutParams(titleWidth,
//                    ViewPager.LayoutParams.WRAP_CONTENT));
//            tv.setGravity(Gravity.CENTER);
//
//            // 标题点击事件
//            // tv.setOnClickListener(new MyClick(i));
//            titles.addView(tv, i);
//            customViewPager.setAdapter(new MyAdapter(fragmentManager));
//            customViewPager.setCurrentItem(curr);
//            customViewPager.setOnPageChangeListener(this);
//        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        TextView tv = (TextView) titles.getChildAt(curr);
        tv.setTextColor(Color.BLACK);

        tv = (TextView) titles.getChildAt(position);
        tv.setTextColor(Color.CYAN);
        curr = position;
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    //自定义适配器
    class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
    //标题点击事件
    class MyClick implements View.OnClickListener{
        private int index;
        public MyClick(int i) {
            this.index=i;
        }
        @Override
        public void onClick(View v) {
            customViewPager.setCurrentItem(index,false);
            TextView tv = (TextView) titles.getChildAt(curr);
            tv.setTextColor(Color.BLACK);

            tv = (TextView) titles.getChildAt(index);
            tv.setTextColor(Color.CYAN);
            curr = index;
        }
    }
}
