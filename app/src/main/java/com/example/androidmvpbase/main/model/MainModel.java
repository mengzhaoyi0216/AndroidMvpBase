package com.example.androidmvpbase.main.model;

import com.example.androidmvpbase.main.bean.LoginBean;
import com.example.androidmvpbase.main.contract.MainContract;
import com.example.androidmvpbase.net.NetService;
import com.example.ltbasemodule.base.BaseObjectBean;
import com.example.ltbasemodule.net.RetrofitClient;
import com.google.gson.Gson;

import io.reactivex.Flowable;
import okhttp3.RequestBody;


public class MainModel implements MainContract.IMainModel {
    @Override
    public Flowable<BaseObjectBean<LoginBean>> login(String username, String password) {
        LoginBean loginBean = new LoginBean();
        loginBean.setUserName(username);
        loginBean.setPassWord(password);
        Gson gson = new Gson();
        String loginJson = gson.toJson(loginBean);

        RequestBody loginBody= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),loginJson);
        return RetrofitClient.getInstance().create(NetService.class,null).login(loginBody);
    }
}
