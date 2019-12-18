package com.example.jeevanjyoti.retrofit;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Volunteer {
    private String msg;

    private boolean status;

    private String data;

    public void setMsg(String msg){
        this.msg = msg;
    }
    public String getMsg(){
        return this.msg;
    }
    public void setStatus(boolean status){
        this.status = status;
    }
    public boolean getStatus(){
        return this.status;
    }
    public void setData(String data){
        this.data = data;
    }
    public String getData(){
        return this.data;
    }
}
