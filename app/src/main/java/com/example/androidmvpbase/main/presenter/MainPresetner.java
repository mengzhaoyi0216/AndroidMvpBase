package com.example.androidmvpbase.main.presenter;


import com.example.androidmvpbase.main.contract.MainContract;
import com.example.androidmvpbase.main.model.MainModel;
import com.example.ltbasemodule.base.BaseObjectBean;
import com.example.ltbasemodule.base.BasePresenter;
import com.example.ltbasemodule.net.RxScheduler;
import com.example.ltbasemodule.utils.LogUtil;

public class MainPresetner extends BasePresenter<MainContract.IMainView> implements MainContract.IMainPresenter {
    private MainContract.IMainModel model;

    public MainPresetner() {
        this.model = new MainModel();
    }


    @Override
    public void login(String username, String password) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        mView.showLoading();
        // 登陆  release
       /* model.login(username, password)
                //切换到主线程
                .compose(RxScheduler.Flo_io_main())
                //自动注销请求绑定
                .as(mView.bindAutoDispose())
                .subscribe(bean -> {
                    //请求成功返回输出
                    mView.loginSuccess(bean);
                    mView.hideLoading();
                }, throwable -> {
                    //请求失败报错
                    mView.onError(throwable);
                    LogUtil.e(this.getClass(),throwable.getMessage());
                    mView.hideLoading();
                });*/
       // debug
        mView.loginSuccess(new BaseObjectBean());
        mView.hideLoading();
    }
}
