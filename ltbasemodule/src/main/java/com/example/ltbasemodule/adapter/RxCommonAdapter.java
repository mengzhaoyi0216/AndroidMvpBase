package com.example.ltbasemodule.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ltbasemodule.common.Constant;
import com.jakewharton.rxbinding3.view.RxView;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class RxCommonAdapter<T> extends CommonAdapter<T> {
    private AppCompatActivity mActivity;

    public RxCommonAdapter(Context context, int layoutId, List<T> datas, AppCompatActivity activity) {
        super(context, layoutId, datas);
        mActivity = activity;
    }

    @Override
    protected void setListener(final ViewGroup parent, final ViewHolder viewHolder, int viewType) {
        if (!isEnabled(viewType)) return;
        RxView.clicks(viewHolder.getConvertView())
                .throttleFirst(Constant.SHAKE_TIME, TimeUnit.SECONDS)
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(mActivity)))
                .subscribe(unit -> {
                    if (mOnItemClickListener != null) {
                        int position = viewHolder.getAdapterPosition();
                        mOnItemClickListener.onItemClick(viewHolder.getConvertView(), viewHolder, position);
                    }
                });
        RxView.longClicks(viewHolder.getConvertView())
                .throttleFirst(Constant.SHAKE_TIME, TimeUnit.SECONDS)
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(mActivity)))
                .subscribe(unit -> {
                    if (mOnItemClickListener != null) {
                        int position = viewHolder.getAdapterPosition();
                        mOnItemClickListener.onItemLongClick(viewHolder.getConvertView(), viewHolder, position);
                    }
                });
    }
}
