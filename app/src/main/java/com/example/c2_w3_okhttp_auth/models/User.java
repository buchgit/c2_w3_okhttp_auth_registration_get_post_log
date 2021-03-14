package com.example.c2_w3_okhttp_auth.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
    @SerializedName("email")
    private String mEmail;
    //прим.: пароль на сервере легиона требуется восьмизначный
    @SerializedName("password")
    private String mPassword;
    @SerializedName("name")
    private String mName;

    private boolean mHasSuccessEmail;

    public boolean isHasSuccessEmail() {
        return mHasSuccessEmail;
    }

    public void setHasSuccessEmail(boolean mHasSuccessEmail) {
        this.mHasSuccessEmail = mHasSuccessEmail;
    }

    public User(String email, String name, String password) {
        mEmail = email;
        mPassword = password;
        mName = name;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }


    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }


}
