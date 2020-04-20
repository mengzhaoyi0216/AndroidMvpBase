package com.example.ltbasemodule.net.upload;

import io.reactivex.observers.DefaultObserver;

/**
 * 上传文件的RxJava2回调.
 *
 * @author devilwwj
 * @since 2017/7/12
 *
 * @param <T> 模板类
 */
public abstract class FileUploadObserver<T> extends DefaultObserver<T> {

    @Override
    public void onNext(T t) {
        onUploadSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        onUploadFail(e);
    }

    @Override
    public void onComplete() {

    }

    // 上传成功的回调
    public abstract void onUploadSuccess(T t);

    // 上传失败回调
    public abstract void onUploadFail(Throwable e);

    // 上传进度回调
    public abstract void onProgress(int progress);

    // 监听进度的改变
    public void onProgressChange(long bytesWritten, long contentLength) {
        onProgress((int) (bytesWritten * 100 / contentLength));
    }
}