package com.example.mybasemoudle.net;

import com.example.ltbasemodule.base.BaseObjectBean;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author mzy
 * @date 2020-04-20
 */
public interface NetService {
    /**
     * 登陆
     * @param loginJson 登陆json字符串
     * @return
     */
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("taskService!appLogin.action")
    Flowable<BaseObjectBean<ResponseBody>> login(@Body RequestBody loginJson);
}
