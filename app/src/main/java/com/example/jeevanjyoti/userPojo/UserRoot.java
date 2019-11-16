package com.example.jeevanjyoti.userPojo;

import java.util.List;

public class UserRoot {

        private List<Data> data;

        public void setData(List<Data> data){
            this.data = data;
        }
        public List<Data> getData(){
            return this.data;
        }
}

