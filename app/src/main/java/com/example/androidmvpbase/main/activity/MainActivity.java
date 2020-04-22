package com.example.androidmvpbase.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidmvpbase.R;
import com.example.androidmvpbase.main.contract.MainContract;
import com.example.androidmvpbase.main.presenter.MainPresetner;
import com.example.androidmvpbase.testrecycleview.TestRecycleActivity;
import com.example.ltbasemodule.base.BaseObjectBean;
import com.example.ltbasemodule.basemvp.BaseMvpActivity;
import com.example.ltbasemodule.utils.LogUtil;
import com.example.ltbasemodule.utils.ProgressDialog;
import com.example.ltbasemodule.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseMvpActivity<MainPresetner> implements MainContract.IMainView {

    @BindView(R.id.tv_helloWorld)
    TextView tvHelloWorld;
    @BindView(R.id.btn_start_testRecycleActivity)
    Button btnStartTestRecycleActivity;
    @BindView(R.id.btn_login)
    Button btnLogin;

    /**
     * 初始化页面等操作
     */
    @Override
    protected void initView() {
        //创建业务层
        mPresenter = new MainPresetner();
        //绑定业务层
        mPresenter.attachView(this);
        tvHelloWorld.setText(R.string.application_name);
        addClickEvent();
    }

    /**
     * 绑定布局文件
     * @return  返回布局文件
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    /**
     * 添加处理了点击抖动的点击监听事件
     */
    private void addClickEvent() {
        addDisposable(tvHelloWorld);
        addDisposable(btnStartTestRecycleActivity);
        addDisposable(btnLogin);
    }

    /**
     * 处理点击抖动的点击监听返回事件
     * @param view  点击的View
     */
    @Override
    protected void clickCallBack(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_helloWorld:
                LogUtil.d(this.getClass(), "tv_helloWorld");
                break;
            case R.id.btn_start_testRecycleActivity:
                Intent intent = new Intent(getApplicationContext(), TestRecycleActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                mPresenter.login("userName", "psw");
                break;
            default:
                break;
        }
    }

    @Override
    public void showLoading() {
        ProgressDialog.getInstance().showLoading(this);
    }

    @Override
    public void hideLoading() {
        ProgressDialog.getInstance().dismiss();
    }

    @Override
    public void onError(Throwable throwable) {
    }

    int count = 1;

    public void testClick(View view) {
        ToastUtil.showToastLong(getApplicationContext(), String.valueOf(count));
        count++;
    }

    @Override
    public void loginSuccess(BaseObjectBean bean) {
        LogUtil.e(this.getClass(), "登录请求成功，do something......");
    }

}
