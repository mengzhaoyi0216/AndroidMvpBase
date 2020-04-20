package com.example.mybasemoudle.main.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ltbasemodule.basemvp.BaseMvpActivity;
import com.example.ltbasemodule.net.RetrofitClient;
import com.example.ltbasemodule.utils.LogUtil;
import com.example.ltbasemodule.utils.ProgressDialog;
import com.example.ltbasemodule.utils.ToastUtil;
import com.example.mybasemoudle.R;

import com.example.mybasemoudle.main.contract.MainContract;
import com.example.mybasemoudle.main.presenter.MainPresetner;
import com.example.mybasemoudle.testrecycleview.TestRecycleActivity;

import butterknife.BindView;

public class MainActivity extends BaseMvpActivity<MainPresetner> implements MainContract.IMainView {

    @BindView(R.id.tv_helloWorld)
    TextView tvHelloWorld;
    @BindView(R.id.btn_start_testRecycleActivity)
    Button btnStartTestRecycleActivity;

    @Override
    protected void initView() {
        tvHelloWorld.setText(R.string.application_name);
        addClickEvent();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private void addClickEvent() {
        addDisposable(tvHelloWorld);
        addDisposable(btnStartTestRecycleActivity);
    }
    @Override
    protected void clickCallBack(View view) {
        int id = view.getId();
        switch (id){
            case R.id.tv_helloWorld:
                LogUtil.d(this.getClass(),"tv_helloWorld");
                break;
            case R.id.btn_start_testRecycleActivity:
                Intent intent = new Intent(getApplicationContext(),TestRecycleActivity.class);
                startActivity(intent);
                break;
                default:
                    break;
        }
    }
    @Override
    public void showLoading(String loadingMessage) {
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
        ToastUtil.showToastLong(getApplicationContext(),String.valueOf(count));
        count++;
    }
}
