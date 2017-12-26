package com.gs.testrxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e(TAG, Thread.currentThread().getName());

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.i(TAG, "emitter 1"+" thread:"+Thread.currentThread().getName());
                e.onNext(1);

                Log.i(TAG, "emitter 2"+" thread:"+Thread.currentThread().getName());
                e.onNext(2);

                Log.i(TAG, "emitter 3"+" thread:"+Thread.currentThread().getName());
                e.onNext(3);

                Log.i(TAG, "emitter complete"+" thread:"+Thread.currentThread().getName());
                e.onComplete();

                Log.i(TAG, "emitter 4"+" thread:"+Thread.currentThread().getName());
                e.onNext(4);
            }
        }).subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<Integer>() {
            private Disposable mDisposable;
            private int i;

            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscribe thread:"+Thread.currentThread().getName());
                mDisposable = d;
            }

            @Override
            public void onNext(Integer value) {
                Log.e(TAG, "onNext " + value+" thread:"+Thread.currentThread().getName());
                i++;
                if(i == 4){
                    Log.e(TAG, "dispose");
                    mDisposable.dispose();
                    Log.e(TAG, "isDisposed:"+mDisposable.isDisposed());
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError"+" thread:"+Thread.currentThread().getName());
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete"+" thread:"+Thread.currentThread().getName());
            }
        });
    }
}
