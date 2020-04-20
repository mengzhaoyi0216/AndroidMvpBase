package com.example.ltbasemodule.net;



import com.example.ltbasemodule.base.BaseObjectBean;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author azheng
 * @date 2018/4/24.
 * GitHub：https://github.com/RookieExaminer
 * Email：wei.azheng@foxmail.com
 * Description：
 */
public interface BaseAPIService {

    /**
     * 登陆,表单提交
     *
     * @param username 账号
     * @param password 密码
     * @return 返回一个流
     */
    @FormUrlEncoded
    @POST("user/login")
    Flowable<BaseObjectBean<ResponseBody>> login(@Field("username") String username,
                                                 @Field("password") String password);
    /**
     * 登陆，json串提交
     *
     * @param loginJson 登陆json字符串
     * @return 返回一个流
     */
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("taskService!appLogin.action")
    Flowable<BaseObjectBean<ResponseBody>> login(@Body RequestBody loginJson);

    /**
     * 多文件上传功能
     * @param body MultipartBody
     * @return 返回结果
     */
    @POST("taskService!bactFiles.action")
    Flowable<ResponseBody> uploadFiles(@Body MultipartBody body);
}
