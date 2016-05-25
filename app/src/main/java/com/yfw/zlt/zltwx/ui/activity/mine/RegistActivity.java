package com.yfw.zlt.zltwx.ui.activity.mine;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yfw.zlt.zltwx.R;
import com.yfw.zlt.zltwx.common.Constant;
import com.yfw.zlt.zltwx.common.SaveDatas;
import com.yfw.zlt.zltwx.http.BaseProtocol;
import com.yfw.zlt.zltwx.http.MyHttpClient;
import com.yfw.zlt.zltwx.mode.RemoteDataHandler;

import java.util.HashMap;

import rx.functions.Action1;

/**
 * 注册页面
 */
public class RegistActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText et_usernick,et_usertel,et_password;
    private Button btn_regist;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Toast.makeText(RegistActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(RegistActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(RegistActivity.this, "手机号已被注册", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(RegistActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        initView();
    }

    private void initView() {
        et_password= (EditText) findViewById(R.id.et_password);
        et_usernick= (EditText) findViewById(R.id.et_usernick);
        et_usertel= (EditText) findViewById(R.id.et_usertel);
        btn_regist= (Button) findViewById(R.id.btn_regist);
        btn_regist.setOnClickListener(this);
    }

    /**
     * 注册
     */
    private void regist(){
        String url= Constant.REGISTER_ACCESS;
        final String usernick = et_usernick.getText().toString().trim();
        final String password = et_password.getText().toString().trim();
        String usertel = et_usertel.getText().toString().trim();
        HashMap parmas=new HashMap();
        parmas.put("userName",usernick);
        parmas.put("userPass",password);
        parmas.put("mobile",usertel);

        new BaseProtocol().createObservable(url,MyHttpClient.METHOD_POST,parmas)
                .subscribe(new Action1() {
                    @Override
                    public void call(Object o) {
                        RemoteDataHandler data = new Gson().fromJson(o.toString(), RemoteDataHandler.class);
                        Log.i("ii","o:"+o);
                        if(data.getResult().equals("right")){
                            SaveDatas.getInstance(RegistActivity.this).setUserInfo("nick",usernick);
                            handler.sendEmptyMessage(0);
                            Intent mIntent = new Intent(Constant.LOGIN_SUCCESS_URL);
                            sendBroadcast(mIntent);
                            finish();
                        }else if(o.equals("wrong")) {
                            handler.sendEmptyMessage(2);
                        }else {
                            handler.sendEmptyMessage(3);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        handler.sendEmptyMessage(1);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_regist:
                regist();
                break;
        }
    }
}
