package com.example.ltbasemodule.net.upload;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * MultipartBuilder.
 *
 * @author devilwwj
 * @since 2017/7/13
 */
public class MultipartBuilder {

    /**
     * 单文件上传构造.
     *
     * @param file 文件
     * @return MultipartBody
     */
    public static MultipartBody fileToMultipartBody(Map<String,String> params,File file) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), file);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("fileName", file.getName());
//        jsonObject.addProperty("fileSha", Utils.getFileSha1(file));
        Set<Map.Entry<String, String>> entrySet = params.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entrySet.iterator();
        while (iterator.hasNext()){
            Map.Entry<String, String> next = iterator.next();
            jsonObject.addProperty(next.getKey(), next.getValue());
        }
        builder.addFormDataPart("file", file.getName(), requestBody);
        builder.addFormDataPart("params", jsonObject.toString());
        builder.setType(MultipartBody.FORM);
        return builder.build();
    }

    /**
     * 多文件上传构造.
     * @param params 附加参数
     * @param files 文件列表
     * @return MultipartBody
     */
    public static MultipartBody filesToMultipartBody(Map<String,String> params,List<File> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder();

//        builder.setType(MediaType.parse("multipart/form-data"));
//        builder.setType(MultipartBody.FORM);

        for (File file : files) {
//            UploadFileRequestBody uploadFileRequestBody =
//                            new UploadFileRequestBody(file, fileUploadObserver);
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), file);
//            jsonObject.addProperty("fileName", file.getName());
//            jsonObject.addProperty("fileSha", Utils.getFileSha1(file));
//            jsonObject.addProperty("appId", "test0002");
            String fileName = file.getName();
            builder.addFormDataPart("file", fileName, requestBody);
        }
        JsonObject jsonObject = new JsonObject();
        Set<Map.Entry<String, String>> entrySet = params.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entrySet.iterator();
        while (iterator.hasNext()){
            Map.Entry<String, String> next = iterator.next();
            jsonObject.addProperty(next.getKey(), next.getValue());
        }
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(jsonObject);
        Gson gson = new Gson();
        builder.addFormDataPart("params", gson.toJson(jsonArray));
        builder.setType(MultipartBody.FORM);
        return builder.build();
    }
    /**
     * 多文件上传构造.
     * 可以监听上传进度的方法
     * @param params 附加参数
     * @param files 文件列表
     * @return MultipartBody
     */
    public static MultipartBody filesToMultipartBody(Map<String,String> params,List<File> files,
                                                     FileUploadObserver<ResponseBody> fileUploadObserve) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
//        builder.setType(MediaType.parse("multipart/form-data"));
        for (File file : files) {
            UploadFileRequestBody uploadFileRequestBody =
                            new UploadFileRequestBody(file, fileUploadObserve);
//            RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), file);
//            jsonObject.addProperty("fileName", file.getName());
//            jsonObject.addProperty("fileSha", Utils.getFileSha1(file));
//            jsonObject.addProperty("appId", "test0002");
            String fileName = file.getName();
            builder.addFormDataPart("file", fileName, uploadFileRequestBody);
        }
        JsonObject jsonObject = new JsonObject();
        Set<Map.Entry<String, String>> entrySet = params.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entrySet.iterator();
        while (iterator.hasNext()){
            Map.Entry<String, String> next = iterator.next();
            jsonObject.addProperty(next.getKey(), next.getValue());
        }
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(jsonObject);
        Gson gson = new Gson();
        builder.addFormDataPart("params", gson.toJson(jsonArray));
        builder.setType(MultipartBody.FORM);
        return builder.build();
    }
}