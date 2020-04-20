package com.example.androidmvpbase.testrecycleview;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ltbasemodule.adapter.RxCommonAdapter;
import com.example.androidmvpbase.R;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

public class TestRecycleAdapter extends RxCommonAdapter<String> {

    public TestRecycleAdapter(Context context, int layoutId, List<String> datas, AppCompatActivity activity) {
        super(context, layoutId, datas, activity);
    }

    @Override
    protected void convert(ViewHolder holder, String s, int position) {
        holder.setText(R.id.tv_test_item, s);
    }
}
