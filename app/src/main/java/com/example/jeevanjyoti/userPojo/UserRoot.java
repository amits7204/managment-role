package com.example.jeevanjyoti.userPojo;

import java.util.List;

public class UserRoot {

    private List<JUser> JUser;

    public void setJUser(List<JUser> JUser){
        this.JUser = JUser;
    }
    public List<JUser> getJUser() {
        return this.JUser;
    }

}

