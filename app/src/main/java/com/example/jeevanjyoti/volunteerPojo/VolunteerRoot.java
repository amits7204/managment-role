package com.example.jeevanjyoti.volunteerPojo;

import java.util.List;

public class VolunteerRoot {
        private List<VolunteerData> data;

        public void setData(List<VolunteerData> data){
            this.data = data;
        }
        public List<VolunteerData> getData(){
            return this.data;
        }

}
