package com.example.mybasemoudle.main.presenter;


import com.example.ltbasemodule.base.BasePresenter;
import com.example.mybasemoudle.main.contract.MainContract;

public class MainPresetner extends BasePresenter<MainContract.IMainView> implements MainContract.IMainPresenter {
    private MainContract.IMainModel model;

    public MainPresetner(MainContract.IMainModel model) {
        this.model = model;
    }
}
