package com.example.androidmvpbase.main.contract;


import com.example.androidmvpbase.main.bean.LoginBean;
import com.example.ltbasemodule.base.BaseObjectBean;
import com.example.ltbasemodule.base.BaseView;

import io.reactivex.Flowable;

public interface MainContract {
    /**
     * 这里写操作页面的方法
     */
    interface IMainView extends BaseView {
        void loginSuccess(BaseObjectBean bean);
    }

    /**
     * 这里写业务相关方法
     */
    interface IMainPresenter{
        void login(String username, String password);
    }

    /**
     * 这里写数据相关方法
     */
    interface IMainModel{
        Flowable<BaseObjectBean<LoginBean>> login(String username, String password);
    }
}
