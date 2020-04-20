package com.example.androidmvpbase.main.bean;

import androidx.annotation.NonNull;

public class LoginBean {
    private String userName;
    private String passWord;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @NonNull
    @Override
    public String toString() {
        return "userName:"+userName+",passWord:"+passWord;
    }
}