package com.example.jeevanjyoti.otpPojo;

import org.json.JSONObject;

public class OtpRoot {
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
