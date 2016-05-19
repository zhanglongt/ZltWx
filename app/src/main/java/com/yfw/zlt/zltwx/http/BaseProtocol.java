package com.yfw.zlt.zltwx.http;

import android.text.TextUtils;

import java.util.Map;

import okhttp3.Request;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/4/22.
 */
public class BaseProtocol {
    public Observable<String> createObservable(final String url, final String method, final Map<String, Object> params) {
              return Observable.create(new Observable.OnSubscribe<String>() {
                   @Override
                   public void call(Subscriber<? super String> subscriber) {
                       Request request = MyHttpClient.getmClient().getRequest(url, method, params);
                       String json = MyHttpClient.getmClient().comString(request);
                       setData(subscriber,json);
                   }
               }).subscribeOn(Schedulers.io());
    }

    protected void setData(Subscriber<? super String> subscriber,String json){
        if(TextUtils.isEmpty(json)){
            subscriber.onError(new Throwable("没有数据"));
            subscriber.onCompleted();
            return;
        }
        subscriber.onNext(json);
        subscriber.onCompleted();
    }
}
