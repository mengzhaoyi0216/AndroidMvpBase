package com.example.ltbasemodule.net;



import androidx.annotation.NonNull;

import com.example.ltbasemodule.net.upload.MultipartBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.Flowable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author azheng
 * @date 2018/4/17.
 * GitHub：https://github.com/RookieExaminer
 * email：wei.azheng@foxmail.com
 * description：
 */
public class RetrofitClient {

    private static volatile RetrofitClient instance;

    private RetrofitClient() {
    }

    public static RetrofitClient getInstance() {
        if (instance == null) {
            synchronized (RetrofitClient.class) {
                if (instance == null) {
                    instance = new RetrofitClient();
                }
            }
        }
        return instance;
    }

    /**
     * 设置Header
     * @param headerMap 添加头参数
     * @return 返回一个拦截器
     */
    private Interceptor getHeaderInterceptor(Map<String,String> headerMap) {
        return chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder();
            //添加Token
            //requestBuilder.header("TOKEN", NetConstant.TOKEN);
            if (headerMap == null||headerMap.isEmpty()){
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
            Set<Map.Entry<String, String>> entrySet = headerMap.entrySet();
            for (Map.Entry<String, String> next : entrySet) {
                requestBuilder.addHeader(next.getKey(), next.getValue());
            }
            Request request = requestBuilder.build();
            return chain.proceed(request);
        };

    }

    /**
     * 设置拦截器
     *  增加打印日志
     * @return 返回一个拦截器
     */
    private Interceptor getInterceptor() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        //显示日志
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return interceptor;
    }

    /**
     * 创建请求，用泛型的好处是，可以写多个接口类，分别处理不同的事情
     * @param clz       请求路径接口
     * @param headerMap 请求头
     * @param <T>       请求路径接口类型
     * @return          返回请求路径接口
     */
    public <T> T create(Class<T> clz, Map<String, String> headerMap) {
        //初始化一个client,不然retrofit会自己默认添加一个
        OkHttpClient client = new OkHttpClient().newBuilder()
                //设置Header
                .addInterceptor(getHeaderInterceptor(headerMap))
                //设置拦截器
                .addInterceptor(getInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                //设置网络请求的Url地址
                .baseUrl(NetConstant.BASEURL)
                //设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                //设置网络请求适配器，使其支持RxJava与RxAndroid
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(clz);
    }

    /**
     * 多文件上传.
     * @param params 附加参数
     * @param files 文件列表
     */
    public Flowable<ResponseBody> upLoadFilesReturn(Map<String,String> params, List<File> files,Map<String,String> headerMap) {
        return create(BaseAPIService.class,headerMap).uploadFiles(MultipartBuilder.filesToMultipartBody(params, files));
    }
}
