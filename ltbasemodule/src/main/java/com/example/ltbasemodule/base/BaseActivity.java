package com.example.ltbasemodule.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ltbasemodule.common.Constant;
import com.example.ltbasemodule.utils.ProgressDialog;
import com.example.ltbasemodule.utils.ToastUtil;
import com.jakewharton.rxbinding3.view.RxView;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author azheng
 * @date 2018/4/24.
 * GitHub：https://github.com/RookieExaminer
 * Email：wei.azheng@foxmail.com
 * Description：Android的mvp模式，用retrofit2+Rxjava2+Rxbinding+AutoDispose做的一个基类，
 * 基本是引用的https://github.com/RookieExaminer的架构。
 * 但是在访问网络、启动方式的bug、点击抖动事件处理做了优化。
 * 而且demo把GreenDao数据库更新和添加int类型字段的bug修复了
 */
public abstract class BaseActivity extends AppCompatActivity {
    private Unbinder unbinder;
    protected CompositeDisposable mCompositeDisposable;
    /**
     * 这个默认是-1，如果他的子类设置了这个值，那么就按照这个值的时间设置抖动时间
     * 下面判断抖动时间默认是1000毫秒
     */
    private long shakeTimeMilliseconds = -1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(this.getLayoutId());
        unbinder = ButterKnife.bind(this);
        // 处理打开方式不同引起的重新开启项目问题
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        initView();
    }

    /**
     * 添加订阅
     * 处理点击抖动
     */
    public void addDisposable(View view) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        Disposable subscribe = RxView.clicks(view)
                .throttleFirst(shakeTimeMilliseconds > 0? shakeTimeMilliseconds :Constant.SHAKE_TIME, TimeUnit.MILLISECONDS)
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(o -> clickCallBack(view));
        mCompositeDisposable.add(subscribe);
    }

    /**
     * 点击抖动处理后的返回
     * @param view 点击的是哪个View
     */
    protected abstract void clickCallBack(View view);

    /**
     * 取消所有订阅
     */
    public void clearDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
            mCompositeDisposable = null;
        }
    }

    protected abstract void initView();

    protected abstract int getLayoutId();

    /**
     * 设置点击抖动时间，毫秒级别
     * @param shakeTimeMilliseconds 默认是-1
     */
    protected void setShakeTimeMilliseconds(int shakeTimeMilliseconds) {
        this.shakeTimeMilliseconds = shakeTimeMilliseconds;
    }

    /**
     * 页面销毁执行的方法
     */
    @Override
    protected void onDestroy() {
        unbinder.unbind();
        clearDisposable();
        ToastUtil.detachToast();
        ProgressDialog.getInstance().detach();
        super.onDestroy();
    }
}
