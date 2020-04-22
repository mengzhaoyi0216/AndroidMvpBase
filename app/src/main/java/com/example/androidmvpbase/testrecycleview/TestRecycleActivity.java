package com.example.androidmvpbase.testrecycleview;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ltbasemodule.utils.LogUtil;
import com.example.ltbasemodule.utils.ToastUtil;
import com.example.androidmvpbase.R;
import com.jakewharton.rxbinding3.view.RxView;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestRecycleActivity extends AppCompatActivity {

    @BindView(R.id.rv_textRecycle)
    RecyclerView rvTextRecycle;
    @BindView(R.id.btn_add_data)
    Button btnAddData;
    private TestRecycleAdapter testRecycleAdapter;
    private List<String> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_recycle);
        ButterKnife.bind(this);

        initClick();
        initView();

    }

    private void initClick() {
        RxView.clicks(btnAddData)
                .throttleFirst(1, TimeUnit.SECONDS)
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(o-> addData());
    }

    private void addData() {
        for (int i = 0; i < 10; i++) {
            dataList.add("textClick" + i);
        }
        testRecycleAdapter.notifyDataSetChanged();
    }

    private void initView() {

        testRecycleAdapter = new TestRecycleAdapter(this, R.layout.item_test_recycle, dataList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvTextRecycle.setLayoutManager(linearLayoutManager);
        rvTextRecycle.setAdapter(testRecycleAdapter);
        testRecycleAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                LogUtil.d(TestRecycleActivity.class,"RecycleView点击事件"+i);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                LogUtil.d(TestRecycleActivity.class,"RecycleView长按事件"+i);
                return false;
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataList.clear();
        ToastUtil.detachToast();
    }
}
