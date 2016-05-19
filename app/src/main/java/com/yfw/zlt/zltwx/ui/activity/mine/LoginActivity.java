package com.yfw.zlt.zltwx.ui.activity.mine;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yfw.zlt.zltwx.MainFragmentManager;
import com.yfw.zlt.zltwx.R;
import com.yfw.zlt.zltwx.common.Contants;
import com.yfw.zlt.zltwx.mode.RemoteDataHandler;
import com.yfw.zlt.zltwx.utils.OkHttpUtils;

public class LoginActivity extends Activity implements View.OnClickListener{
    private Button goRegist;
    private Intent intent;
    private EditText et_usertel,et_password;
    private Button btn_login;
    private boolean isLogin=true;
    Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Toast.makeText(LoginActivity.this, "输入有误，请重试", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(LoginActivity.this, "网络连接失败或尚未注册", Toast.LENGTH_SHORT).show();
                    break;
            }
           // Toast.makeText(LoginActivity.this,"输入有误，请重试！",Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        goRegist= (Button) findViewById(R.id.btn_qtlogin);
        et_usertel= (EditText) findViewById(R.id.et_usertel);
        et_password= (EditText) findViewById(R.id.et_password);
        btn_login= (Button) findViewById(R.id.btn_login);
        //点击事件
        goRegist.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_qtlogin:
                intent=new Intent(LoginActivity.this,RegistActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
               login();
                break;
        }
    }

    /**
     * 登录
     */
   public void login(){
        String url= Contants.LOGIN_ACCESS;
        String phone=et_usertel.getText().toString();
        String pwd=et_password.getText().toString();
        String url1=url+"&phone="+phone+"&password="+pwd;
        Log.i("ii","url1:"+url1);
//       new BaseProtocol().createObservable(url1, MyHttpClient.METHOD_GET,null)
//               .subscribe(new Action1<String>() {
//                   @Override
//                   public void call(String s) {
//                       Log.i("ii",s);
//                       if(s.equals("wrong")){
//                         handler.sendEmptyMessage(0);
//                       }else {
//                           intent = new Intent(LoginActivity.this, MainFragmentManager.class);
//                           startActivity(intent);
//                           finish();
//                       }
//                   }
//               }, new Action1<Throwable>() {
//                   @Override
//                   public void call(Throwable throwable) {
//                       handler.sendEmptyMessage(1);
//                   }
//               });
       OkHttpUtils.ResultCallback<String> resultCallback = new OkHttpUtils.ResultCallback<String>() {

           @Override
           public void onSuccess(String response) {
              // LogUtils.i("ii","111111111:"+response);
               RemoteDataHandler data = new Gson().fromJson(response, RemoteDataHandler.class);
              // Log.i("ii","data.getResult():"+data.getResult());
               if(data.getResult().equals("right")) {
                   Toast.makeText(LoginActivity.this, "登录成功,正为你跳转主页面.....", Toast.LENGTH_SHORT).show();
                   new Handler().postDelayed(new Thread(){
                       public void run(){
                               intent = new Intent(LoginActivity.this, MainFragmentManager.class);
                               startActivity(intent);
                               finish();
                       }

                   },2000);
               }else if(data.getResult().equals("wrong")){
                   Toast.makeText(LoginActivity.this, "登录失败,请重试！", Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onFailure(Exception e) {
               Toast.makeText(LoginActivity.this,"网络连接失败",Toast.LENGTH_SHORT).show();
              // LogUtils.i("ii","222222222:"+e);
           }
       };
       OkHttpUtils.get(url1,resultCallback);


   }

    private BroadcastReceiver mBro=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(Contants.LOGIN_SUCCESS_URL)) {
                intent = new Intent(LoginActivity.this, MainFragmentManager.class);
                startActivity(intent);
                finish();
            }
        }
    };
    //注册广播
    public void registerBoradcastReceiver(){
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(Contants.LOGIN_SUCCESS_URL);
        this.registerReceiver(mBro, myIntentFilter);  //注册广播
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerBoradcastReceiver();
        if(isLogin){
            intent = new Intent(LoginActivity.this, MainFragmentManager.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
            unregisterReceiver(mBro);
    }
}
