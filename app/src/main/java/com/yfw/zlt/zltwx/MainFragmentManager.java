package com.yfw.zlt.zltwx;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.yfw.zlt.zltwx.common.SaveDatas;
import com.yfw.zlt.zltwx.http.BaseProtocol;
import com.yfw.zlt.zltwx.http.MyHttpClient;
import com.yfw.zlt.zltwx.ui.activity.base.BaseActivity;
import com.yfw.zlt.zltwx.ui.activity.mine.LoginActivity;
import com.yfw.zlt.zltwx.ui.fragment.FxFragment;
import com.yfw.zlt.zltwx.ui.fragment.MineFragment;
import com.yfw.zlt.zltwx.ui.fragment.TxlFragment;
import com.yfw.zlt.zltwx.ui.fragment.WxFragment;
import com.yfw.zlt.zltwx.view.SlidingLayout;

import org.androidannotations.annotations.EActivity;

import rx.functions.Action1;

//@EActivity(R.layout.main_fragment_manager)
public class MainFragmentManager extends BaseActivity implements View.OnClickListener{
    ListView menulist;
    ImageView menuButton;
    SlidingLayout slidingLayout;
    LinearLayout content;
    TextView mainTitle;
    int height;//屏幕高度
    /**  定义微信、通讯录、发现、我 的fragment页面 */
    private FxFragment fxFragemnt;
    private MineFragment mineFragment;
    private TxlFragment txlFragment;
    private WxFragment wxFragment;

    /**  定义微信、通讯录、发现、我 的tab的图标  */
    private RadioButton btnWxID;
    private RadioButton btnTxlID;
    private RadioButton btnFxID;
    private RadioButton btnMineID;
    /** 对Fragment进行管理  */
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment_manager);
        fragmentManager=getSupportFragmentManager();
        height=getWindowManager().getDefaultDisplay().getHeight();
        init();


    }
    private void init(){
        mainTitle= (TextView) findViewById(R.id.mainTitle);
        menulist= (ListView) findViewById(R.id.menulist);
        //点击
        menuButton=(ImageView) findViewById(R.id.menuButton);
        //侧滑
        slidingLayout=(SlidingLayout) findViewById(R.id.slidingLayout);
        content=(LinearLayout) findViewById(R.id.content);
        //监听事件
        //侧滑
        slidingLayout.setScrollEvent(content);
        //点击
        menuButton.setOnClickListener(this);

        //页面
        btnWxID= (RadioButton) findViewById(R.id.btnWxID);
        btnTxlID= (RadioButton) findViewById(R.id.btnTxlID);
        btnFxID= (RadioButton) findViewById(R.id.btnFxID);
        btnMineID= (RadioButton) findViewById(R.id.btnMineID);
        btnWxID.setOnClickListener(this);
        btnTxlID.setOnClickListener(this);
        btnFxID.setOnClickListener(this);
        btnMineID.setOnClickListener(this);

        menulist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String da=  menulist.getItemAtPosition(position).toString();
//                Log.i("ii","da:"+da);
//                Toast.makeText(MainFragmentManager.this,da,Toast.LENGTH_SHORT).show();
                if(da.equals("注销")){
                    SaveDatas.getInstance(MainFragmentManager.this).delete();
                    Intent intent = new Intent(MainFragmentManager.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else if(da.equals("图片")){
                    Toast.makeText(MainFragmentManager.this,"图片",Toast.LENGTH_SHORT).show();
                }else if(da.equals("收藏")){
                    Toast.makeText(MainFragmentManager.this,"收藏",Toast.LENGTH_SHORT).show();
                }
                else if(da.equals("分享QQ")){
                    Toast.makeText(MainFragmentManager.this,"分享QQ",Toast.LENGTH_SHORT).show();
                }else if(da.equals("分享微信")){
                    Toast.makeText(MainFragmentManager.this,"分享微信",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //首次进入首页
        wxIn();
    }
    /**  隐藏所有的fragment*/
    private void hideFragment(FragmentTransaction transaction){
        if(wxFragment !=null){
            transaction.hide(wxFragment);
        }
        if(txlFragment !=null){
            transaction.hide(txlFragment);
        }
        if(fxFragemnt !=null){
            transaction.hide(fxFragemnt);
        }
        if(mineFragment  !=null){
            transaction.hide(mineFragment);
        }
    }
    /** 设置开启的tab微信首页页面 */
    private void wxIn(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        if(wxFragment==null){
            wxFragment=new WxFragment();
            transaction.add(R.id.include,wxFragment);
        }else {
            transaction.show(wxFragment);
        }
        transaction.commitAllowingStateLoss();
    }
    /** 设置开启的tab微信通讯录页面 */
    private void txlIn(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        if(txlFragment==null){
            txlFragment=new TxlFragment();
            transaction.add(R.id.include,txlFragment);
        }else{
            transaction.show(txlFragment);
        }
        transaction.commitAllowingStateLoss();
    }
    /** 设置开启的tab微信发现页面 */
    private void fxIn(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        if(fxFragemnt==null){
            fxFragemnt=new FxFragment(fragmentManager);
            transaction.add(R.id.include,fxFragemnt);
        }else{
            transaction.show(fxFragemnt);
        }
        transaction.commitAllowingStateLoss();
    }
    /** 设置开启的tab微信我的页面 */
    private void mineIn(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        if(mineFragment==null){
            mineFragment=new MineFragment();
            transaction.add(R.id.include,mineFragment);
        }else{
            transaction.show(mineFragment);
        }
        transaction.commitAllowingStateLoss();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            // 点击侧滑菜单
            case R.id.menuButton:
                if(slidingLayout.isLeftLayoutVisible()){
                    slidingLayout.scrollToRightLayout();
                }else {
                    slidingLayout.scrollToLeftLayout();
                }
                break;
            case R.id.btnWxID:
                wxIn();
                mainTitle.setText("微信");
                break;
            case R.id.btnTxlID:
                txlIn();
                mainTitle.setText("通讯录");
                break;
            case R.id.btnFxID:
                fxIn();
                mainTitle.setText("发现");
                break;
            case R.id.btnMineID:
                mineIn();
                mainTitle.setText("我");
                break;
        }
    }
}
