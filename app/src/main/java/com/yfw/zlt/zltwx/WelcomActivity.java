package com.yfw.zlt.zltwx;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;

import com.yfw.zlt.zltwx.ui.activity.mine.LoginActivity;

import java.io.File;

/**
 * 开屏页
 */
public class WelcomActivity extends Activity {
    private static final int sleepTime = 2000;
    boolean isLogin=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        View view = View.inflate(this, R.layout.activity_welcom, null);
//        setContentView(view);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);
       // setContentView(R.layout.activity_welcom);

        //        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                Intent intent=new Intent(MainActivity.this,MainFragmentManager.class);
//                startActivity(intent);
//            }
//        },1000);

//        new Handler().postDelayed(new Thread(){
//            public void run(){
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                Intent intent=new Intent(WelcomActivity.this,MainFragmentManager.class);
//                startActivity(intent);
//                WelcomActivity.this.finish();
//            }
//        },1000);
        initFile() ;

//        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
//        animation.setDuration(1500);
//        view.startAnimation(animation);
    }
    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Thread(){
            public void run(){
                if(isLogin){
                    long start = System.currentTimeMillis();
                    long costTime = System.currentTimeMillis() - start;
                    //等待sleeptime时长
                    if(sleepTime-costTime>0){
                        try {
                            Thread.sleep(sleepTime-costTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Intent intent=new Intent(WelcomActivity.this,MainFragmentManager.class);
                        startActivity(intent);
                        WelcomActivity.this.finish();

                    }
                }else {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent intent=new Intent(WelcomActivity.this,LoginActivity.class);
                    startActivity(intent);
                    WelcomActivity.this.finish();
                }
            }
        },1000);
    }
    /**
     * 获取当前应用程序的版本号
     */
    private String getVersion(){
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packinfo = pm.getPackageInfo(getPackageName(), 0);
            String version = packinfo.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "版本号错误";
        }
    }

    @SuppressLint("SdCardPath")
    private void initFile() {
        File dir = new File("/sdcard/fanxin");
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

}

